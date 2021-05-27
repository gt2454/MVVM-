package com.bjyw.aiyan.network.network

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxJavaImpl {

    private object initRxJavaDataImpl{
        val  impl = RxJavaImpl()
    }

    companion object{
        val dataImpl = initRxJavaDataImpl.impl
    }


    fun <T> post(observable:Observable<Reult<T>>, observer: Observer<Reult<T>>) {
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}