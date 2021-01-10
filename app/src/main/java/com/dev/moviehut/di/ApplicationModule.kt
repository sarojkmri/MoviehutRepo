package com.dev.moviehut.di

import com.dev.moviehut.BuildConfig.BASE_URL
import com.dev.moviehut.data.remote.NetworkApi
import com.dev.moviehut.data.remote.NetworkService
import com.dev.moviehut.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule() {
    @Singleton
    @Provides
    fun provideMoviesAPI(): NetworkApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NetworkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesService(networkApi: NetworkApi): NetworkService {
        return NetworkService(networkApi)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(apiService: NetworkService): MovieRepository {
        return MovieRepository(apiService)
    }

}