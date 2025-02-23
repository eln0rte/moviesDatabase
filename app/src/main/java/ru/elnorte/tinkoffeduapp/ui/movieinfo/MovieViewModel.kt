package ru.elnorte.tinkoffeduapp.ui.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.elnorte.tinkoffeduapp.data.movierepository.MovieRepository
import ru.elnorte.tinkoffeduapp.ui.models.MovieInfoDataModel
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    private val _model = MutableLiveData<MovieInfoDataModel>()
    val model: LiveData<MovieInfoDataModel>
        get() = _model

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private var cachedId: Int? = null
    fun fetchMovieDetails(id: Int) {
        cachedId = id
        viewModelScope.launch {
            try {
                _model.value = repo.getMovie(id)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun resetError() {
        _error.value = null
    }

    fun retry() {
        resetError()
        cachedId?.let {
            fetchMovieDetails(it)
        }
    }
}
