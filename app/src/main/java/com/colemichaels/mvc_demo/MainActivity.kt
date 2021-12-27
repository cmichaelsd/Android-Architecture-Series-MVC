package com.colemichaels.mvc_demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.colemichaels.mvc_demo.model.LocalDataSource
import com.colemichaels.mvc_demo.model.Movie
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val ADD_MOVIE_ACTIVITY_REQUEST_CODE = 1
    }

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var noMoviesLayout: TextView
    private lateinit var dataSource: LocalDataSource

    private val compositeDisposable = CompositeDisposable()
    private val myMoviesObservable: Observable<List<Movie>> get() = dataSource.allMovies

    private var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        dataSource = LocalDataSource(application)
        getMyMoviesList()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            showToast("Movie successfully added.")
        } else {
            displayError("Movie could not be added.")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenuItem) {
            val adapter = this.adapter
            if (adapter != null) {
                for (movie in adapter.selectedMovies) {
                    dataSource.delete(movie)
                }

                if (adapter.selectedMovies.size == 1) {
                    showToast("Movie deleted")
                } else if (adapter.selectedMovies.size > 1) {
                    showToast("Movies deleted")
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun setupViews() {
        moviesRecyclerView = findViewById(R.id.activity_main_recyclerview)
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        fab = findViewById(R.id.activity_main_fab)
        noMoviesLayout = findViewById(R.id.activity_main_no_movies_layout)
        supportActionBar?.title = "Movies to Watch"
    }

    private fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Movie>>() {
                override fun onNext(t: List<Movie>) {
                    displayMovies(t)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "Error: $e")
                    e.printStackTrace()
                    displayError("Error fetching movie list")
                }

                override fun onComplete() {
                    Log.d(TAG, "Completed")
                }
            })
        compositeDisposable.add(myMoviesDisposable)
    }

    private fun displayMovies(moviesList: List<Movie>?) {
        if (moviesList == null || moviesList.isEmpty()) {
            Log.d(TAG, "No movies to display")
            moviesRecyclerView.visibility = View.INVISIBLE
            noMoviesLayout.visibility = View.VISIBLE
        } else {
            adapter = MainAdapter(moviesList, this)
            moviesRecyclerView.adapter = adapter
            moviesRecyclerView.visibility = View.VISIBLE
            noMoviesLayout.visibility = View.INVISIBLE
        }
    }

    fun goToAddMovieActivity(v: View) {
        val intent = Intent(this, AddMovieActivity::class.java)
        startActivityForResult(intent, ADD_MOVIE_ACTIVITY_REQUEST_CODE)
    }

    fun showToast(s: String) {
        Toast.makeText(baseContext, s, Toast.LENGTH_LONG).show()
    }

    fun displayError(e: String) {
        showToast(e)
    }
}