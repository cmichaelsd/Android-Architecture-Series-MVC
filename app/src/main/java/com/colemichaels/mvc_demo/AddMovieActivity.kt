package com.colemichaels.mvc_demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.colemichaels.mvc_demo.model.LocalDataSource
import com.colemichaels.mvc_demo.model.Movie
import com.colemichaels.mvc_demo.network.RetrofitClient

class AddMovieActivity : BaseActivity() {
    companion object {
        const val SEARCH_MOVIE_ACTIVITY_REQUEST_CODE = 2
    }

    private lateinit var titleEditText: EditText
    private lateinit var releaseDateEditText: EditText
    private lateinit var movieImageView: ImageView
    private lateinit var dataSource: LocalDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        setupViews()
        dataSource = LocalDataSource(application)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        runOnUiThread {
            titleEditText.setText(data?.getStringExtra(Keys.EXTRA_TITLE))
            releaseDateEditText.setText(data?.getStringExtra(Keys.EXTRA_RELEASE_DATE))
            movieImageView.tag = data?.getStringExtra(Keys.EXTRA_POSTER_PATH)
            Glide.with(this)
                .load(data?.getStringExtra(Keys.EXTRA_POSTER_PATH))
                .override(Target.SIZE_ORIGINAL)
                .into(movieImageView)
        }
    }

    private fun setupViews() {
        titleEditText = findViewById(R.id.activity_add_movie_title)
        releaseDateEditText = findViewById(R.id.activity_add_movie_release_year)
        movieImageView = findViewById(R.id.activity_add_movie_imageview)
    }

    fun goToSearchMovieActivity(v: View) {
        val title = titleEditText.text.toString()
        val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra(Keys.SEARCH_QUERY, title)
        startActivityForResult(intent, SEARCH_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    fun onClickAddMovie(v: View) {
        if (titleEditText.text.isEmpty()) {
            showToast("Movie title cannot be empty")
        } else {
            val title = titleEditText.text.toString()
            val releaseDate = releaseDateEditText.text.toString()
            val posterPath = if (movieImageView.tag != null) movieImageView.tag.toString() else ""

            val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
            dataSource.insert(movie)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}