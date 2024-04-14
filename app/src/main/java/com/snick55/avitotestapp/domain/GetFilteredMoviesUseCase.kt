package com.snick55.avitotestapp.domain

import androidx.paging.PagingData
import com.snick55.avitotestapp.domain.entities.Filter
import com.snick55.avitotestapp.domain.entities.FilterType
import com.snick55.avitotestapp.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetFilteredMoviesUseCase {
    fun execute(filter: Filter): Flow<PagingData<Movie>>


    class GetFilteredMoviesUseCaseImpl @Inject constructor(
       private val repository: MoviesRepository
    ): GetFilteredMoviesUseCase{

        override fun execute(filter: Filter): Flow<PagingData<Movie>> {
            val filterType = filter.filterBy
            val filterValue = filter.filterValue
           return when(filterType){
                FilterType.EMPTY -> repository.getPagedMovies()
                FilterType.AGE -> repository.getFilteredPagedMovies(ageRating = filterValue)
                FilterType.YEAR-> repository.getFilteredPagedMovies(year = filterValue)
                FilterType.COUNTRY -> repository.getFilteredPagedMovies(countryName = filterValue)
            }
        }
    }
}