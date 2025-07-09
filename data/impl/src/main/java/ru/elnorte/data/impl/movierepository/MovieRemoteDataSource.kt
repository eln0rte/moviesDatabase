package ru.elnorte.data.impl.movierepository

import ru.elnorte.data.impl.movierepository.models.network.MovieDetailsTransferModel
import ru.elnorte.data.impl.movierepository.models.network.TopMoviesTransferModel

interface MovieRemoteDataSource {
    suspend fun getTopMovies(): Result<TopMoviesTransferModel>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsTransferModel>
}
