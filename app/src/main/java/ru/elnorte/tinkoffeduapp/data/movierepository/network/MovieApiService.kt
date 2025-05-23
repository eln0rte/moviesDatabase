package ru.elnorte.tinkoffeduapp.data.movierepository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.elnorte.tinkoffeduapp.data.models.network.MovieDetailsTransferModel
import ru.elnorte.tinkoffeduapp.data.models.network.TopMoviesTransferModel

interface MovieApiService {
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    fun getTopMovies(): Call<TopMoviesTransferModel>

    @GET("/api/v2.2/films/{id}")
    fun getMovie(@Path("id") movieId: Int): Call<MovieDetailsTransferModel>
}
