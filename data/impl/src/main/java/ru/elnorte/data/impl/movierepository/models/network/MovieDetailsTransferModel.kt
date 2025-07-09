package ru.elnorte.data.impl.movierepository.models.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsTransferModel(
    @Json(name = "kinopoiskId")
    val kinopoiskId: Int, // 1115471
    @Json(name = "kinopoiskHDId")
    val kinopoiskHDId: Any?, // null
    @Json(name = "imdbId")
    val imdbId: String?, // tt14536120
    @Json(name = "nameRu")
    val nameRu: String, // Мастер и Маргарита
    @Json(name = "nameEn")
    val nameEn: Any?, // null
    @Json(name = "nameOriginal")
    val nameOriginal: Any?, // null
    @Json(name = "posterUrl")
    val posterUrl: String, // https://kinopoiskapiunofficial.tech/images/posters/kp/1115471.jpg
    @Json(name = "posterUrlPreview")
    val posterUrlPreview: String?, // https://kinopoiskapiunofficial.tech/images/posters/kp_small/1115471.jpg
    @Json(name = "coverUrl")
    val coverUrl: Any?, // null
    @Json(name = "logoUrl")
    val logoUrl: Any?, // null
    @Json(name = "reviewsCount")
    val reviewsCount: Int?, // 0
    @Json(name = "ratingGoodReview")
    val ratingGoodReview: Any?, // null
    @Json(name = "ratingGoodReviewVoteCount")
    val ratingGoodReviewVoteCount: Int?, // 0
    @Json(name = "ratingKinopoisk")
    val ratingKinopoisk: Any?, // null
    @Json(name = "ratingKinopoiskVoteCount")
    val ratingKinopoiskVoteCount: Int?, // 0
    @Json(name = "ratingImdb")
    val ratingImdb: Any?, // null
    @Json(name = "ratingImdbVoteCount")
    val ratingImdbVoteCount: Int?, // 0
    @Json(name = "ratingFilmCritics")
    val ratingFilmCritics: Any?, // null
    @Json(name = "ratingFilmCriticsVoteCount")
    val ratingFilmCriticsVoteCount: Int?, // 0
    @Json(name = "ratingAwait")
    val ratingAwait: Double?, // 98.0
    @Json(name = "ratingAwaitCount")
    val ratingAwaitCount: Int?, // 51729
    @Json(name = "ratingRfCritics")
    val ratingRfCritics: Any?, // null
    @Json(name = "ratingRfCriticsVoteCount")
    val ratingRfCriticsVoteCount: Int?, // 0
    @Json(name = "webUrl")
    val webUrl: String?, // https://www.kinopoisk.ru/film/1115471/
    @Json(name = "year")
    val year: Int?, // 2023
    @Json(name = "filmLength")
    val filmLength: Int?, // 157
    @Json(name = "slogan")
    val slogan: Any?, // null
    @Json(name = "description")
    val description: String, // Москва, 1930-е годы. Известный писатель на взлёте своей карьеры внезапно оказывается в центре литературного скандала. Спектакль по его пьесе снимают с репертуара, коллеги демонстративно избегают встречи, в считанные дни он превращается в изгоя. Вскоре после этого, он знакомится с Маргаритой, которая становится его возлюбленной и музой. Воодушевлённый ее любовью и поддержкой, писатель берется за новый роман, в котором персонажи — это люди из его окружения, а главный герой — загадочный Воланд, прообразом которого становится недавний знакомый иностранец. Писатель уходит с головой в мир своего романа и постепенно перестает замечать, как вымысел и реальность сплетаются в одно целое.
    @Json(name = "shortDescription")
    val shortDescription: String?, // null
    @Json(name = "editorAnnotation")
    val editorAnnotation: Any?, // null
    @Json(name = "isTicketsAvailable")
    val isTicketsAvailable: Boolean?, // true
    @Json(name = "productionStatus")
    val productionStatus: String?, // POST_PRODUCTION
    @Json(name = "type")
    val type: String?, // FILM
    @Json(name = "ratingMpaa")
    val ratingMpaa: Any?, // null
    @Json(name = "ratingAgeLimits")
    val ratingAgeLimits: String?, // age18
    @Json(name = "countries")
    val countries: List<Country>,
    @Json(name = "genres")
    val genres: List<Genre>,
    @Json(name = "startYear")
    val startYear: Any?, // null
    @Json(name = "endYear")
    val endYear: Any?, // null
    @Json(name = "serial")
    val serial: Boolean?, // false
    @Json(name = "shortFilm")
    val shortFilm: Boolean?, // false
    @Json(name = "completed")
    val completed: Boolean?, // false
    @Json(name = "hasImax")
    val hasImax: Boolean?, // false
    @Json(name = "has3D")
    val has3D: Boolean?, // false
    @Json(name = "lastSync")
    val lastSync: String? // 2024-01-24T02:34:40.127093
) {
    @JsonClass(generateAdapter = true)
    data class Country(
        @Json(name = "country")
        val country: String // Россия
    )

    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "genre")
        val genre: String // драма
    )
}
