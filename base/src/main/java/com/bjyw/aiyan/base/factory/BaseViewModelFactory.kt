package com.bjyw.aiyan.base.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bjyw.aiyan.data_util.DataRepository

class BaseViewModelFactory(application : Application ) : ViewModelProvider.NewInstanceFactory() {

    private var  applicatio : Application? = null

    private var repository : DataRepository? = null


    constructor(application : Application,dataRepository: DataRepository) : this(application) {
        applicatio = application;
        repository = dataRepository
    }


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return modelClass.getConstructor(Application::class.java,DataRepository::class.java)
            .newInstance(applicatio,repository)
    }


}