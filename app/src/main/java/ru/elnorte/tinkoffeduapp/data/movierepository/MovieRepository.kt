package ru.elnorte.tinkoffeduapp.data.movierepository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieInfoDataModel
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieOverviewDataModel
import ru.elnorte.tinkoffeduapp.data.movierepository.network.MoviesApi
import ru.elnorte.tinkoffeduapp.ui.models.MovieInfoDataModel
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel

class MovieRepository {

    suspend fun getMovie(id: Int): MovieInfoDataModel {
        return withContext(Dispatchers.IO) {
            MoviesApi.retrofitService.getMovie(id).asMovieInfoDataModel()
        }

    }

    suspend fun getTopMovies(): List<MovieOverviewDataModel> {
        return withContext(Dispatchers.IO) {
            val data = MoviesApi.retrofitService.getTopMovies()
            data.asMovieOverviewDataModel()
        }
    }
}