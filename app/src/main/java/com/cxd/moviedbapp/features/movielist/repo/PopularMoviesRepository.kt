package com.cxd.moviedbapp.features.movielist.repo

import androidx.paging.*
import com.cxd.moviedbapp.common.db.AppDataBase
import com.cxd.moviedbapp.common.models.domain.Movie
import com.cxd.moviedbapp.features.popular.datasource.local.PopularMovieEntity
import com.cxd.moviedbapp.features.movielist.datasource.remote.MovieListService
import com.cxd.moviedbapp.features.popular.paging.PopularMoviesRemoteMediator
import com.cxd.moviedbapp.features.upcoming.datasource.local.UpcomingMovieEntity
import com.cxd.moviedbapp.features.upcoming.paging.UpcomingMoviesRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepository @Inject constructor(
    private val service: MovieListService,
    private val db: AppDataBase
): MovieListRepo {
    private val popularPagingSourceFactory = { db.popularMovieDao.getPopularMovies() }
    private val upcomingPagingSourceFactory = { db.upcomingMovieDao.getUpcomingMovies() }

    /**
     * for caching
     */
    @ExperimentalPagingApi
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = PopularMoviesRemoteMediator(
                service,
                db
            ),
            pagingSourceFactory = popularPagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                mapPopularEntityMovieToDomain(entity = entity)
            }
        }
    }

    @ExperimentalPagingApi
    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = UpcomingMoviesRemoteMediator(
                service,
                db
            ),
            pagingSourceFactory = upcomingPagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                mapUpcomingEntityMovieToDomain(entity = entity)
            }
        }
    }

    private fun mapUpcomingEntityMovieToDomain(entity: UpcomingMovieEntity):Movie {
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

    private fun mapPopularEntityMovieToDomain(entity: PopularMovieEntity):Movie {
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