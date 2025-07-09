package ru.elnorte.features.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.elnorte.data.api.movierepository.MovieRepository
import javax.inject.Inject

class OverviewViewModelFactory @Inject constructor(private val repo: MovieRepository) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("unchecked_cast")
            return OverviewViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
