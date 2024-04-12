package com.snick55.avitotestapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snick55.avitotestapp.core.Container
import com.snick55.avitotestapp.di.IoDispatcher
import com.snick55.avitotestapp.domain.GetReviewsForMovieUseCase
import com.snick55.avitotestapp.domain.MovieDetail
import com.snick55.avitotestapp.domain.MoviesRepository
import com.snick55.avitotestapp.domain.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
   @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
   private val repository: MoviesRepository,
    private val getReviewsForMovieUseCase: GetReviewsForMovieUseCase
): ViewModel() {

    private val innerMovie = MutableLiveData<Container<MovieDetail>>(Container.Pending)
    val movie: LiveData<Container<MovieDetail>> = innerMovie

    private val innerReviews: MutableStateFlow<PagingData<Review>> =
        MutableStateFlow(PagingData.empty())

    val reviews: StateFlow<PagingData<Review>> = innerReviews.asStateFlow()
    fun getMovieDetails(id: Int) = viewModelScope.launch(ioDispatcher){
        delay(2000)
       innerMovie.postValue(repository.getMovieById(id))
    }

    fun getReviews(id: Int) = viewModelScope.launch(ioDispatcher) {
        getReviewsForMovieUseCase.execute(id).cachedIn(viewModelScope).collectLatest {
            innerReviews.emit(it)
        }
    }

}