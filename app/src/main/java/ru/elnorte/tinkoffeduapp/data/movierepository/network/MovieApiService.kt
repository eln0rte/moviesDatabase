package ru.elnorte.tinkoffeduapp.data.movierepository.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.elnorte.tinkoffeduapp.data.models.network.MovieDetailsTransferModel
import ru.elnorte.tinkoffeduapp.data.models.network.TopMoviesTransferModel

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val keyInterceptor = Interceptor{chain ->
    val newRequest = chain.request()
        .newBuilder()
        .addHeader("x-api-key", API_KEY)
        .build()
    chain.proceed(newRequest)
}


private val internetClient = OkHttpClient().newBuilder()
    .addInterceptor(keyInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(internetClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getTopMovies(): TopMoviesTransferModel
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovie(@Path("id") movieId: Int): MovieDetailsTransferModel
}

object MoviesApi{
    val retrofitService : MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}