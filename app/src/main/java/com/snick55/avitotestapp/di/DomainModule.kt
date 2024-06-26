package com.snick55.avitotestapp.di

import com.snick55.avitotestapp.core.DateFormatter
import com.snick55.avitotestapp.domain.GetFilteredMoviesUseCase
import com.snick55.avitotestapp.domain.GetReviewsForMovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetReviewsForMovieUseCase(useCase: GetReviewsForMovieUseCase.GetReviewsForMovieUseCaseImpl): GetReviewsForMovieUseCase

    @Binds
    abstract fun bindDateFormatter(formatter: DateFormatter.DateFormatterImpl): DateFormatter

    @Binds
    abstract fun bindGetFilteredMoviesUseCase(useCase: GetFilteredMoviesUseCase.GetFilteredMoviesUseCaseImpl): GetFilteredMoviesUseCase

}