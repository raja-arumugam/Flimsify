package com.example.moviesdataapp.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import com.example.moviesdataapp.data.api.Result.Status.ERROR
import com.example.moviesdataapp.data.api.Result.Status.SUCCESS


/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */

fun <T, A> resultLiveData(
    networkCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit,
    databaseQuery: () -> LiveData<T>
): LiveData<Result<T>> =
    liveData(Dispatchers.IO) {
        emit(Result.loading<T>())

        val source = databaseQuery.invoke().map { Result.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()

        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
            val source = databaseQuery.invoke().map { Result.success(it) }
            emitSource(source)
        } else if (responseStatus.status == ERROR) {
            emit(Result.error<T>(responseStatus.message!!))
            emitSource(source)
        }
    }