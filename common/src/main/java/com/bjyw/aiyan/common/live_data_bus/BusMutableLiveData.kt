package com.bjyw.aiyan.common.live_data_bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method


class BusMutableLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    @Throws(Exception::class)
    private fun hook(observer: Observer<*>) {
        // 获取 LiveData 的类对象
        val liveDataClass = LiveData::class.java
        //  获取 mVersion 对象
        val mVersionField: Field = liveDataClass.getDeclaredField("mVersion")
        mVersionField.setAccessible(true)
        // 获取 mObservers 对象
        val mObserversField: Field = liveDataClass.getDeclaredField("mObservers")
        mObserversField.setAccessible(true)

        // 通过get 能获取到mObservers这个成员变量
        // SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers
        // 因为MutableLiveData 是 LiveData的子类，所以直接this 就能获取
        val mObservers: Any = mObserversField.get(this)

        // 获取map 的get方法 ，map 获取的value 是Object类型
        val get: Method = mObservers.javaClass.getDeclaredMethod("get", Any::class.java)
        get.setAccessible(true)

        // 执行get， mObservers 里面就是 Observer 和 ObserverWrapper，所以直接传递observer
        val invoke: Any = get.invoke(mObservers, observer)
        var observerWrapper: Any? = null

        // 如果 执行结果不为空 并且也是Entry类型，说明结果对的
        if (invoke != null && invoke is Map.Entry<*, *>) {
            observerWrapper = invoke.value
        }
        if (observerWrapper == null) {
            throw NullPointerException()
        }

        // 这个时候获取到mObservers 的value 再来改变mLastVersion 的值
        val observerWrapperClass: Class<*> = observerWrapper.javaClass
//        Log.i(TAG, "observerWrapperClass: $observerWrapperClass")
        // 发现获取的类类型 是 LifecycleBoundObserver，是 ObserverWrapper 子类，所以要 getSuperclass
        val mLastVersion: Field =
            observerWrapperClass.superclass!!.getDeclaredField("mLastVersion")
        mLastVersion.setAccessible(true)

        // 得到 mVersionField 的值
        val o: Any = mVersionField.get(this)
        // 将mVersionField的值 付给 mLastVersion
        mLastVersion.set(observerWrapper, o)
    }
}