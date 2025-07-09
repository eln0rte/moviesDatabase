package ru.elnorte.data.impl.movierepository.remote

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import ru.elnorte.data.impl.movierepository.models.network.MovieDetailsTransferModel
import ru.elnorte.data.impl.movierepository.models.network.TopMoviesTransferModel
import ru.elnorte.data.impl.movierepository.MovieRemoteDataSource
import ru.elnorte.data.impl.movierepository.remote.network.MovieApiService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRemoteDataSource {

    override suspend fun getTopMovies(): Result<TopMoviesTransferModel> {
        return apiService.getTopMovies().safeApiCall()
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsTransferModel> {
        return apiService.getMovie(movieId).safeApiCall()
    }

    private fun <T> Call<T>.safeApiCall(): Result<T> {
        return try {
            val response: Response<T> = execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(NullResponseBodyException())
                }
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class NullResponseBodyException : Exception("Response body is null")
