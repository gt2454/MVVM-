package com.bjyw.aiyan.custom_target

import android.app.Activity
import android.os.Parcelable
import android.text.TextUtils
import java.util.*

object InjectUtils {

    fun injectAutowired(activity: Activity) {
        val cls: Class<out Activity> = activity.javaClass
        //获得数据
        val intent = activity.intent
        val extras = intent.extras ?: return

        //获得此类所有的成员
        val declaredFields = cls.declaredFields
        for (field in declaredFields) {
            field.isAccessible = true;
            if (field.isAnnotationPresent(Autowired::class.java)) {
                val autowired = field.getAnnotation(Autowired::class.java)
                //获得key
                val key =
                    if (TextUtils.isEmpty(autowired.value)) field.name else autowired.value
                if (extras.containsKey(key)) {
                    var obj = extras[key]
//                     todo Parcelable数组类型不能直接设置，其他的都可以.
                    //获得数组单个元素类型
                    val componentType = field.type.componentType
                    //当前属性是数组并且是 Parcelable（子类）数组
                    if (field.type.isArray &&
                        Parcelable::class.java.isAssignableFrom(componentType!!)
                    ) {
                        val objs = obj as Array<Any>?
                        //创建对应类型的数组并由objs拷贝
                        val objects =
                            Arrays.copyOf(
                                objs,
                                objs!!.size,
                                field.type as Class<out Array<Any>?>
                            )
                        obj = objects
                    }
                    field.isAccessible = true
                    try {
                        field[activity] = obj
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }
                }
            } else {
                break
            }
        }
    }

//    fun injectAutowired(fragment: Fragment){
//
//    }


}