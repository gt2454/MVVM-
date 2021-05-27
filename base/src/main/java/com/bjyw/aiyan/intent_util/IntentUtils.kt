package com.bjyw.aiyan.intent_util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

class IntentUtils {

    private var bundle: Bundle? = null

    private object  init{
        val  intentUtils = IntentUtils()
    }

    companion object {
        val  intentU = init.intentUtils
    }

    constructor(){
        bundle = Bundle()
    }



    fun addValue(key:String,value : Any) : IntentUtils{
        addData(key,value)
        return  this
    }

    private fun  addData(key:String,value : Any){
        if(value is String){
            bundle!!.putString(key,value)
        }else if(value is Int){
            bundle!!.putInt(key,value)
        }else if(value is Float){
            bundle!!.putFloat(key,value)
        }else if(value is Double){
            bundle!!.putDouble(key,value)
        }
    }



    fun navigation(activity: Activity,clz: Class<*>?){
        val intent = Intent(activity, clz)
        if(bundle == null){
            activity.startActivity(intent)
            return
        }
        if(bundle!!.size() > 0){
            activity.startActivity(intent,bundle)
            return
        }
        activity.startActivity(intent)
    }

    fun navigation(context: Context,clz: Class<*>?){
        val intent = Intent(context, clz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if(bundle == null){
            context.startActivity(intent)
            return
        }
        if(bundle!!.size() > 0){
            context.startActivity(intent,bundle)
            return
        }
        context.startActivity(intent)
    }


//    fun newBundle() : IntentUtils{
//        bundle = Bundle()
//        return this
//    }

//    fun isBundle() : Boolean{
//        return bundle != null
//    }

//    fun clearBundle(){
//        if(bundle == null){
//            return
//        }
//        if(bundle!!.size() > 0)
//            bundle!!.clear()
//    }

}