package com.scdz.wifidemo;

import java.lang.reflect.Method;

/**
 * Created by ScDz on 2017/7/13.
 * ClassNote:
 */

public class Invokeutil {

    /**
     * 执行动态方法
     */
    public static Object invokeMethod(String className, Object instance, String methodName,
                                      Object[] args) throws Exception {

        Class<?> ownerClass = Class.forName(className);
        Method[] methods = ownerClass.getMethods();
        Object ret = null;
        for (Method method : methods) {
            if (method.getName().toLowerCase().equals(methodName.toLowerCase())) {
                ret = method.invoke(instance, args);
                break;
            }
        }
        return ret;
    }

    /**
     * 执行静态方法
     */
    public static Object invokeStaticMethod(String className, String methodName,
                                            Object[] args) throws Exception {

        Class<?> ownerClass = Class.forName(className);

        Method[] methods = ownerClass.getMethods();
        Object ret = null;
        for (Method method : methods) {
            if (method.getName().toLowerCase().equals(methodName.toLowerCase())) {
                ret = method.invoke(null, args);
                break;
            }
        }
        return ret;
    }

    /**
     * 执行静态方法
     */
    public static Object invokeStaticMethod(String className, String methodName, Class[] argsclass,
                                            Object[] args) throws Exception {
        Object ret = null;
        Class<?> ownerClass = Class.forName(className);
        Method method = ownerClass.getMethod(methodName, argsclass);
        if (method != null) ret = method.invoke(null, args);
        return ret;
    }

}
