package com.bjyw.aiyan.network.network

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

//拦截  自定义操作符  目的  包装Bean 给拆成两份  如果成功  data-> UI  如果失败  meg-> Ui
abstract class APIResponse <T>: Observer<Reult<T>> {
    //如果请求成功
    abstract fun success(data : T ?)

    // 如果请求失败
    abstract fun failure(errorMsg : String ?)




    override fun onSubscribe(d: Disposable) {}

    override fun onNext(t: Reult<T>) {
        if(t?.data == null){
            failure(t?.errorMsg)
        }else{
            success(t.data)
        }
    }

    override fun onError(e: Throwable) {
        failure(e?.message)
    }

    override fun onComplete() {

    }

}