package ru.elnorte.features.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.elnorte.data.api.models.MovieListType
import ru.elnorte.data.api.models.MovieOverviewDataModel
import ru.elnorte.data.api.movierepository.MovieRepository
import javax.inject.Inject

class OverviewViewModel @Inject constructor(
    private val repo: MovieRepository,
) : ViewModel() {
    private val _state = MutableLiveData<OverviewUiState>()
    val state: LiveData<OverviewUiState> get() = _state

    private val _effects = MutableSharedFlow<OverviewEffect>()
    val effects: SharedFlow<OverviewEffect> = _effects.asSharedFlow()

    private var originalList = emptyList<MovieOverviewDataModel>()

    init {
        loadOverview()
    }

    fun updateSearchQuery(query: String) {
        (state.value as? OverviewUiState.Success)?.let {
            val currentList = originalList.filterTitleContains(query)
            _state.value = it.copy(list = currentList, searchShow = true)
        }
    }

    fun switchToFavs() {
        if ((state.value as OverviewUiState.Success).fragment == MovieListType.OVERVIEW) {
            loadFavs()
        }

    }

    fun switchToOverview() {
        if ((state.value as? OverviewUiState.Success)?.fragment == MovieListType.FAVORITE) {
            loadOverview()
        }
    }

    fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            repo.toggleFav(id)
            reloadCurrentState()
        }
    }

    fun retry() {
        reloadCurrentState()
    }

    fun navigateToMovie(movieId: Int) {
        viewModelScope.launch {
            _effects.emit(OverviewEffect.NavigateToMovie(movieId))
        }
    }

    private fun loadOverview() {
        _state.value = OverviewUiState.Loading
        viewModelScope.launch {
            repo.getTopMovies().toOverviewUiState().also { state ->
                state.tryUpdateOriginalList()
                _state.value = state
            }
        }
    }

    private fun loadFavs() {
        _state.value = OverviewUiState.Loading
        viewModelScope.launch {
            repo.getFavMovies().toOverviewUiState().also { state ->
                state.tryUpdateOriginalList()
                _state.value = state
            }
        }
    }

    private fun OverviewUiState.tryUpdateOriginalList() {
        (this as? OverviewUiState.Success)?.run { originalList = list }
    }

    private fun List<MovieOverviewDataModel>.filterTitleContains(query: String) =
        filter { it.title.contains(query, ignoreCase = true) }

    private fun reloadCurrentState() {

        when (val current = state.value) {
            is OverviewUiState.Success -> {
                reloadSuccess(current)
            }

            else -> loadOverview()
        }
    }

    private fun reloadSuccess(current: OverviewUiState.Success) {
        when (current.fragment) {
            MovieListType.FAVORITE -> loadFavs()
            MovieListType.OVERVIEW -> loadOverview()
        }
    }
}
