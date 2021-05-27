package com.bjyw.aiyan.common.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV

class MMKVUtils {

    val   mKV = MMKV.defaultMMKV()

    private object initMMKV{
        var  mmkv = MMKVUtils()
    }

    companion object {
        val mmkvUtils = initMMKV.mmkv
    }

     fun getData(name: String, aclass: Any): Any? {
        if (aclass is String) {
            return mKV.decodeString(name)
        }
        if (aclass is Int) {
            return mKV.decodeInt(name)
        }
        if (aclass is Boolean) {
            return mKV.decodeBool(name)
        }
        return null
    }



    fun setData(name: String,any: Any): Any {
        if (any is String) {
            return mKV.encode(name,any)
        }
        if (any is Int) {
            return mKV.decodeInt(name,any)
        }
        if (any is Boolean) {
            return mKV.decodeBool(name,any)
        }
        return false
    }

    fun setParcelable(name :String ,bean :Parcelable ) : Boolean{
        return mKV.encode(name,bean)
    }

}