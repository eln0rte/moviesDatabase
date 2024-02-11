package ru.elnorte.tinkoffeduapp.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.elnorte.tinkoffeduapp.data.movierepository.MovieRepository
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel
import java.net.ConnectException

class OverviewViewModel(private val repo: MovieRepository) : ViewModel() {

    private val _model = MutableLiveData<List<MovieOverviewDataModel>>()
    val model: LiveData<List<MovieOverviewDataModel>>
        get() = _model

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _navigateToMovie = MutableLiveData<Int?>()
    val navigateToMovie: LiveData<Int?>
        get() = _navigateToMovie

    fun update() {
        viewModelScope.launch {
            try {
                _model.value = repo.getTopMovies()
            } catch (e: ConnectException) {
                _error.value = e.message
            } catch (e: Exception) {
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
}