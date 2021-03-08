package com.example.themovies.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.themovies.LiveDataTestUtil
import com.example.themovies.domain.MoviesInteractor
import com.example.themovies.model.domain.Movie
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MoviesViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesInteractor: MoviesInteractor
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movies: List<Movie>

    @Before
    fun setUpMoviesViewModel() {
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        moviesViewModel = MoviesViewModel(moviesInteractor)

        // We initialise list of 3 movies
        val movie1 = Movie(
            adult = false,
            backdropPath = "/fev8UFNFFYsD5q7AcYS8LyTzqwl.jpg",
            id = 587807,
            originalLanguage = "en",
            originalTitle = "Tom & Jerry",
            overview = "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel",
            posterPath = "/6KErczPBROQty7QoIsaa6wJYXZi.jpg",
            releaseDate = "2021-02-25",
            title = "Tom & Jerry",
            video = false,
            voteAverage = 7.8,
            voteCount = 676
        )

        val movie2 = Movie(
            adult = false,
            backdropPath = "/7prYzufdIOy1KCTZKVWpjBFqqNr.jpg",
            id = 527774,
            originalLanguage = "en",
            originalTitle = "Raya and the Last Dragon",
            overview = "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony.",
            posterPath = "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
            releaseDate = "2021-03-05",
            title = "Raya and the Last Dragon",
            video = false,
            voteAverage = 8.7,
            voteCount = 521
        )

        val movie3 = Movie(
            adult = false,
            backdropPath = "/3ombg55JQiIpoPnXYb2oYdr6DtP.jpg",
            id = 560144,
            originalLanguage = "en",
            originalTitle = "Skylines",
            overview = "When a virus threatens to turn the now earth-dwelling friendly alien hybrids against humans",
            posterPath = "/2W4ZvACURDyhiNnSIaFPHfNbny3.jpg",
            releaseDate = "2021-03-11",
            title = "Skylines",
            video = false,
            voteAverage = 6.1,
            voteCount = 208
        )

        movies = listOf(movie1, movie2, movie3)
    }

    @Test
    fun getPopularMoviesFromDB_dataLoaded() {

        `when`(moviesInteractor.getPopularMovies()).thenReturn(Single.just(movies))

        `when`(moviesInteractor.loadPopularMovies()).thenReturn(Single.just(movies))

        moviesViewModel.getPopularMoviesFromDB()

        verify<MoviesInteractor>(moviesInteractor).getPopularMovies()

        verify<MoviesInteractor>(moviesInteractor).loadPopularMovies()

        // Data loaded
        Assert.assertFalse(LiveDataTestUtil.getValue(moviesViewModel.popularMovies).isEmpty())
        Assert.assertTrue(LiveDataTestUtil.getValue(moviesViewModel.popularMovies).size == 3)
    }
}