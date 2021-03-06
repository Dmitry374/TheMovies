package com.example.themovies.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovies.R
import com.example.themovies.common.Constants
import com.example.themovies.model.domain.Movie

class MoviesAdapter(private val clickMovieListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(photos: List<Movie>) {
        differ.submitList(photos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        ) { position ->
            clickMovieListener(differ.currentList[position])
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val photo = differ.currentList[position]
        holder.bind(photo)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    class MovieViewHolder(
        itemView: View,
        private val onMovieClick: (Int) -> Unit
    ) : BaseMovieViewHolder(itemView) {

        override fun bind(movie: Movie) {

            itemView.setOnClickListener { onMovieClick(absoluteAdapterPosition) }

            val imageMovieBanner = itemView.findViewById<ImageView>(R.id.imageMovieBanner)

            Glide.with(imageMovieBanner)
                .load(Constants.IMAGE_BASE_URL + movie.posterPath)
                .thumbnail(0.5f)
                .into(imageMovieBanner)

            itemView.findViewById<TextView>(R.id.movieTitle).text = movie.title

            itemView.findViewById<TextView>(R.id.movieVoteAverage).text =
                movie.voteAverage.toString()
        }

    }
}