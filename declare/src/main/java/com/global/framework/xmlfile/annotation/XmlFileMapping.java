package com.global.framework.xmlfile.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author chen.feng
 * @date 2014-6-30 下午3:46:38
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlFileMapping {

	public String fieldName();
	
	public String fieldType();
	
	/**
	 * 默认为空，当字段类型为日期时可指定format格式
	 * @return
	 */
	public String fieldDateFormat() default "";
	
	public String fieldLength() default "";
	
	public boolean required() default false;
	
}
