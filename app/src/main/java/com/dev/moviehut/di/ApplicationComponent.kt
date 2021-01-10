package com.dev.moviehut.di

import com.dev.moviehut.data.remote.NetworkService
import com.dev.moviehut.data.repository.MovieRepository
import com.dev.moviehut.viewmodel.MovieViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun injectNetworkService(networkService: NetworkService)
    fun injectMovieRepository(movieRepository: MovieRepository)
    fun injectMovieViewModel(movieViewModel: MovieViewModel)
}
