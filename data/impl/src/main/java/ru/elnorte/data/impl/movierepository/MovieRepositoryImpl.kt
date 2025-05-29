package ru.elnorte.data.impl.movierepository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.elnorte.data.api.models.MovieListType
import ru.elnorte.data.api.models.MovieResult
import ru.elnorte.data.api.models.OverviewResult
import ru.elnorte.data.api.movierepository.MovieRepository
import ru.elnorte.data.impl.movierepository.database.FavDatabaseDao
import ru.elnorte.data.impl.movierepository.models.asMovieInfoDataModel
import ru.elnorte.data.impl.movierepository.models.network.TopMoviesTransferModel
import ru.elnorte.data.impl.movierepository.models.network.asFavDatabaseModel
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val dao: FavDatabaseDao,
    private val remoteDataSource: MovieRemoteDataSource,
    private val converter: TopMoviesResponseConverter,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MovieRepository {


    override suspend fun getMovie(id: Int): MovieResult {
        return withContext(defaultDispatcher) {
            if (dao.getIds().contains(id)) {
                getCachedMovie(id)
            } else {
                getNetworkMovie(id)
            }
        }
    }

    override suspend fun getTopMovies(): OverviewResult {
        return withContext(defaultDispatcher) {
            remoteDataSource.getTopMovies().fold(
                onSuccess = { response ->
                    markFavs(response)
                },
                onFailure = { exception ->
                    OverviewResult.Error(exception.message)
                }
            )
        }
    }
    override suspend fun getFavMovies(): OverviewResult {
        return withContext(defaultDispatcher) {
            OverviewResult.Success(
                list = dao.getAll().map { converter.convertToOverview(it)},
                fragment = MovieListType.FAVORITE
            )
        }
    }

    override suspend fun toggleFav(id: Int) {
        withContext(defaultDispatcher) {
            if (dao.getMovie(id) == null) {
                remoteDataSource.getMovieDetails(id).getOrNull()?.let { details ->
                    dao.insert(details.asFavDatabaseModel())
                }
            } else {
                dao.delete(id)
            }
        }
    }

    override suspend fun getNetworkMovie(id: Int): MovieResult {
        return remoteDataSource.getMovieDetails(id).fold(
            onSuccess = { MovieResult.Success(converter.convertToInfo(it)) },
            onFailure = { error -> MovieResult.Error(error.toString()) }
        )
    }

    override suspend fun getCachedMovie(id: Int): MovieResult {
        return dao.getMovie(id)?.let {
            MovieResult.Success(it.asMovieInfoDataModel())
        } ?: MovieResult.Error("Universe collapsed and database was in that universe")
    }

    private suspend fun markFavs(response: TopMoviesTransferModel): OverviewResult.Success {
        val ids = dao.getIds()
        val model = converter.convertToMovieOverviewDataModel(response, ids)
        return OverviewResult.Success(model, MovieListType.OVERVIEW)
    }
}
