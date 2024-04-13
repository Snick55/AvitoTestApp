package com.snick55.avitotestapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.snick55.avitotestapp.core.toReview
import com.snick55.avitotestapp.data.entities.ReviewsCloud
import com.snick55.avitotestapp.di.IoDispatcher
import com.snick55.avitotestapp.domain.Review
import com.snick55.avitotestapp.domain.ReviewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val errorHandler: ErrorHandler
): ReviewsRepository {

    override  fun getPagedReviewsForMovie(id: Int): Flow<PagingData<Review>> {
        val loader = object : Loader<ReviewsCloud.Doc> {
            override suspend fun load(pageIndex: Int, pageSize: Int): List<ReviewsCloud.Doc> {
                return getReviewsForMovie(pageIndex, pageSize,id)
            }
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { MoviesPagingSource(loader, errorHandler) }
        ).flow.map {paging->
            paging.map {reviewCloud->
                reviewCloud.toReview()
            }
        }

    }

    private suspend fun getReviewsForMovie(pageIndex: Int, pageSize: Int,id: Int): List<ReviewsCloud.Doc> =
        withContext(ioDispatcher) {
            delay(2000)
            val reviewsResponse = moviesApi.getReviews(pageIndex, pageSize,id)
            return@withContext reviewsResponse.docs
        }



    private companion object{
        private const val PAGE_SIZE = 7
    }
}