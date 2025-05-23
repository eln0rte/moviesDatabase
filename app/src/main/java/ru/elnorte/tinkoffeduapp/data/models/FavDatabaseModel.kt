package ru.elnorte.tinkoffeduapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.elnorte.tinkoffeduapp.ui.models.MovieInfoDataModel
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel

@Entity(tableName = "favs_table")
data class FavDatabaseModel(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "postersmall")
    val posterSmall: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "info")
    val info: String,
    @ColumnInfo(name = "decription")
    val description: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "origin")
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


fun FavDatabaseModel.asMovieOverviewDataModel(): MovieOverviewDataModel = MovieOverviewDataModel(
    id = this.id,
    poster = this.poster,
    title = this.title,
    info = this.info,
    isFavourite = true,
)
