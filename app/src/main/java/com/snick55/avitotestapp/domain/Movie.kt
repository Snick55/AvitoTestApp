package com.snick55.avitotestapp.domain

data class Movie(
    val ageRating: Int,
    val countries: List<String>,
    val description: String,
    val id: Int,
    val name: String,
    val poster: String,
    val rating: Double,
    val shortDescription: String,
    val year: Int
)
