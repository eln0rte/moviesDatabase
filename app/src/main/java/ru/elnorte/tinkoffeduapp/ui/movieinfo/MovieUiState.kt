package ru.elnorte.tinkoffeduapp.ui.movieinfo

import ru.elnorte.tinkoffeduapp.ui.models.MovieInfoDataModel

sealed class MovieUiState {
    data class Success(val data: MovieInfoDataModel) : MovieUiState()
    data class Error(var error: String) : MovieUiState()
    data object Loading: MovieUiState()
}
