package ru.elnorte.tinkoffeduapp.data.remote

import ru.elnorte.tinkoffeduapp.data.models.network.MovieDetailsTransferModel
import ru.elnorte.tinkoffeduapp.data.models.network.TopMoviesTransferModel

interface MovieRemoteDataSource {
    suspend fun getTopMovies(): Result<TopMoviesTransferModel>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsTransferModel>
}
