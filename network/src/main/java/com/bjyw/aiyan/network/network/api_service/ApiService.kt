package com.bjyw.aiyan.network.network.api_service

import com.bjyw.aiyan.network.network.Reult
import com.bjyw.aiyan.network.network.bean.LoginBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("/user/login")
    @FormUrlEncoded
    fun Login(@Field("username") usermane: String,@Field("password") password: String)
    : Observable<Reult<LoginBean>>


    @POST("/location/add")
    @FormUrlEncoded
    fun Login(@Field("id")
              id: Int,@Field("staffid")
    staffid: Int,@Field("lon")
    lon: Double,@Field("lat")
    lat: Double,@Field("time") time: String,@Field("radius")radius: Float )
            : Observable<String>

    @POST("location/add/")
    @FormUrlEncoded
    fun postData(@Field("json") json: String) : Observable<ResponseBody>

}