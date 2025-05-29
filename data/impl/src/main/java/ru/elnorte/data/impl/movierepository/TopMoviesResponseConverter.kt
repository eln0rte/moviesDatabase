package ru.elnorte.data.impl.movierepository

import ru.elnorte.data.api.models.MovieInfoDataModel
import ru.elnorte.data.api.models.MovieOverviewDataModel
import ru.elnorte.data.impl.movierepository.models.FavDatabaseModel
import ru.elnorte.data.impl.movierepository.models.asMovieOverviewDataModel
import ru.elnorte.data.impl.movierepository.models.network.MovieDetailsTransferModel
import ru.elnorte.data.impl.movierepository.models.network.TopMoviesTransferModel
import ru.elnorte.data.impl.movierepository.models.network.asMovieInfoDataModel


internal class TopMoviesResponseConverter {
    fun convertToInfo(input: MovieDetailsTransferModel): MovieInfoDataModel =
        input.asMovieInfoDataModel()

    fun convertToOverview(input: FavDatabaseModel): MovieOverviewDataModel =
        input.asMovieOverviewDataModel()

    fun convertToMovieOverviewDataModel(
        input: TopMoviesTransferModel,
        favIds: List<Int>
    ): List<MovieOverviewDataModel> = input.films.map {
        MovieOverviewDataModel(
            id = it.filmId,
            poster = it.posterUrlPreview,
            title = it.nameRu,
            info = "${it.genres[0].genre} (${it.year ?: "----"})",
            isFavourite = favIds.contains(it.filmId),

            )
    }
}
