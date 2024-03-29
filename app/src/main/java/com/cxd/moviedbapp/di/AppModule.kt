package com.cxd.moviedbapp.di

import android.content.Context
import com.cxd.moviedbapp.BuildConfig
import com.cxd.moviedbapp.common.dao.RemoteKeysDao
import com.cxd.moviedbapp.common.db.AppDataBase
import com.cxd.moviedbapp.features.favorites.datasource.FavoriteMovieDao
import com.cxd.moviedbapp.features.upcoming.datasource.local.UpcomingMoviesDao
import com.cxd.moviedbapp.features.popular.datasource.local.PopularMoviesDao
import com.cxd.moviedbapp.features.movielist.datasource.remote.MovieListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    internal fun provideClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url
                val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
                request.url(url)
                return@addInterceptor chain.proceed(request.build())
            }
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesDB(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.invoke(context)
    }

    @Singleton
    @Provides
    fun providesKeysDao(appDataBase: AppDataBase): RemoteKeysDao = appDataBase.remoteKeysEntityDao

    @Singleton
    @Provides
    fun providesPopularMoviesDao(appDataBase: AppDataBase): PopularMoviesDao = appDataBase.popularMovieDao

    @Singleton
    @Provides
    fun providesFavoriteMoviesDao(appDataBase: AppDataBase): FavoriteMovieDao = appDataBase.favoriteMovieDao

    @Singleton
    @Provides
    fun providesUpcomingMoviesDao(appDataBase: AppDataBase): UpcomingMoviesDao = appDataBase.upcomingMovieDao


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesMovieAPIService(retrofit: Retrofit): MovieListService = retrofit.create(
        MovieListService::class.java)
}