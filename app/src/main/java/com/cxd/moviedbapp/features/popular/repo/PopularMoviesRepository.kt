package com.cxd.moviedbapp.features.popular.repo

import androidx.paging.*
import com.cxd.moviedbapp.common.db.AppDataBase
import com.cxd.moviedbapp.common.models.domain.Movie
import com.cxd.moviedbapp.features.popular.datasource.local.PopularMovieEntity
import com.cxd.moviedbapp.features.popular.datasource.remote.PopularMoviesService
import com.cxd.moviedbapp.features.popular.paging.PopularMoviesRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepository @Inject constructor(
    private val service: PopularMoviesService,
    private val db: AppDataBase
){
    private val pagingSourceFactory = { db.popularMovieDao.getPopularMovies() }

    /**
     * for caching
     */
    @ExperimentalPagingApi
    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = PopularMoviesRemoteMediator(
                service,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                mapEntityMovieToDomain(entity = entity)
            }
        }
    }

    private fun mapEntityMovieToDomain(entity: PopularMovieEntity):Movie {
        return Movie(
            id = entity.id,
            isAdultOnly = entity.isAdultOnly,
            popularity = entity.popularity,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
            image = entity.image,
            title = entity.title,
            overview = entity.overview,
            releaseDate = entity.releaseDate,
            originalLanguage = entity.originalLanguage
        )
    }

}