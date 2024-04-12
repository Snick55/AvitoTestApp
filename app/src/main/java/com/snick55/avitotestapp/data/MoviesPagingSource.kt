package com.snick55.avitotestapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.snick55.avitotestapp.core.log

class MoviesPagingSource<T : Any>(
    private val loader: Loader<T>,
    private val errorHandler: ErrorHandler
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageIndex = params.key ?: 1

        return try {
            val movies = loader.load(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if (movies.size == params.loadSize)
                    pageIndex + 1
                else
                    null


            )
        } catch (e: Exception) {
            log("error = ${e.message}")
            LoadResult.Error(throwable = errorHandler.handle(e))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }


}