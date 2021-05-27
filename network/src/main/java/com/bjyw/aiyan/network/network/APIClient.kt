package com.bjyw.aiyan.network.network

import com.bjyw.aiyan.common.constants.Constants
import com.bjyw.aiyan.network.network.api_service.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {

    private object initNet{
        var apiClient = APIClient()
    }

     companion object{
         val  instance = initNet.apiClient
     }


     fun  instance() : ApiService? {

         val  mOkhttp = OkHttpClient().newBuilder()
                 .readTimeout(10000,TimeUnit.SECONDS)
                 .connectTimeout(10000,TimeUnit.SECONDS)
                 .writeTimeout(10000,TimeUnit.SECONDS)
                 .build()


         val retrofit : Retrofit = Retrofit.Builder()
                 .baseUrl(Constants.getBaseURL())
                 .client(mOkhttp)
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .build()



         return retrofit.create(ApiService::class.java)
     }


}