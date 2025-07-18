package ru.elnorte.features.ui.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.elnorte.data.api.movierepository.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {

    private val _state = MutableLiveData<MovieUiState>()
    val state: LiveData<MovieUiState>
        get() = _state

    private var cachedId: Int? = null
    fun fetchMovieDetails(id: Int) {
        cachedId = id
        viewModelScope.launch {
            _state.value = repo.getMovie(id).toMovieUiState()
        }
    }

    fun retry() {
        cachedId?.let {
            fetchMovieDetails(it)
        }
    }
}
