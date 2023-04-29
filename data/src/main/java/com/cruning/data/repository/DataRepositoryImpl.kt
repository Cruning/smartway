package com.cruning.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cruning.data.rest.api.RestApi
import com.cruning.domain.model.ImageUrl
import com.cruning.domain.repository.DataRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class DataRepositoryImpl @Inject constructor(
    private val restApi: RestApi
) : DataRepository {

    override fun getData() = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            NewsPagingSource(restApi)
        }
    ).flow

    inner class NewsPagingSource(
        private val newsApiService: RestApi,
    ) : PagingSource<Int, ImageUrl>() {
        override fun getRefreshKey(state: PagingState<Int, ImageUrl>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageUrl> {
            return try {
                val page = params.key ?: 1
                val response = newsApiService.getPhotos(
                    authHeader = id,
                    page = page,
                    per_page = count_photos,
                ).map {
                    ImageUrl(
                        url = it.urls.raw
                    )
                }
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.isEmpty()) null else page + 1,
                )
            } catch (e: IOException) {
                LoadResult.Error(e)
            } catch (e: HttpException) {
                LoadResult.Error(e)
            }
        }


    }

    companion object {
        private const val id = "Client-ID EszGLM0Qv6WmOvjO7Bhmk3tv_Ls84VjZjhFkcCLCFUo"
        private const val count_photos = 20
    }
}
