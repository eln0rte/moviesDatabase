package ru.elnorte.tinkoffeduapp.data.movierepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.elnorte.tinkoffeduapp.data.models.asMovieInfoDataModel
import ru.elnorte.tinkoffeduapp.data.models.asMovieOverviewDataModel
import ru.elnorte.tinkoffeduapp.data.models.network.TopMoviesTransferModel
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieDatabaseModel
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieInfoDataModel
import ru.elnorte.tinkoffeduapp.data.movierepository.database.FavDatabaseDao
import ru.elnorte.tinkoffeduapp.data.remote.MovieRemoteDataSource
import ru.elnorte.tinkoffeduapp.ui.movieinfo.MovieUiState
import ru.elnorte.tinkoffeduapp.ui.overview.FragmentType
import ru.elnorte.tinkoffeduapp.ui.overview.OverviewUiState
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val dao: FavDatabaseDao,
    private val remoteDataSource: MovieRemoteDataSource,
    private val moviesResponseConverter: TopMoviesResponseConverter,
) {


    suspend fun getMovie(id: Int): MovieUiState {
        return withContext(Dispatchers.IO) {
            if (dao.getIds().contains(id)) {
                getCachedMovie(id)
            } else {
                getNetworkMovie(id)
            }
        }
    }

    private suspend fun getNetworkMovie(id: Int): MovieUiState {
        return remoteDataSource.getMovieDetails(id).fold(
            onSuccess = { MovieUiState.Success(it.asMovieInfoDataModel()) },
            onFailure = { error -> MovieUiState.Error(error.toString()) }
        )
    }

    private suspend fun getCachedMovie(id: Int): MovieUiState {
        return dao.getMovie(id)?.let {
            MovieUiState.Success(it.asMovieInfoDataModel())
        } ?: MovieUiState.Error("Universe collapsed and database was in that universe")
    }

    suspend fun getTopMovies(): OverviewUiState {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getTopMovies().fold(
                onSuccess = { response ->
                    markFavs(response)
                },
                onFailure = { exception ->
                    OverviewUiState.Error(exception.message)
                }
            )
        }
    }

    private suspend fun markFavs(response: TopMoviesTransferModel): OverviewUiState.Success {
        val ids = dao.getIds()
        val model = moviesResponseConverter.convertToMovieOverviewDataModel(response, ids)
        return OverviewUiState.Success(model, FragmentType.OVERVIEW)
    }

    suspend fun getFavMovies(): OverviewUiState {
        return withContext(Dispatchers.IO) {
            OverviewUiState.Success(
                list = dao.getAll().map { it.asMovieOverviewDataModel() },
                fragment = FragmentType.FAVORITE
            )
        }
    }

    suspend fun toggleFav(id: Int) {
        withContext(Dispatchers.IO) {
            if (dao.getMovie(id) == null) {
                remoteDataSource.getMovieDetails(id).getOrNull()?.let { details ->
                    dao.insert(details.asMovieDatabaseModel())
                }
            } else {
                dao.delete(id)
            }
        }
    }
}
