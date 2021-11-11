package com.cxd.moviedbapp.features.popular.paging

import com.cxd.moviedbapp.common.models.data.MovieResponse
import com.cxd.moviedbapp.features.popular.datasource.local.PopularMovieEntity
import junit.framework.TestCase
import org.junit.Assert

class PopularMapperKtTest : TestCase() {
    lateinit var  movieResponse: MovieResponse

    override fun setUp() {
        movieResponse = MovieResponse(id = 1,
            isAdultOnly = false,
            popularity = 1.0,
            voteAverage = 18.0,
            voteCount= 3,
            overview= "Good movie",
            title= "Custom Title")
    }
    fun testMapRemoteMovieToUpcomingMovie_allValid() {
        val entity = mapRemoteMovieToPopularMovie(remoteMovie = movieResponse)
        reusableTests(entity)
    }

    fun testMapRemoteMovieToUpcomingMovie_ImageSelection_useImage() {
        val expectedMovie =  MovieResponse(id = movieResponse.id,
            isAdultOnly = movieResponse.isAdultOnly,
            popularity = movieResponse.popularity,
            voteAverage = movieResponse.voteAverage,
            voteCount= movieResponse.voteCount,
            overview= movieResponse.overview,
            image = "http://image.com",
            title= movieResponse.title)

        val entity = mapRemoteMovieToPopularMovie(remoteMovie = expectedMovie)
        reusableTests(entity)

        Assert.assertEquals(expectedMovie.image, entity.image)
    }

    fun testMapRemoteMovieToUpcomingMovie_ImageSelection_useBackdropImage() {
        val expectedMovie =  MovieResponse(id = movieResponse.id,
            isAdultOnly = movieResponse.isAdultOnly,
            popularity = movieResponse.popularity,
            voteAverage = movieResponse.voteAverage,
            voteCount= movieResponse.voteCount,
            overview= movieResponse.overview,
            backdropImage = "http://image.com",
            title= movieResponse.title)

        val entity = mapRemoteMovieToPopularMovie(remoteMovie = expectedMovie)
        reusableTests(entity)

        Assert.assertEquals(expectedMovie.backdropImage, entity.image)
    }

    private fun reusableTests(actual: PopularMovieEntity) {
        Assert.assertEquals(movieResponse.isAdultOnly, actual.isAdultOnly)
        Assert.assertEquals(movieResponse.popularity, actual.popularity, 0.0)
        Assert.assertEquals(movieResponse.voteAverage, actual.voteAverage,0.0)
        Assert.assertEquals(movieResponse.voteCount, actual.voteCount)
        Assert.assertEquals(movieResponse.overview, actual.overview)
        Assert.assertEquals(movieResponse.title, actual.title)
    }
}