package com.colemichaels.mvc_demo.model

import com.colemichaels.mvc_demo.network.RetrofitClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

open class RemoteDataSource {
    fun searchResultsObservable(query: String): Observable<TmbdResponse> {
        return RetrofitClient.moviesApi
            .searchMovie(RetrofitClient.API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}