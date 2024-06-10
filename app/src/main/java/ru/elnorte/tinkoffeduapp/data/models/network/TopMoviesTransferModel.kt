package ru.elnorte.tinkoffeduapp.data.models.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.elnorte.tinkoffeduapp.ui.models.MovieOverviewDataModel

@JsonClass(generateAdapter = true)
data class TopMoviesTransferModel(
    @Json(name = "pagesCount")
    val pagesCount: Int?, // 35
    @Json(name = "films")
    val films: List<Film>
) {
    @JsonClass(generateAdapter = true)
    data class Film(
        @Json(name = "filmId")
        val filmId: Int, // 1115471
        @Json(name = "nameRu")
        val nameRu: String, // Мастер и Маргарита
        @Json(name = "nameEn")
        val nameEn: String?, // Dogman
        @Json(name = "year")
        val year: String, // 2023
        @Json(name = "filmLength")
        val filmLength: String?, // 02:37
        @Json(name = "countries")
        val countries: List<Country>,
        @Json(name = "genres")
        val genres: List<Genre>,
        @Json(name = "rating")
        val rating: String?, // 98.0%
        @Json(name = "ratingVoteCount")
        val ratingVoteCount: Int?, // 0
        @Json(name = "posterUrl")
        val posterUrl: String, // https://kinopoiskapiunofficial.tech/images/posters/kp/1115471.jpg
        @Json(name = "posterUrlPreview")
        val posterUrlPreview: String, // https://kinopoiskapiunofficial.tech/images/posters/kp_small/1115471.jpg
        @Json(name = "ratingChange")
        val ratingChange: Any?, // null
        @Json(name = "isRatingUp")
        val isRatingUp: Any?, // null
        @Json(name = "isAfisha")
        val isAfisha: Int? // 0
    ) {
        @JsonClass(generateAdapter = true)
        data class Country(
            @Json(name = "country")
            val country: String? // Россия
        )

        @JsonClass(generateAdapter = true)
        data class Genre(
            @Json(name = "genre")
            val genre: String? // драма
        )
    }
}

fun TopMoviesTransferModel.asMovieOverviewDataModel(): List<MovieOverviewDataModel> = films.map {
    MovieOverviewDataModel(
        id = it.filmId,
        poster = it.posterUrlPreview,
        title = it.nameRu,
        info = "${it.genres[0].genre} (${it.year})",
        isFavourite = false
    )
}
