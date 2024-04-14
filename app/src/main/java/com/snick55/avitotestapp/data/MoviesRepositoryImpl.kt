package com.snick55.avitotestapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.snick55.avitotestapp.core.Container
import com.snick55.avitotestapp.core.EmptyResponseException
import com.snick55.avitotestapp.core.toMovie
import com.snick55.avitotestapp.core.toMovieDetails
import com.snick55.avitotestapp.data.entities.Doc
import com.snick55.avitotestapp.di.IoDispatcher
import com.snick55.avitotestapp.domain.MoviesRepository
import com.snick55.avitotestapp.domain.entities.Movie
import com.snick55.avitotestapp.domain.entities.MovieDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
 @IoDispatcher  private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: ErrorHandler
) : MoviesRepository {

    override fun getPagedMovies(): Flow<PagingData<Movie>> {
        val loader = object : Loader<Doc> {
            override suspend fun load(pageIndex: Int, pageSize: Int): List<Doc> {
                return getMovies(pageIndex, pageSize)
            }
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { MoviesPagingSource(loader, errorHandler) }
        ).flow
            .map {pagingData->
                pagingData.map {movieDTO->
                    movieDTO.toMovie()
                }
            }
    }

    override fun getFilteredPagedMovies(
        year: String?,
        ageRating: String?,
        countryName: String?
    ): Flow<PagingData<Movie>> {
        val loader = object : Loader<Doc> {
            override suspend fun load(pageIndex: Int, pageSize: Int): List<Doc> {
                return getFilteredMovies(pageIndex, pageSize,year,ageRating,countryName)
            }
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { MoviesPagingSource(loader, errorHandler) }
        ).flow
            .map {pagingData->
                pagingData.map {movieDTO->
                    movieDTO.toMovie()
                }
            }
    }

    private suspend fun getFilteredMovies(
        pageIndex: Int,
        pageSize: Int,
        year: String?,
        ageRating: String?,
        countryName: String?
    ): List<Doc> = withContext(ioDispatcher) {
        val moviesResponse = moviesApi.getFilteredMovies(pageIndex, pageSize,year, ageRating, countryName)
        return@withContext moviesResponse.docs
    }

    override suspend fun getMovieById(id: Int): Container<MovieDetail> {
       return try {
           Container.Success(moviesApi.getMovieById(id).toMovieDetails())
       } catch (e: Exception){
           Container.Error(errorHandler.handle(e))
       }
    }

    override fun getSearchedPagedMovies(query: String): Flow<PagingData<Movie>> {
        val loader = object : Loader<Doc> {
            override suspend fun load(pageIndex: Int, pageSize: Int): List<Doc> {
                return getSearchedMovies(pageIndex, pageSize,query)
            }
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { MoviesPagingSource(loader, errorHandler) }
        ).flow
            .map {pagingData->
                pagingData.map {movieDTO->
                    movieDTO.toMovie()
                }
            }
    }

    private suspend fun getSearchedMovies(pageIndex: Int, pageSize: Int, query: String) = withContext(ioDispatcher){
         val moviesResponse = moviesApi.getSearchedMovies(pageIndex, pageSize,query)
        if (moviesResponse.docs.isEmpty()) throw EmptyResponseException()
        return@withContext moviesResponse.docs
    }

    private suspend fun getMovies(pageIndex: Int, pageSize: Int): List<Doc> =
        withContext(ioDispatcher) {
            val moviesResponse = moviesApi.getMovies(pageIndex, pageSize)
            return@withContext moviesResponse.docs
        }


    private companion object {
        private const val PAGE_SIZE = 20
    }

}