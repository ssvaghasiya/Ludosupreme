package com.ludosupreme.data.datasource

import com.ludosupreme.data.pojo.DataWrapper
import com.ludosupreme.data.pojo.ResponseBody
import com.ludosupreme.exception.ServerException
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

open class BaseDataSource {

    fun <T> execute(observable: Single<ResponseBody<T>>): Single<DataWrapper<T>> {
        return observable
            /*.subscribeOn(Schedulers.from(appExecutors.networkIO()))
            .observeOn(Schedulers.from(appExecutors.mainThread()))*/
            .subscribeOn(Schedulers.io())
            .map { t -> DataWrapper(t, null) }
            .onErrorReturn { t -> DataWrapper(null, t) }
            .map {
                if (it.responseBody != null) {
                    when (it.responseBody.responseCode) {
                        2, 3, 4 -> return@map DataWrapper(
                            it.responseBody,
                            ServerException(it.responseBody.message, it.responseBody.responseCode)
                        )
                    }
                }
                return@map it
            }
    }

}