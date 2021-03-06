package com.bjyw.aiyan.custom_target;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class InjectUtils1 {





    public static void injectAutowired(Activity activity) {



        Class<? extends Activity> cls = activity.getClass();
        //获得数据
        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }

        //获得此类所有的成员
        Field[] declaredFields = cls.getDeclaredFields();

        for (Field field : declaredFields) {
//            field.setAccessible(true);
            if (field.isAnnotationPresent(Autowired.class)) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                //获得key
                String key = TextUtils.isEmpty(autowired.value()) ? field.getName() : autowired.value();

                if (extras.containsKey(key)) {
                    Object obj = extras.get(key);
                    // todo Parcelable数组类型不能直接设置，其他的都可以.
                    //获得数组单个元素类型
                    Class<?> componentType = field.getType().getComponentType();
                    //当前属性是数组并且是 Parcelable（子类）数组
                    if (field.getType().isArray() &&
                            Parcelable.class.isAssignableFrom(componentType)) {
                        Object[] objs = (Object[]) obj;
                        //创建对应类型的数组并由objs拷贝

                        Object[] objects = Arrays.copyOf(objs, objs.length, (Class<? extends Object[]>) field.getType());
                        obj = objects;
                    }

                    field.setAccessible(true);
                    try {
                        field.set(activity, obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                break;
            }
        }
    }
}
