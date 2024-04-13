package com.snick55.avitotestapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snick55.avitotestapp.domain.Filter
import com.snick55.avitotestapp.domain.FilterType
import com.snick55.avitotestapp.domain.GetFilteredMoviesUseCase
import com.snick55.avitotestapp.domain.Movie
import com.snick55.avitotestapp.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val getFilteredMoviesUseCase: GetFilteredMoviesUseCase
) : ViewModel() {

    private var job: Job? = null
    private var searchedJob: Job? = null
    private var filterJob: Job? = null

    private val innerMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> = innerMovies.asStateFlow()

    private val filterMutableLiveData = MutableLiveData(Filter("",FilterType.EMPTY,""))

    private val search = MutableLiveData("")


    init {
        loadMovies()
        search()
        getFilteredMovies()
    }


    fun setFilter(filter: Filter){
        if ( this.filterMutableLiveData.value == filter) return
        this.filterMutableLiveData.value = filter
    }
    fun setSearch(search: String) {
        if (this.search.value == search) return
        this.search.value = search
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun search() = viewModelScope.launch {
        if (searchedJob?.isActive == true) {
            searchedJob?.cancel()
        }
        searchedJob = viewModelScope.launch {
            search.asFlow()
                .debounce(1000)
                .flatMapLatest {
                    repository.getSearchedPagedMovies(it)
                }
                .cachedIn(viewModelScope)
                .collect {
                    innerMovies.value = it
                }
        }

    }

    private fun loadMovies() = viewModelScope.launch {
        if (job?.isActive == true) {
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
        searchedJob?.cancel()
        filterJob?.cancel()
        if (filterMutableLiveData.value?.filterBy != FilterType.EMPTY)
            getFilteredMovies() else {
            if (search.value.isNullOrEmpty())
                loadMovies()
            else
                search()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getFilteredMovies() = viewModelScope.launch {
        if (filterJob?.isActive == true) {
            filterJob?.cancel()
        }
        filterMutableLiveData.asFlow()
            .flatMapLatest {
                getFilteredMoviesUseCase.execute(it)
            }
            .cachedIn(viewModelScope)
            .collect{
                innerMovies.value = it
            }
    }

}