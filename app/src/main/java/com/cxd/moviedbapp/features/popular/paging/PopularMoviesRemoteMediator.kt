package com.cxd.moviedbapp.features.popular.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cxd.moviedbapp.common.db.AppDataBase
import com.cxd.moviedbapp.common.models.data.MovieResponse
import com.cxd.moviedbapp.common.models.paging.RemoteKeysEntity
import com.cxd.moviedbapp.features.popular.datasource.local.PopularMovieEntity
import com.cxd.moviedbapp.features.movielist.datasource.remote.MovieListService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PopularMoviesRemoteMediator(
    private val service: MovieListService,
    private val db: AppDataBase
) : RemoteMediator<Int, PopularMovieEntity>() {
    private val remoteKeysEntityID = 0
    override suspend fun load(loadType: LoadType, state: PagingState<Int, PopularMovieEntity>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (db.popularMovieDao.count() > 0) return MediatorResult.Success(false)
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)

            }
            LoadType.APPEND -> {
                getKey()
            }
        }

        try {
            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: 1
            val apiResponse = service.getPopularMovies("en-US",page)

            val results = apiResponse.results

            val endOfPaginationReached =
                results.isEmpty()

            db.withTransaction {

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                db.remoteKeysEntityDao.insertKey(
                    RemoteKeysEntity(
                        remoteKeysEntityID,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.popularMovieDao.insertBatchMovies(results.map { mapRemoteMovieToPopularMovie(it) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey(): RemoteKeysEntity? {
        return db.remoteKeysEntityDao.remoteKeysRepoId(remoteKeysEntityID)
    }

}