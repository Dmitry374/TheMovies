package com.example.themovies.ui.movies

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovies.App
import com.example.themovies.R
import com.example.themovies.ui.movies.adapter.MoviesAdapter
import com.example.themovies.ui.movies.adapter.SearchMoviesAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MoviesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val moviesViewModel: MoviesViewModel by viewModels {
        viewModelFactory
    }

    private var isNetworkAvailable = true

    private val popularMoviesAdapter = MoviesAdapter { movie ->
        Snackbar.make(moviesLayout, movie.title, Snackbar.LENGTH_SHORT)
            .show()
    }

    private val nowPlayingMoviesAdapter = MoviesAdapter { movie ->
        Snackbar.make(moviesLayout, movie.title, Snackbar.LENGTH_SHORT)
            .show()
    }

    private val searchMoviesAdapter = SearchMoviesAdapter { movie ->
        Snackbar.make(moviesLayout, movie.title, Snackbar.LENGTH_SHORT)
            .show()
    }

    private val inputMethodManager: InputMethodManager by lazy {
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        registerNetworkCallback()

        if (savedInstanceState == null) {
            moviesViewModel.searchMovies()
            moviesViewModel.getPopularMoviesFromDB()
            moviesViewModel.getNowPlayingMoviesFromDB()
        }

//        search

        searchViewMovies.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (isNetworkAvailable) {
                    moviesViewModel.searsNewMovie(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (isNetworkAvailable) {
                    moviesViewModel.searsNewMovie(newText)
                }
                return true
            }
        })

        searchViewMovies.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showSearchResultMovies(true)
            }
        }

        searchViewMovies.setOnCloseListener {
            searchViewMoviesClearFocus()
            showSearchResultMovies(false)
            true
        }

        recyclerSearchMovies.layoutManager = LinearLayoutManager(activity)
        recyclerSearchMovies.adapter = searchMoviesAdapter

        moviesViewModel.searchMovies.observe(viewLifecycleOwner, Observer {
            it?.let { movies ->
                searchMoviesAdapter.submitList(movies)
            }
        })

//        popular movies

        recyclerPopularMovies.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerPopularMovies.adapter = popularMoviesAdapter

        moviesViewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            it?.let { movies ->
                popularMoviesAdapter.submitList(movies)
            }
        })


//        now playing movies

        recyclerNowPlayingMovies.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerNowPlayingMovies.adapter = nowPlayingMoviesAdapter

        moviesViewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer {
            it?.let { movies ->
                nowPlayingMoviesAdapter.submitList(movies)
            }
        })

//        Error loading data

        moviesViewModel.searchMoviesNetError.observe(viewLifecycleOwner, Observer {
            it?.let { isErrorLoading ->
                if (isErrorLoading) {
                    Snackbar.make(
                        moviesLayout,
                        R.string.search_error_data_loading,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })

        moviesViewModel.popularMoviesNetError.observe(viewLifecycleOwner, Observer {
            it?.let { isErrorLoading ->
                if (isErrorLoading) {
                    Snackbar.make(
                        moviesLayout,
                        R.string.popular_movies_error_data_loading,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })

        moviesViewModel.nowPlayingMoviesNetError.observe(viewLifecycleOwner, Observer {
            it?.let { isErrorLoading ->
                if (isErrorLoading) {
                    Snackbar.make(
                        moviesLayout,
                        R.string.now_playing_movies_error_data_loading,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })
    }

    private fun showSearchResultMovies(isShowSearchResult: Boolean) {

        if (!isShowSearchResult) {
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        recyclerSearchMovies.visibility = if (isShowSearchResult) View.VISIBLE else View.GONE
        titlePopularMovies.visibility = if (isShowSearchResult) View.GONE else View.VISIBLE
        recyclerPopularMovies.visibility = if (isShowSearchResult) View.GONE else View.VISIBLE
        titleNowPlayingMovies.visibility = if (isShowSearchResult) View.GONE else View.VISIBLE
        recyclerNowPlayingMovies.visibility = if (isShowSearchResult) View.GONE else View.VISIBLE
    }

    private fun searchViewMoviesClearFocus() {
        searchMoviesAdapter.submitList(listOf())
        searchViewMovies.setQuery("", false)
        searchViewMovies.clearFocus()
        searchViewMovies.onActionViewCollapsed()
    }

    private fun registerNetworkCallback() {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    isNetworkAvailable = false
                    Snackbar.make(
                        moviesLayout,
                        R.string.lost_internet_connection,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAvailable(network: Network) {
                    if (!isNetworkAvailable) {
                        Snackbar.make(
                            moviesLayout,
                            R.string.internet_connection_available,
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                    isNetworkAvailable = true
                }
            })
        }
    }
}