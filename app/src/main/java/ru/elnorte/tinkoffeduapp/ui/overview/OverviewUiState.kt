package ru.elnorte.tinkoffeduapp.ui.overview

import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel

sealed class OverviewUiState {
    data class Success(
        val list: List<MovieOverviewDataModel>,
        val fragment: FragmentType,
        val searchShow: Boolean = false,
    ) : OverviewUiState()

    data class Error(val error: String?) : OverviewUiState()

    data object Loading : OverviewUiState()
}

enum class FragmentType { FAVORITE, OVERVIEW }
