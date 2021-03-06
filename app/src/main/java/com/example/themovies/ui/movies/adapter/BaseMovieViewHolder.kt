package com.example.themovies.ui.movies.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.model.domain.Movie
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager

abstract class BaseMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        val layoutParams = itemView.layoutParams
        if (layoutParams is FlexboxLayoutManager.LayoutParams) {
            layoutParams.flexShrink = 0.0f
            layoutParams.alignSelf =
                AlignItems.FLEX_START // this will align each itemView on Top or use AlignItems.FLEX_END to align it at Bottom
        }
    }

    abstract fun bind(movie: Movie)
}