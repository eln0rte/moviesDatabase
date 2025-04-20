package ru.elnorte.tinkoffeduapp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.elnorte.tinkoffeduapp.data.movierepository.network.MovieApiService
import ru.elnorte.tinkoffeduapp.data.remote.MovieRemoteDataSource
import ru.elnorte.tinkoffeduapp.data.remote.MovieRemoteDataSourceImpl
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(
        apiService: MovieApiService
    ): MovieRemoteDataSource = MovieRemoteDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideRetrofitService(
        internetClient: OkHttpClient,
        moshi: Moshi,
    ): MovieApiService {
        return Retrofit.Builder()
            .client(internetClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .addHeader("x-api-key", API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }

    companion object {
        private const val SERVER_URL = "https://kinopoiskapiunofficial.tech/"
        private const val BASE_URL = "$SERVER_URL/api/v2.2/"
        private const val API_KEY = ""
    }
}
