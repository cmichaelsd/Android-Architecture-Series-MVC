package com.colemichaels.mvc_demo.model

import com.colemichaels.mvc_demo.network.RetrofitClient
import junit.framework.TestCase
import org.junit.Test

class MovieTest : TestCase() {
    @Test
    fun testGetPosterUrlFromPosterPath() {
        val movie = Movie(title = "Finding Nemo", posterPath = "/acceptable-path")
        assertEquals("${RetrofitClient.TMDB_IMAGEURL}/acceptable-path", movie.getPosterUrl())
    }

    @Test
    fun testGetPosterUrlFromEdgeCaseEmpty() {
        val movie = Movie(title = "Finding Nemo", posterPath = "")
        assertEquals(null, movie.getPosterUrl())
    }

    @Test
    fun testGetPosterUrlFromEdgeCaseNull() {
        val movie = Movie(title = "Finding Nemo")
        assertEquals(null, movie.getPosterUrl())
    }

    @Test
    fun testGetReleaseYearFromStringFormattedDate() {
        val movie = Movie(title = "Finding Nemo", releaseDate = "2003-05-30")
        assertEquals("2003", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseYearFromYear() {
        val movie = Movie(title = "FindingNemo", releaseDate = "2003")
        assertEquals("2003", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseEmpty() {
        val movie = Movie(title = "FindingNemo", releaseDate = "")
        assertEquals("", movie.getReleaseYearFromDate())
    }

    @Test
    fun testGetReleaseYearFromDateEdgeCaseNull() {
        val movie = Movie(title = "FindingNemo")
        assertEquals("", movie.getReleaseYearFromDate())
    }
}