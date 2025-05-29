package ru.elnorte.data.models

import ru.elnorte.core.models.FavDatabaseModel
import ru.elnorte.core.models.network.MovieDetailsTransferModel

data class MovieInfoDataModel(
    val id: Int,
    val poster: String,
    val title: String,
    val description: String,
    val genre: String,
    val origin: String,
)

fun FavDatabaseModel.asMovieInfoDataModel(): MovieInfoDataModel = MovieInfoDataModel(
    id = this.id,
    poster = this.poster,
    title = this.title,
    description = this.description,
    genre = this.genre,
    origin = this.origin,
)
fun MovieDetailsTransferModel.asMovieInfoDataModel(): MovieInfoDataModel = MovieInfoDataModel(
    id = this.kinopoiskId,
    poster = this.posterUrl,
    title = this.nameRu,
    description = this.description,
    genre = this.genres.joinToString(", ") { it.genre },
    origin = this.countries.joinToString(", ") { it.country }
)
