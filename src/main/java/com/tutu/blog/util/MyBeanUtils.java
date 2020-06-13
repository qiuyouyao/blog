package com.tutu.blog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tutu 2020/5/2 2:13
 */
public class MyBeanUtils {

    public static String[] filterNullPropertyNames(Object source){
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
        List<String> nullProperties = new ArrayList<>();
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            if(wrapper.getPropertyValue(name) == null){
                nullProperties.add(name);
            }
        }
        return nullProperties.toArray(new String[0]);
    }
}
