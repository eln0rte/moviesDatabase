package ru.elnorte.features.ui.overview

import ru.elnorte.data.models.MovieOverviewDataModel
import ru.elnorte.data.models.OverviewResult
import ru.elnorte.data.movierepository.FragmentType

sealed class OverviewUiState {
    data class Success(
        val list: List<MovieOverviewDataModel>,
        val fragment: FragmentType,
        val searchShow: Boolean = false,
    ) : OverviewUiState()

    data class Error(val error: String?) : OverviewUiState()

    data object Loading : OverviewUiState()
}


fun OverviewResult.toOverviewUiState() : OverviewUiState{
    return when(this){
        is OverviewResult.Error -> OverviewUiState.Error(this.error)
        is OverviewResult.Success -> OverviewUiState.Success(this.list,this.fragment,this.searchShow)
    }
}
