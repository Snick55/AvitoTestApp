package com.snick55.avitotestapp.domain

import androidx.paging.PagingData
import com.snick55.avitotestapp.core.Container
import com.snick55.avitotestapp.domain.entities.Movie
import com.snick55.avitotestapp.domain.entities.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPagedMovies(): Flow<PagingData<Movie>>

    suspend fun getMovieById(id: Int): Container<MovieDetail>

    fun getSearchedPagedMovies(query: String): Flow<PagingData<Movie>>

    fun getFilteredPagedMovies(
        year: String? = null,
        ageRating: String? = null,
        countryName: String? = null,
    ):Flow<PagingData<Movie>>

}