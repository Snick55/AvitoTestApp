package com.snick55.avitotestapp.domain

import androidx.paging.PagingData
import com.snick55.avitotestapp.domain.entities.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
     fun getPagedReviewsForMovie(id: Int) : Flow<PagingData<Review>>

}