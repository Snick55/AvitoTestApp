package com.snick55.avitotestapp.domain

data class MovieDetail(
    val name: String,
    val rating: Double,
    val description: String,
    val actors: List<Actor>,
    val posters:List<String>
)
