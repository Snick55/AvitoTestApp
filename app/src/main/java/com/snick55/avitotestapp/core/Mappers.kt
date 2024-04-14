package com.snick55.avitotestapp.core

import com.snick55.avitotestapp.data.entities.Doc
import com.snick55.avitotestapp.data.entities.MovieCloud
import com.snick55.avitotestapp.data.entities.ReviewsCloud
import com.snick55.avitotestapp.domain.entities.Actor
import com.snick55.avitotestapp.domain.entities.Movie
import com.snick55.avitotestapp.domain.entities.MovieDetail
import com.snick55.avitotestapp.domain.entities.Review


fun Doc.toMovie() = Movie(
    ageRating = ageRating,
    countries = countries.map {
        it.name
    },
    description = description,
    id = id,
    name = name,
    poster = poster?.url ?: "",
    rating = rating.imdb,
    shortDescription = shortDescription ?: "",
    year = year
)


fun MovieCloud.toMovieDetails() = MovieDetail(
    name = name,
    rating = rating.imdb,
    description = description ?: "нет информации",
    actors = persons.map {
        Actor(
            name = it.name ?: "нет информации",
            description = it.description ?: "нет информации",
            photo = it.photo
        )
    },
    posters = listOf(poster.previewUrl, backdrop.url)
)

fun ReviewsCloud.Doc.toReview() = Review(
    author = author, createdAt = createdAt ?: "нет информации", review = review
)