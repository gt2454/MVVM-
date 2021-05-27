package com.bjyw.aiyan.data_util

import com.bjyw.aiyan.mvvm.BaseModel
import com.bjyw.aiyan.network.network.APIClient
import com.bjyw.aiyan.network.network.RxJavaImpl

class DataRepository :BaseModel(){

    val  https = RxJavaImpl()

    val api = APIClient.instance.instance()
//    val  room = DataBase.dataBase


    override fun onCleared() {

    }


}