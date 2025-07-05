package ru.elnorte.data.impl.movierepository

import ru.elnorte.data.api.models.MovieInfoDataModel
import ru.elnorte.data.api.models.MovieOverviewDataModel
import ru.elnorte.data.impl.movierepository.models.FavDatabaseModel
import ru.elnorte.data.impl.movierepository.models.network.MovieDetailsTransferModel
import ru.elnorte.data.impl.movierepository.models.network.TopMoviesTransferModel


internal class MoviesConverter {
    fun convertToInfo(input: MovieDetailsTransferModel): MovieInfoDataModel =
        MovieInfoDataModel(
            id = input.kinopoiskId,
            poster = input.posterUrl,
            title = input.nameRu,
            description = input.description,
            genre = input.genres.joinToString(", ") { it.genre },
            origin = input.countries.joinToString(", ") { it.country }
        )
    fun convertToInfo(input: FavDatabaseModel): MovieInfoDataModel = MovieInfoDataModel(
        id = input.id,
        poster = input.poster,
        title = input.title,
        description = input.description,
        genre = input.genre,
        origin = input.origin,
    )
    fun convertToOverview(input: FavDatabaseModel): MovieOverviewDataModel =
        MovieOverviewDataModel(
            id = input.id,
            poster = input.poster,
            title = input.title,
            info = input.info,
            isFavourite = true,
        )

    fun convertToOverview(
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

    fun convertToFav(input: MovieDetailsTransferModel): FavDatabaseModel = FavDatabaseModel(
        id = input.kinopoiskId,
        poster = input.posterUrl,
        posterSmall = input.posterUrlPreview ?: input.posterUrl,
        title = input.nameRu,
        info = "${input.genres[0].genre} (${input.year})",
        description = input.description,
        genre = input.genres.joinToString(", ") { it.genre },
        origin = input.countries.joinToString(", ") { it.country }
    )
}
