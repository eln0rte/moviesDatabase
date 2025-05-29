package ru.elnorte.features.ui.overview

sealed class OverviewEffect {
    data class NavigateToMovie(val movieId:Int): OverviewEffect()
//    data object SwitchToOverview: OverviewEffect()
//    data object SwitchToFavs: OverviewEffect()
}
