package com.global.framework.xmlfile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.global.framework.dbutils.support.Entity;
import com.global.framework.exception.BaseException;
import com.global.framework.xmlfile.annotation.FieldDescribe;
import com.global.framework.xmlfile.annotation.XmlFileDescribe;
import com.global.framework.xmlfile.annotation.XmlFileMapping;
import com.global.framework.xmlfile.annotation.XmlRootMapping;

/**
 * 
 * @author chen.feng
 * @date 2014-6-30 下午3:56:02
 */
public class XmlFileSupport {
	
	private static Map<Class<? extends Entity>, XmlFileDescribe> entityDescMap = new HashMap<Class<? extends Entity>, XmlFileDescribe>(
			512);

	public XmlFileDescribe getXmlFileMap(Class<? extends Entity> entity)
			throws BaseException {
		Annotation[] fieldAnnotations = null;
		Map<String, FieldDescribe> fieldDescMap = null;
		LinkedList<FieldDescribe> fieldList = null;
		Field[] fields = null;
		XmlFileDescribe xmlFileDescribe = null;
		XmlFileMapping field = null;
		FieldDescribe fieldDescribe = null;

		synchronized (entityDescMap) {
			xmlFileDescribe = entityDescMap.get(entity);
			if (xmlFileDescribe != null) {
				return xmlFileDescribe;
			}

			xmlFileDescribe = new XmlFileDescribe();
			xmlFileDescribe.setEntityName(entity.getSimpleName());
			Annotation[] classAnnotations = entity.getDeclaredAnnotations();
//			if (classAnnotations.length == 0) {
//				throw new BaseException("构建XML文件的实体必须注解XmlRootMapping");
//			}
			for (Annotation an : classAnnotations) {
				if (an instanceof XmlRootMapping) {
					xmlFileDescribe.setClassAnnotation(an);
				}
			}

			fields = entity.getDeclaredFields();
			fieldDescMap = new HashMap<String, FieldDescribe>();
			fieldList = new LinkedList<FieldDescribe>();
			for (Field f : fields) {
				fieldAnnotations = f.getDeclaredAnnotations();
				if (fieldAnnotations.length == 0) {
					continue;
				}
				for (Annotation an : fieldAnnotations) {
					if (an instanceof XmlFileMapping) {
						field = (XmlFileMapping) an;
						fieldDescribe = new FieldDescribe();
						fieldDescribe.setAttrName(f.getName());
						fieldDescribe.setFieldName(field.fieldName());
						fieldDescribe.setFieldType(field.fieldType());
						fieldDescribe.setDateFormatPattern(field.fieldDateFormat());
						fieldDescribe.setFieldLength(field.fieldLength());
						fieldDescribe.setFieldIsNotNull(field.required());
						fieldDescMap.put(f.getName(), fieldDescribe);
						fieldList.add(fieldDescribe);
					}
				}
			}
			xmlFileDescribe.setFieldMap(fieldDescMap);
			xmlFileDescribe.setFieldList(fieldList);
			entityDescMap.put(entity, xmlFileDescribe);
			return xmlFileDescribe;
		}
	}
}
