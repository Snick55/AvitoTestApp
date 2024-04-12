package com.snick55.avitotestapp.core

import com.snick55.avitotestapp.data.entities.Doc
import com.snick55.avitotestapp.data.entities.MovieCloud
import com.snick55.avitotestapp.data.entities.ReviewsCloud
import com.snick55.avitotestapp.domain.Actor
import com.snick55.avitotestapp.domain.Movie
import com.snick55.avitotestapp.domain.MovieDetail
import com.snick55.avitotestapp.domain.Review


fun Doc.toMovie() = Movie(
    ageRating = ageRating,
    countries = countries.map {
        it.name
    },
    description = description,
    id = id,
    name = name,
    poster = poster.url,
    rating = rating.imdb,
    shortDescription = shortDescription ?: "",
    year = year
)


fun MovieCloud.toMovieDetails() = MovieDetail(
    name = name, rating = rating.imdb, description = description, actors = persons.map {
        Actor(name = it.name?:"нет информации", description = it.description?:"нет информации", photo = it.photo)
    }, posters = listOf(poster.previewUrl,backdrop.url)
)

fun ReviewsCloud.Doc.toReview() = Review(
    author =  author, createdAt =  createdAt, review =  review
)