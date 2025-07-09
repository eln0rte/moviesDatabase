package ru.elnorte.data.impl.movierepository

import ru.elnorte.data.impl.movierepository.models.FavDatabaseModel

interface MovieLocalDataSource {
    suspend fun addMovie(item: FavDatabaseModel)
    suspend fun deleteMovie(movieId: Int)
    suspend fun getMovie(movieId: Int): FavDatabaseModel?
    suspend fun getMoviesIds() : List<Int>
    suspend fun getAll(): List<FavDatabaseModel>
}
