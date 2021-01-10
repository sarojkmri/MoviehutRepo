package com.dev.moviehut.view

import android.app.ActionBar
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dev.moviehut.R
import com.dev.moviehut.utils.Status
import com.dev.moviehut.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.movie_list.*

class MovieListActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var pagerAdapter: MoviePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_list)

        pagerAdapter = MoviePagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        val actionBar: ActionBar? = actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        observeViewModel()
    }

    private fun observeViewModel() {
        movieViewModel.movies.observe(this, Observer { response ->
            when (response.status) {
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    response.message?.let { errorMessage ->
                        val alertDialogBuilder = AlertDialog.Builder(this)
                        alertDialogBuilder.apply {
                            setTitle("Error")
                            setMessage(errorMessage)
                            setPositiveButton("OK") { dialogInterface, _ ->
                                dialogInterface.dismiss()
                            }
                            show()
                        }
                    }
                }
                else -> {
                    //success
                    progressBar.visibility = View.GONE
                }
            }

        })

        movieViewModel.currentMovie.observe(this, Observer { movie ->
            movie?.let {
                viewPager.currentItem =
                    pagerAdapter.getFragmentPosition(MovieDetailsFragment::class)
            }
        })
    }

    override fun onBackPressed() {
        val mainFragmentPosition = pagerAdapter.getFragmentPosition(MovieListFragment::class)
        if (viewPager.currentItem == mainFragmentPosition) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = mainFragmentPosition
        }
    }
}