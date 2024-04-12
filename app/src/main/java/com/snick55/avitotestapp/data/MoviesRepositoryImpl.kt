package com.snick55.avitotestapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.snick55.avitotestapp.core.Container
import com.snick55.avitotestapp.core.toMovie
import com.snick55.avitotestapp.core.toMovieDetails
import com.snick55.avitotestapp.data.entities.Doc
import com.snick55.avitotestapp.di.IoDispatcher
import com.snick55.avitotestapp.domain.Movie
import com.snick55.avitotestapp.domain.MovieDetail
import com.snick55.avitotestapp.domain.MoviesRepository
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
    override suspend fun getMovieById(id: Int): Container<MovieDetail> {
       return try {
           Container.Success(moviesApi.getMovieById(id).toMovieDetails())
       } catch (e: Exception){
           Container.Error(errorHandler.handle(e))
       }
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