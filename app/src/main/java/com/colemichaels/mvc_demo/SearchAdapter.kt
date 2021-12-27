package com.colemichaels.mvc_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.colemichaels.mvc_demo.model.Movie
import com.colemichaels.mvc_demo.network.RetrofitClient

class SearchAdapter(
    val movieList: List<Movie>,
    val context: Context,
    val listener: SearchActivity.RecyclerItemListener
) : RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_details, parent, false)
        val viewHolder = SearchMoviesHolder(view)
        view.setOnClickListener { listener.onItemClick(it, viewHolder.adapterPosition) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) {
        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].getReleaseYearFromDate()
        holder.overviewTextView.text = movieList[position].overview

        if (movieList[position].getPosterUrl() != null) {
            Glide.with(context)
                .load(movieList[position].getPosterUrl())
                .into(holder.movieImageView)
        }
    }

    override fun getItemCount(): Int = movieList.size

    fun getItemAtPosition(p: Int): Movie = movieList[p]

    inner class SearchMoviesHolder(v: View) : RecyclerView.ViewHolder(v) {
        var titleTextView: TextView = v.findViewById(R.id.item_movie_details_title_textview)
        var overviewTextView: TextView = v.findViewById(R.id.item_movie_details_overview)
        var releaseDateTextView: TextView = v.findViewById(R.id.item_movie_details_release_date_textview)
        var movieImageView: ImageView = v.findViewById(R.id.item_movie_details_imageview)

        init {
            v.setOnClickListener {
                listener.onItemClick(it, adapterPosition)
            }
        }
    }
}