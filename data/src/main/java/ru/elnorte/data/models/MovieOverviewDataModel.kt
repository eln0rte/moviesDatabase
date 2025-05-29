package ru.elnorte.data.models

import ru.elnorte.core.models.FavDatabaseModel

data class MovieOverviewDataModel(
    val id: Int,
    val poster: String,
    val title: String,
    val info: String,
    val isFavourite:Boolean,
)

fun FavDatabaseModel.asMovieOverviewDataModel(): MovieOverviewDataModel = MovieOverviewDataModel(
    id = this.id,
    poster = this.poster,
    title = this.title,
    info = this.info,
    isFavourite = true,
)
