package ru.elnorte.data.movierepository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.elnorte.core.models.asMovieDatabaseModel
import ru.elnorte.core.models.network.TopMoviesTransferModel
import ru.elnorte.data.models.MovieResult
import ru.elnorte.data.models.OverviewResult
import ru.elnorte.data.models.asMovieInfoDataModel
import ru.elnorte.data.models.asMovieOverviewDataModel
import ru.elnorte.data.movierepository.database.FavDatabaseDao
import ru.elnorte.data.remote.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dao: FavDatabaseDao,
    private val remoteDataSource: MovieRemoteDataSource,
    private val moviesResponseConverter: TopMoviesResponseConverter,
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
                list = dao.getAll().map { it.asMovieOverviewDataModel() },
                fragment = FragmentType.FAVORITE
            )
        }
    }

    override suspend fun toggleFav(id: Int) {
        withContext(defaultDispatcher) {
            if (dao.getMovie(id) == null) {
                remoteDataSource.getMovieDetails(id).getOrNull()?.let { details ->
                    dao.insert(details.asMovieDatabaseModel())
                }
            } else {
                dao.delete(id)
            }
        }
    }

    override suspend fun getNetworkMovie(id: Int): MovieResult {
        return remoteDataSource.getMovieDetails(id).fold(
            onSuccess = { MovieResult.Success(it.asMovieInfoDataModel()) },
            onFailure = { error -> MovieResult.Error(error.toString()) }
        )
    }

    override suspend fun getCachedMovie(id: Int): MovieResult {
        return dao.getMovie(id)?.let {
            MovieResult.Success(it.asMovieInfoDataModel())
        } ?: MovieResult.Error("Universe collapsed and database was in that universe")
    }

    override suspend fun markFavs(response: TopMoviesTransferModel): OverviewResult.Success {
        val ids = dao.getIds()
        val model = moviesResponseConverter.convertToMovieOverviewDataModel(response, ids)
        return OverviewResult.Success(model, FragmentType.OVERVIEW)
    }
}

enum class FragmentType { FAVORITE, OVERVIEW }
