package com.bjyw.aiyan.base.location

import android.app.Application
import android.content.Context
import com.bjyw.aiyan.network.applocation.DataApplication
import com.zhy.autolayout.config.AutoLayoutConifg

open class BaseApplication : DataApplication() {


    var application: Application? = null


    var instance: Application? = null

    private var context: Context? = null

    fun getContext(): Context? {
        return context
    }

    private object init {
        val baseApp = BaseApplication()
    }

    companion object {
        val baseApplication = init.baseApp
    }


    override fun onCreate() {
        super.onCreate()
        application = this

        instance = this
        context = this.applicationContext
        val instance: AutoLayoutConifg = AutoLayoutConifg.getInstance()
        instance.useDeviceSize()
//        instance.init(this)

    }

}