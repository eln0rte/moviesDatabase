package ru.elnorte.data.impl.movierepository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


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
