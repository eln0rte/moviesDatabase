package ru.elnorte.tinkoffeduapp.ui.movieinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.elnorte.tinkoffeduapp.data.movierepository.MovieRepository

class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("unchecked_cast")
            return MovieViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}