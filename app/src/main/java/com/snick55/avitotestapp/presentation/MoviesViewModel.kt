package com.snick55.avitotestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snick55.avitotestapp.domain.Movie
import com.snick55.avitotestapp.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
   private val repository: MoviesRepository
): ViewModel() {

   private var job: Job? = null

   private val innerMovies: MutableStateFlow<PagingData<Movie>> =
      MutableStateFlow(PagingData.empty())

   val movies: StateFlow<PagingData<Movie>> = innerMovies.asStateFlow()


   init {
       loadMovies()
   }

   private fun loadMovies() = viewModelScope.launch {
      if (job?.isActive == true){
         job?.cancel()
      }
      job = viewModelScope.launch {
         repository.getPagedMovies()
            .cachedIn(viewModelScope)
            .collect {
               innerMovies.value = it
            }
      }
   }

   fun refresh() {
      job?.cancel()
      loadMovies()
   }

}