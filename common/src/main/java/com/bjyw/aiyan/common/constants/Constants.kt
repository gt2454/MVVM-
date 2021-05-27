package com.bjyw.aiyan.common.constants

import com.bjyw.aiyan.common.BuildConfig

class Constants {

    companion object{
        /*
        *   网络数据  BaseURL
        * */
        fun getBaseURL() : String{

            return if(BuildConfig.DEBUG){
                "http://101.200.167.139:8086/bjyw-controller/"
            }else{
                "http://101.200.167.139:8085/bjyw-controller/"
            }
//            return  "http://192.168.0.108:8082/bjyw_controller_war/location/add"
        }

//        http://192.168.0.108:8082/bjyw_controller_war/location/add

        /*
        *   图片压缩后的分辨力大小
        * */
        val  witch  = 480f
        val  height = 800f
    }
}