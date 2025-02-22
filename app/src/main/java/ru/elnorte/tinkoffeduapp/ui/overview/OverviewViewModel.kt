package ru.elnorte.tinkoffeduapp.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.elnorte.tinkoffeduapp.data.movierepository.MovieRepository
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel
import java.net.ConnectException
import javax.inject.Inject

class OverviewViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {

    private val _model = MutableLiveData<List<MovieOverviewDataModel>>()
    val model: LiveData<List<MovieOverviewDataModel>>
        get() = _model

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _navigateToMovie = MutableLiveData<Int?>()
    val navigateToMovie: LiveData<Int?>
        get() = _navigateToMovie

    private val _state = MutableLiveData<ScreenState?>()
    val state: LiveData<ScreenState?>
        get() = _state

    private var favsChecked = false
    fun update() {
        _state.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                val test = repo.getTopMovies(favsChecked)
                _model.value = test
                _state.value = ScreenState.Success
            } catch (e: ConnectException) {
                _state.value = ScreenState.Error(e.message)
                _error.value = e.message
            } catch (e: Exception) {
                _state.value = ScreenState.Error(e.message)
                _error.value = e.message
            }
        }
    }

    fun showMovie(movieId: Int) {
        _navigateToMovie.value = movieId
    }

    fun showMovieComplete() {
        _navigateToMovie.value = null
    }

    fun resetError() {
        _error.value = null
    }

    fun retry() {
        resetError()
        update()

    }

    fun addFavourite(movieId: Int) {
        viewModelScope.launch {
            repo.addFav(movieId)
            _model.value =
                _model.value?.map {
                    if (it.id == movieId) {
                        it.copy(isFavourite = !it.isFavourite)
                    } else {
                        it
                    }
                }
            if (favsChecked) {
                update()
            }

            //update()
        }

    }

    fun switchToPopular() {
        favsChecked = false
        update()
    }

    fun switchToFavs() {
        favsChecked = true
        update()
    }
}

sealed class ScreenState() {
    data class Error(val message: String? = null) : ScreenState()
    data object Success : ScreenState()
    data object Loading : ScreenState()
}
