package ru.elnorte.data.impl.movierepository.local

import ru.elnorte.data.impl.movierepository.MovieLocalDataSource
import ru.elnorte.data.impl.movierepository.local.database.FavDatabaseDao
import ru.elnorte.data.impl.movierepository.models.FavDatabaseModel
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val dao: FavDatabaseDao) :MovieLocalDataSource {
    override suspend fun addMovie(item: FavDatabaseModel) {
        dao.insert(item)
    }

    override suspend fun deleteMovie(movieId: Int) {
        dao.delete(movieId)
    }

    override suspend fun getMovie(movieId: Int): FavDatabaseModel? {
        return dao.getMovie(movieId)
    }

    override suspend fun getMoviesIds(): List<Int> {
        return dao.getIds()
    }

    override suspend fun getAll(): List<FavDatabaseModel> {
        return dao.getAll()
    }
}
