package com.snick55.avitotestapp.data.entities



data class MoviesResponse(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)

data class Doc(
    val ageRating: Int,
    val countries: List<Country>,
    val description: String,
    val id: Int,
    val name: String,
    val poster: Poster,
    val rating: Rating,
    val shortDescription: String?,
    val year: Int
)

data class Country(
    val name: String
)

data class Poster(
    val previewUrl: String,
    val url: String
)

data class Rating(
    val await: Any,
    val filmCritics: Double,
    val imdb: Double,
    val kp: Double,
    val russianFilmCritics: Double
)