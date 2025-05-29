package ru.elnorte.core.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.elnorte.core.models.network.MovieDetailsTransferModel


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


fun MovieDetailsTransferModel.asMovieDatabaseModel(): FavDatabaseModel = FavDatabaseModel(
    id = this.kinopoiskId,
    poster = this.posterUrl,
    posterSmall = this.posterUrlPreview ?: this.posterUrl,
    title = this.nameRu,
    info = "${this.genres[0].genre} (${this.year})",
    description = this.description,
    genre = this.genres.joinToString(", ") { it.genre },
    origin = this.countries.joinToString(", ") { it.country }
)
