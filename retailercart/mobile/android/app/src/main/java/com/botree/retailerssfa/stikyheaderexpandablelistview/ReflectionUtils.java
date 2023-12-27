package com.botree.retailerssfa.stikyheaderexpandablelistview;

import android.util.Log;

import com.botree.retailerssfa.db.FileAccessUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionUtils {
    private static final String TAG = ReflectionUtils.class.getName();

    private ReflectionUtils() {
    }

    static Object getFieldValue(Class<?> fieldClass, String fieldName, Object instance) {
        try {
            final Field field = fieldClass.getDeclaredField(fieldName);
            FileAccessUtil.getInstance().setAccessibleField(field, true);
            return field.get(instance);
        } catch (Exception e) {
            Log.w(TAG, Log.getStackTraceString(e));
        }
        return null;
    }

    static void setFieldValue(Class<?> fieldClass, String fieldName, Object instance, Object value) {
        try {
            final Field field = fieldClass.getDeclaredField(fieldName);
            FileAccessUtil.getInstance().setField(instance, field,value, true);
//            field.setAccessible(true);
//            field.set(instance, value);
        } catch (Exception e) {
            Log.w(TAG, Log.getStackTraceString(e));
        }
    }

    public static Object invokeMethod(Class<?> methodClass, String methodName, Class<?>[] parameters, Object instance, Object... arguments) {
        try {
            final Method method = methodClass.getDeclaredMethod(methodName, parameters);
            FileAccessUtil.getInstance().setAccessibleMethod(method, true);
            return method.invoke(instance, arguments);
        } catch (Exception e) {
            Log.w(TAG, Log.getStackTraceString(e));
        }
        return null;
    }
}