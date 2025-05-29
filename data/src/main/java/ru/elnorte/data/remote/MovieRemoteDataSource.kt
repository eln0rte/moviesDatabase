package ru.elnorte.data.remote

import ru.elnorte.core.models.network.MovieDetailsTransferModel
import ru.elnorte.core.models.network.TopMoviesTransferModel

interface MovieRemoteDataSource {
    suspend fun getTopMovies(): Result<ru.elnorte.core.models.network.TopMoviesTransferModel>
    suspend fun getMovieDetails(movieId: Int): Result<ru.elnorte.core.models.network.MovieDetailsTransferModel>
}
