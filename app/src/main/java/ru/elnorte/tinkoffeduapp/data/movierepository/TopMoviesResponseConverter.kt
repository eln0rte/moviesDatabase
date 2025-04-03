package ru.elnorte.tinkoffeduapp.data.movierepository

import ru.elnorte.tinkoffeduapp.data.models.network.TopMoviesTransferModel
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel
import javax.inject.Inject

class TopMoviesResponseConverter @Inject constructor() {
    fun convertToMovieOverviewDataModel( input: TopMoviesTransferModel, favIds: List<Int>):List<MovieOverviewDataModel> = input.films.map{
        MovieOverviewDataModel(
            id = it.filmId,
            poster = it.posterUrlPreview,
            title = it.nameRu,
            info = "${it.genres[0].genre} (${it.year ?: "----"})",
            isFavourite = favIds.contains(it.filmId),

        )
    }
}
