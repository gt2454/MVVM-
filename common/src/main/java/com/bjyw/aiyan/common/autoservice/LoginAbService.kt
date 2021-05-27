package com.bjyw.aiyan.common.autoservice

import android.content.Context
import java.lang.ref.WeakReference

abstract class LoginAbService:LoginService {

    lateinit var mContext:WeakReference<Context>

    open fun next(context: Context){
        mContext= WeakReference(context)
    }


    fun getContext():Context{
        return mContext.get()!!
    }

}