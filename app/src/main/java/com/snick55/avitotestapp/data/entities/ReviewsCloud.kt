package com.snick55.avitotestapp.data.entities

data class ReviewsCloud(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
) {
    data class Doc(
        val author: String,
        val authorId: Int,
        val createdAt: String?,
        val date: String,
        val id: Int,
        val movieId: Int,
        val review: String,
        val reviewDislikes: Int,
        val reviewLikes: Int,
        val title: String,
        val type: String,
        val updatedAt: String,
        val userRating: Int
    )
}