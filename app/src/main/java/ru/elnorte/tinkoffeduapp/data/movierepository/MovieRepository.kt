package ru.elnorte.tinkoffeduapp.data.movierepository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.elnorte.tinkoffeduapp.data.models.FavDatabaseModel
import ru.elnorte.tinkoffeduapp.data.models.asMovieInfoDataModel
import ru.elnorte.tinkoffeduapp.data.models.asMovieOverviewDataModel
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieInfoDataModel
import ru.elnorte.tinkoffeduapp.data.models.network.asMovieOverviewDataModel
import ru.elnorte.tinkoffeduapp.data.movierepository.database.FavDatabaseDao
import ru.elnorte.tinkoffeduapp.data.movierepository.network.MoviesApi
import ru.elnorte.tinkoffeduapp.ui.models.MovieInfoDataModel
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel

class MovieRepository(private val dao: FavDatabaseDao) {

    suspend fun getMovie(id: Int): MovieInfoDataModel {
        return withContext(Dispatchers.IO) {
            if(dao.checkIfExists(id)){
                dao.get(id).asMovieInfoDataModel()
            }
            else{
                MoviesApi.retrofitService.getMovie(id).asMovieInfoDataModel()
            }

        }

    }

    suspend fun getTopMovies(onlyFavs:Boolean): List<MovieOverviewDataModel> {
        return withContext(Dispatchers.IO) {
            val dbData = dao.getAll().map{it.asMovieOverviewDataModel()}
            if(onlyFavs){
                dbData
            }
            else{
                val data = MoviesApi.retrofitService.getTopMovies().asMovieOverviewDataModel()
                (dbData+ data.filter{mId ->!dbData.contains(dbData.find { it.id == mId.id })}).sortedBy { it.id }
            }

        }
    }

    suspend fun addFav(id: Int) {
        if(!dao.checkIfExists(id)){
            val movieInfo = MoviesApi.retrofitService.getMovie(id)
            dao.insert(
                FavDatabaseModel(
                    id = movieInfo.kinopoiskId,
                    poster = movieInfo.posterUrl,
                    posterSmall = movieInfo.posterUrlPreview ?: movieInfo.posterUrl,
                    title = movieInfo.nameRu,
                    info = "${movieInfo.genres[0].genre} (${movieInfo.year})",
                    description = movieInfo.description,
                    genre = "Жанры: ${movieInfo.genres.joinToString(", "){it.genre}}",
                    origin = "Страны: ${movieInfo.countries.joinToString(", ") { it.country }}"
                )
            )
        }
        else{
            dao.delete(id)
        }


    }
}
//todo remove this comment
//genre = "Жанры: ${this.genres.joinToString(", "){it.genre}}",
//origin = "Страны: ${this.countries.joinToString(", ") { it.country }}"
//info = "${it.genres[0].genre} (${it.year})"