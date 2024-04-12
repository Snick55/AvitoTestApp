package com.snick55.avitotestapp.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.snick55.avitotestapp.core.DateFormater
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetReviewsForMovieUseCase {

    fun execute(id: Int): Flow<PagingData<Review>>


    class GetReviewsForMovieUseCaseImpl @Inject constructor
        (private val reviewsRepository: ReviewsRepository, private val dateFormater: DateFormater) :
        GetReviewsForMovieUseCase {
        override fun execute(id: Int): Flow<PagingData<Review>> {
            return reviewsRepository.getPagedReviewsForMovie(id).map {
                it.map {review->
                    review.copy(createdAt = dateFormater.format(review.createdAt))
                }
            }
        }
    }

}