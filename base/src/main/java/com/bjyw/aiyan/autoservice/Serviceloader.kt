package com.bjyw.aiyan.autoservice

import java.util.*

open class Serviceloader {

    companion object{
        fun <S> load (class1 : Class<S>): S?{
             try {
                 val iterator = ServiceLoader.load(class1).iterator()
                 if(iterator.hasNext()){
                     return  iterator.next()
                 }
                 return null
//                return  ServiceLoader.load(class1).iterator().next()
            } catch (e: Exception) {
                 return null
            }
        }
    }

}