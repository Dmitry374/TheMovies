package com.example.themovies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.themovies.R
import com.example.themovies.ui.movies.MoviesFragment

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    private val moviesFragment = MoviesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, moviesFragment)
                .commit()
        }
    }
}