package com.colemichaels.mvc_demo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Observable

@Dao
interface MovieDao {
    @get:Query("SELECT * FROM movie_table")
    val all: Observable<List<Movie>>

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Query("DELETE FROM movie_table WHERE id = :id")
    fun delete(id: Int?)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Update
    fun update(movie: Movie)
}