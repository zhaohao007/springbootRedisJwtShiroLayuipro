package com.hinmu.lims.util.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by hao on 2019/3/22.
 */
public class BeanUtils extends org.springframework.beans.BeanUtils{

    public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
        Class actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        PropertyDescriptor[] var4 = targetPds;
        int var5 = targetPds.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor targetPd = var4[var6];
            Method writeMethod = targetPd.getWriteMethod();
            if(writeMethod != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if(sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if(readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }

                            Object ex = readMethod.invoke(source, new Object[0]);
                            if(ex != null) {
                                if(!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }

                                writeMethod.invoke(target, new Object[]{ex});
                            }
                        } catch (Throwable var12) {
                            throw new FatalBeanException("Could not copy property \'" + targetPd.getName() + "\' from source to target", var12);
                        }
                    }
                }
            }
        }

    }
}
