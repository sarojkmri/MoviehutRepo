package com.dev.moviehut.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.moviehut.R
import com.dev.moviehut.data.model.Movie
import com.dev.moviehut.utils.MovieListUtil
import com.dev.moviehut.utils.Status
import com.dev.moviehut.viewmodel.MovieViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.movie_list_main.*


class MovieListFragment : Fragment() {
    private val moviesViewModel: MovieViewModel by activityViewModels()
    private lateinit var adapter: MovieAdapter
    private val disposable = CompositeDisposable()
    private lateinit var movieLists: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        movieLists = MovieListUtil.getMovieList(requireContext())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_list_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(requireContext(), listOf())
        disposable.add(
            adapter.clickEvent.subscribe { movie ->
                moviesViewModel.setCurrentMovie(movie)
            }
        )
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 3)
        moviesRecycleView.layoutManager = mLayoutManager
        moviesRecycleView.itemAnimator = DefaultItemAnimator()
        moviesRecycleView.adapter = adapter


        swipeRefreshLayout.setOnRefreshListener {
            getMoviesBySpinnerPosition(movieListSpinner.selectedItemPosition, true)
        }

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item, movieLists
        )
        movieListSpinner.adapter = adapter

        movieListSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                getMoviesBySpinnerPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        moviesViewModel.movies.observe(viewLifecycleOwner, Observer { response ->
            swipeRefreshLayout.isRefreshing = false
            if (response.status == Status.SUCCESS) {
                response.data?.let {
                    updateMovieList(it)
                }
            }
        })
    }

    private fun getMoviesBySpinnerPosition(movieListPosition: Int, isRefresh: Boolean = false) {
        var list = MovieListUtil.MovieList.GET_TOP_RATED
        if (movieLists[movieListPosition] == getString(R.string.get_popular)) {
            list = MovieListUtil.MovieList.GET_POPULAR
        }

        if (isRefresh) {
            moviesViewModel.refresh(list)
        } else {
            moviesViewModel.getMovies(list)
        }

    }

    private fun updateMovieList(movies: List<Movie>) {
        adapter.update(movies)

        adapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
    }
}