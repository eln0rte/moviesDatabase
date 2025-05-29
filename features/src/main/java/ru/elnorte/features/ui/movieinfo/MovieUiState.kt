package ru.elnorte.features.ui.movieinfo

import ru.elnorte.data.api.models.MovieInfoDataModel
import ru.elnorte.data.api.models.MovieResult

sealed class MovieUiState {
    data class Success(val data: MovieInfoDataModel) : MovieUiState()
    data class Error(var error: String) : MovieUiState()
    data object Loading : MovieUiState()
}

fun MovieResult.toMovieUiState(): MovieUiState {
    return when (this) {
        is MovieResult.Error -> MovieUiState.Error(this.error)
        is MovieResult.Success -> MovieUiState.Success(this.data)
    }
}
