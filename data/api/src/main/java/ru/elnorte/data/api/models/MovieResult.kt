package ru.elnorte.data.api.models

sealed class MovieResult {
    data class Success(val data: MovieInfoDataModel) : MovieResult()
    data class Error(var error: String) : MovieResult()
}
