package com.bjyw.aiyan.common.live_data_bus

import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.HashMap


class LiveDataBus  {


     companion object{
         private val  hashMap = HashMap<String, BusMutableLiveData<Objects>>()

         fun <T> with(name :String){
             with(name,Objects::class.java)
         }
         fun <T> with(name : String,aclass: Class<T>): MutableLiveData<T>{
             if(!hashMap.containsKey(name)){
                 hashMap.set(name,
                     BusMutableLiveData<Objects>()
                 )
             }
             return  hashMap.get(name) as MutableLiveData<T>
         }

         fun  clear(){
             if(hashMap.size > 0){
                 hashMap.clear()
             }
         }
     }
}


