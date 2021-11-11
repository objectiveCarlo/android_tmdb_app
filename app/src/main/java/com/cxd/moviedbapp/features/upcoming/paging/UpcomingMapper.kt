package com.cxd.moviedbapp.features.upcoming.paging

import com.cxd.moviedbapp.common.models.data.MovieResponse
import com.cxd.moviedbapp.features.upcoming.datasource.local.UpcomingMovieEntity

fun mapRemoteMovieToUpcomingMovie(
    remoteMovie: MovieResponse
): UpcomingMovieEntity {
    return UpcomingMovieEntity(
        id = remoteMovie.id,
        isAdultOnly = remoteMovie.isAdultOnly,
        popularity = remoteMovie.popularity,
        voteAverage = remoteMovie.voteAverage,
        voteCount = remoteMovie.voteCount,
        image = remoteMovie.image ?: remoteMovie.backdropImage ?: "",
        title = remoteMovie.title ?: remoteMovie.originalTitle
        ?: remoteMovie.originalTitleAlternative
        ?: "No title found",
        overview = remoteMovie.overview,
        releaseDate = remoteMovie.releaseDate ?: remoteMovie.releaseDateAlternative
        ?: "No date found",
        originalLanguage = remoteMovie.originalLanguage ?: ""
    )
}