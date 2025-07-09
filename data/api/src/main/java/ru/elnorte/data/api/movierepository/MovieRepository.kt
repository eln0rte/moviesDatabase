package ru.elnorte.data.api.movierepository



import ru.elnorte.data.api.models.MovieResult
import ru.elnorte.data.api.models.OverviewResult


interface MovieRepository {
    suspend fun getMovie(id: Int): MovieResult

    suspend fun getTopMovies(): OverviewResult

    suspend fun getFavMovies(): OverviewResult

    suspend fun toggleFav(id: Int)

    suspend fun getNetworkMovie(id: Int): MovieResult

    suspend fun getCachedMovie(id: Int): MovieResult

}
