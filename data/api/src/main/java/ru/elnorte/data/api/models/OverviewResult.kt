package ru.elnorte.data.api.models

sealed class OverviewResult {
    data class Success(
        val list: List<MovieOverviewDataModel>,
        val fragment: MovieListType,
        val searchShow: Boolean = false,
    ) : OverviewResult()

    data class Error(val error: String?) : OverviewResult()
}

enum class MovieListType { FAVORITE, OVERVIEW }
