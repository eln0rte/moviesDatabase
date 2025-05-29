package ru.elnorte.data.movierepository

import ru.elnorte.core.models.network.TopMoviesTransferModel
import ru.elnorte.data.models.MovieResult
import ru.elnorte.data.models.OverviewResult


interface MovieRepository {
    suspend fun getMovie(id: Int): MovieResult

    suspend fun getTopMovies(): OverviewResult

    suspend fun getFavMovies(): OverviewResult

    suspend fun toggleFav(id: Int)

    suspend fun getNetworkMovie(id: Int): MovieResult

    suspend fun getCachedMovie(id: Int): MovieResult

    suspend fun markFavs(response: TopMoviesTransferModel): OverviewResult.Success
}
