package com.global.framework.xmlfile.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author chen.feng
 * @date 2014-6-30 下午4:51:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlRootMapping {

	public String rootName();
}
