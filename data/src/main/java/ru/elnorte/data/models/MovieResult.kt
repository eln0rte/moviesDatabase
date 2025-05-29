package ru.elnorte.data.models

sealed class MovieResult {
    data class Success(val data: MovieInfoDataModel) : MovieResult()
    data class Error(var error: String) : MovieResult()
}
