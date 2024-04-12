package com.snick55.avitotestapp.data

import com.snick55.avitotestapp.data.entities.MovieCloud
import com.snick55.avitotestapp.data.entities.MoviesResponse
import com.snick55.avitotestapp.data.entities.ReviewsCloud
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesApi {


    @GET("v1.4/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): MoviesResponse

    @GET("v1.4/movie/{id}")
    suspend fun getMovieById(
        @Path("id") movieId: Int
    ): MovieCloud

    @GET("v1.4/review")
    suspend fun getReviews(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("movieId") movieId: Int
    ): ReviewsCloud

}
