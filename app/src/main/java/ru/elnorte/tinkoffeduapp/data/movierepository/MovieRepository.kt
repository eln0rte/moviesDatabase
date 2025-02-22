package ru.elnorte.tinkoffeduapp.data.movierepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.elnorte.tinkoffeduapp.data.models.FavDatabaseModel
import ru.elnorte.tinkoffeduapp.data.models.asMovieInfoDataModel
import ru.elnorte.tinkoffeduapp.data.models.asMovieOverviewDataModel
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieInfoDataModel
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieOverviewDataModel
import ru.elnorte.tinkoffeduapp.data.movierepository.database.FavDatabaseDao
import ru.elnorte.tinkoffeduapp.data.movierepository.network.MovieApiService
import ru.elnorte.tinkoffeduapp.ui.models.MovieInfoDataModel
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val dao: FavDatabaseDao,
    private val api: MovieApiService
) {

    suspend fun getMovie(id: Int): MovieInfoDataModel {
        return withContext(Dispatchers.IO) {
            if (dao.checkIfExists(id)) {
                dao.get(id).asMovieInfoDataModel()
            } else {
                api.getMovie(id).asMovieInfoDataModel()
            }
        }
    }

    suspend fun getTopMovies(onlyFavs: Boolean): List<MovieOverviewDataModel> {
        return withContext(Dispatchers.IO) {
            val dbData = dao.getAll().map { it.asMovieOverviewDataModel() }
            if (onlyFavs) {
                dbData
            } else {
                val dbIds = dbData.map { it.id }.toSet()
                val apiData = api.getTopMovies().asMovieOverviewDataModel()

                (dbData + apiData.filterNot { it.id in dbIds })
                    .sortedBy { it.id }
            }

        }
    }

    suspend fun addFav(id: Int) {
        if (!dao.checkIfExists(id)) {
            val movieInfo = api.getMovie(id)
            dao.insert(
                FavDatabaseModel(
                    id = movieInfo.kinopoiskId,
                    poster = movieInfo.posterUrl,
                    posterSmall = movieInfo.posterUrlPreview ?: movieInfo.posterUrl,
                    title = movieInfo.nameRu,
                    info = "${movieInfo.genres[0].genre} (${movieInfo.year})",
                    description = movieInfo.description,
                    genre = "Жанры: ${movieInfo.genres.joinToString(", ") { it.genre }}",
                    origin = "Страны: ${movieInfo.countries.joinToString(", ") { it.country }}"
                )
            )
        } else {
            dao.delete(id)
        }


    }
}
//todo remove this comment
//genre = "Жанры: ${this.genres.joinToString(", "){it.genre}}",
//origin = "Страны: ${this.countries.joinToString(", ") { it.country }}"
//info = "${it.genres[0].genre} (${it.year})"
