package com.snick55.avitotestapp.di

import com.snick55.avitotestapp.data.ErrorHandler
import com.snick55.avitotestapp.data.MoviesRepositoryImpl
import com.snick55.avitotestapp.data.ReviewsRepositoryImpl
import com.snick55.avitotestapp.domain.MoviesRepository
import com.snick55.avitotestapp.domain.ReviewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindErrorHandler(errorHandler: ErrorHandler.BaseErrorHandler): ErrorHandler

    @Binds
    abstract fun bindMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindReviewsRepository(reviewsRepository: ReviewsRepositoryImpl): ReviewsRepository

}