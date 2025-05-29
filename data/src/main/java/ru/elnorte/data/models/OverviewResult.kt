package ru.elnorte.data.models

import ru.elnorte.data.movierepository.FragmentType

sealed class OverviewResult {
    data class Success(
        val list: List<MovieOverviewDataModel>,
        val fragment: FragmentType,
        val searchShow: Boolean = false,
    ) : OverviewResult()

    data class Error(val error: String?) : OverviewResult()
}
