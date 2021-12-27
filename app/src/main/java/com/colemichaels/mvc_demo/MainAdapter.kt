package com.colemichaels.mvc_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.colemichaels.mvc_demo.model.Movie

class MainAdapter(
    val movieList: List<Movie>,
    val context: Context
) : RecyclerView.Adapter<MainAdapter.MoviesHolder>() {
    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        return MoviesHolder(v)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.titleTextView.text = movieList[position].title
        holder.releaseDateTextView.text = movieList[position].releaseDate
        if (movieList[position].getPosterUrl() == null) {
            holder.movieImageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_baseline_local_movies_primary))
        } else {
            Glide.with(context)
                .load(movieList[position].getPosterUrl())
                .into(holder.movieImageView)
        }
    }

    override fun getItemCount(): Int = movieList.size

    inner class MoviesHolder(v: View) : RecyclerView.ViewHolder(v) {
        var titleTextView: TextView = v.findViewById(R.id.item_movie_title_textview)
        var releaseDateTextView: TextView = v.findViewById(R.id.item_movie_release_date_textview)
        var movieImageView: ImageView = v.findViewById(R.id.item_movie_imageview)
        var checkBox: CheckBox = v.findViewById(R.id.item_movie_checkbox)

        init {
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(movieList[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(movieList[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.remove(movieList[adapterPosition])
                }
            }
        }
    }
}