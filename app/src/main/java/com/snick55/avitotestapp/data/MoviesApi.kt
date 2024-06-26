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

    @GET("v1.4/movie/search")
    suspend fun getSearchedMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") query: String
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

    @GET("v1.4/movie")
    suspend fun getFilteredMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("year") year: String? = null,
        @Query("ageRating") ageRating: String? = null,
        @Query("countries.name") countryName: String? = null,
    ):MoviesResponse

}
