package com.snick55.avitotestapp.data.entities

data class MovieCloud(
    val ageRating: Int,
    val alternativeName: String,
    val audience: List<Audience>,
    val backdrop: Backdrop,
    val budget: Budget,
    val countries: List<Country>,
    val deletedAt: Any,
    val description: String,
    val distributors: Distributors,
    val enName: Any,
    val externalId: ExternalId,
    val facts: List<Fact>,
    val fees: Fees,
    val genres: List<Genre>,
    val id: Int,
    val images: Images,
    val imagesInfo: ImagesInfo,
    val isSeries: Boolean,
    val lists: List<String>,
    val logo: Logo,
    val movieLength: Int,
    val name: String,
    val names: List<Name>,
    val networks: Any,
    val persons: List<Person>,
    val poster: Poster,
    val premiere: Premiere,
    val productionCompanies: List<ProductionCompany>,
    val rating: Rating,
    val ratingMpaa: String,
    val seasonsInfo: List<Any>,
    val sequelsAndPrequels: List<Any>,
    val seriesLength: Any,
    val shortDescription: String,
    val similarMovies: List<SimilarMovy>,
    val slogan: String,
    val spokenLanguages: List<SpokenLanguage>,
    val status: Any,
    val technology: Technology,
    val ticketsOnSale: Boolean,
    val top10: Any,
    val top250: Int,
    val totalSeriesLength: Any,
    val type: String,
    val typeNumber: Int,
    val updatedAt: String,
    val videos: Videos,
    val votes: Votes,
    val watchability: Watchability,
    val year: Int
) {
    data class Audience(
        val count: Int,
        val country: String
    )

    data class Backdrop(
        val previewUrl: String,
        val url: String
    )

    data class Budget(
        val currency: String,
        val value: Int
    )

    data class Country(
        val name: String
    )

    data class Distributors(
        val distributor: String,
        val distributorRelease: String
    )

    data class ExternalId(
        val imdb: String,
        val kpHD: String,
        val tmdb: Int
    )

    data class Fact(
        val spoiler: Boolean,
        val type: String,
        val value: String
    )

    data class Fees(
        val russia: Russia,
        val usa: Usa,
        val world: World
    ) {
        data class Russia(
            val currency: String,
            val value: Int
        )

        data class Usa(
            val currency: String,
            val value: Int
        )

        data class World(
            val currency: String,
            val value: Int
        )
    }

    data class Genre(
        val name: String
    )

    data class Images(
        val backdropsCount: Int,
        val framesCount: Int,
        val postersCount: Int
    )

    data class ImagesInfo(
        val framesCount: Int
    )

    data class Logo(
        val url: String
    )

    data class Name(
        val language: String,
        val name: String,
        val type: String
    )

    data class Person(
        val description: String?,
        val enName: String,
        val enProfession: String,
        val id: Int,
        val name: String?,
        val photo: String,
        val profession: String
    )

    data class Poster(
        val previewUrl: String,
        val url: String
    )

    data class Premiere(
        val dvd: String,
        val russia: String,
        val world: String
    )

    data class ProductionCompany(
        val name: String,
        val previewUrl: String,
        val url: String
    )

    data class Rating(
        val await: Any,
        val filmCritics: Double,
        val imdb: Double,
        val kp: Double,
    )

    data class SimilarMovy(
        val alternativeName: String,
        val enName: Any,
        val id: Int,
        val name: String,
        val poster: Poster,
        val rating: Rating,
        val type: String,
        val year: Int
    ) {
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
    }

    data class SpokenLanguage(
        val name: String,
        val nameEn: String
    )

    data class Technology(
        val has3D: Boolean,
        val hasImax: Boolean
    )

    data class Videos(
        val trailers: List<Trailer>
    ) {
        data class Trailer(
            val name: String,
            val site: String,
            val type: String,
            val url: String
        )
    }

    data class Votes(
        val await: Int,
        val filmCritics: Int,
        val imdb: Int,
        val kp: Int,
        val russianFilmCritics: Int
    )

    data class Watchability(
        val items: List<Any>
    )
}