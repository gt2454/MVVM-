package com.bjyw.aiyan.network.applocation

import android.app.Application
import androidx.multidex.MultiDex

open class DataApplication : Application(){

    companion object{
        var baseApplication  = DataApplication()
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);
    }

}