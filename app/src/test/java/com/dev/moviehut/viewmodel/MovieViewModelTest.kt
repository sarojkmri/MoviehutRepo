package com.dev.moviehut.viewmodel

import com.dev.moviehut.data.repository.MovieRepository
import com.dev.moviehut.utils.MovieListUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


//class MovieViewModelTest {
//    @Rule
//    @JvmField
//    var mockitorule: MockitoRule = MockitoJUnit.rule()
//
//    val movieRepository: MovieRepository = Mockito.mock(MovieRepository::class.java)
//
//    var movieViewModel: MovieViewModel? = null
////    @Rule
////    var taskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setup() {
//        initMocks(this)
//        movieViewModel = MovieViewModel()
//    }
//
//
//    @Test
//    fun testRefresh() {
//        verify(movieViewModel?.refresh(MovieListUtil.MovieList.GET_TOP_RATED))
//    }
//
//}