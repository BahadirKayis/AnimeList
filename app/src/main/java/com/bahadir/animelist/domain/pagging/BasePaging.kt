package com.bahadir.animelist.domain.pagging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.gms.common.api.ApiException
import retrofit2.HttpException

abstract class BasePaging<value : Any> : PagingSource<Int, value>() {
    abstract suspend fun fetchData(page: Int): List<value>
    override fun getRefreshKey(state: PagingState<Int, value>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, value> {
        return try {
            val currentPage = params.key ?: 1
            val response = fetchData(currentPage)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if (response.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: HttpException) {
            Log.e("Paging HttpException", "Error: ${e.message}")
            LoadResult.Error(e)
        } catch (e: ApiException) {
            Log.e("Paging ApiException", "Error: ${e.message}")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("Paging Exception", "Error: ${e.message}")
            LoadResult.Error(e)
        }
    }
}