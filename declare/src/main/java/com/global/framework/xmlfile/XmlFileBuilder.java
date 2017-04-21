package com.global.framework.xmlfile;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.global.framework.dbutils.support.Entity;
import com.global.framework.exception.BaseException;
import com.global.framework.util.CharUtil;
import com.global.framework.util.DateTimeUtil;
import com.global.framework.util.StringUtil;
import com.global.framework.util.SysUtils;
import com.global.framework.xmlfile.annotation.FieldDescribe;
import com.global.framework.xmlfile.annotation.XmlFileDescribe;
import com.global.framework.xmlfile.annotation.XmlRootMapping;
import com.global.framework.xmlfile.base.XmlContent;

/**
 * XML文件构建器
 * @author chen.feng
 * @date 2014-6-30 下午3:54:45
 */
public class XmlFileBuilder extends XmlFileSupport {

	/**
	 * 生成XML文件
	 * @param xmlContent XML文件内容实体
	 * @param localFilePath XML文件本地存放路径
	 * @return
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	public String buildXmlFile(XmlContent xmlContent, String localFilePath) throws BaseException {
		String retXml = "";
		//获取XML文件数据
		Map<String, Object> xmlContentMap = null;
		try {
			xmlContentMap = PropertyUtils.describe(xmlContent);
		} catch (Exception e) {
			throw new BaseException("获取XML文件内容失败", e);
		}
		//获取公共部分注解信息
		XmlFileDescribe desc = super.getXmlFileMap(xmlContent.getClass());
		XmlRootMapping root = (XmlRootMapping) desc.getClassAnnotation();

		//创建XML文件头
		Document document = DocumentHelper.createDocument();
		Element element = null;
		if (StringUtils.isNotBlank(root.rootName())) {
			element = document.addElement(root.rootName());
		} else {
			throw new BaseException("XML文件的root必须在声明");
		}

		List<FieldDescribe> fieldList = desc.getFieldList();
		getContentRecordsElement(xmlContentMap, element, fieldList);

		try {
			//			ByteArrayOutputStream wrstr= new ByteArrayOutputStream();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setTrimText(true);
			format.setEncoding("gb18030");
			//			org.dom4j.io.XMLWriter wr = new  org.dom4j.io.XMLWriter(wrstr,format);
			//			wr.write(document);

			XMLWriter writer = new XMLWriter(
					new FileWriterWithEncoding(new File(localFilePath),"gb18030"), format);//"d:\\test.xml"
			writer.write(document);

			//			retXml = new String(wrstr.toByteArray(),"gb18030");
			//			wrstr.close();
			//			wr.close();
			writer.close();
		} catch (Exception e) {
			throw new BaseException("构建XML文件失败", e);
		}
		return retXml;
	}

	/**
	 * 解析RECORDS部分
	 * @param xmlContentMap
	 * @param element
	 * @param fieldList
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	private void getContentRecordsElement(Map<String, Object> xmlContentMap,
			Element element, List<FieldDescribe> fieldList) throws BaseException {
		try {
			for (FieldDescribe fieldDescribe : fieldList) {
				//根据注解信息校验数据
				validField(xmlContentMap, fieldDescribe);
				//REC部分
				if ("Group".equals(fieldDescribe.getFieldType())) {
					Object data = xmlContentMap.get(fieldDescribe.getAttrName());
					if(!StringUtil.isNullOrEmpty(data)) {
						if (data instanceof List) {
							List<? extends Entity> recList = (List<? extends Entity>) data;
							for (Entity recEntity : recList) {
								Class<? extends Entity> c = recList.get(0).getClass();
								Element recordsElement = element.addElement(fieldDescribe.getFieldName());//RECORDS
								XmlFileDescribe recDesc = super.getXmlFileMap(c);
								XmlRootMapping root = (XmlRootMapping) recDesc.getClassAnnotation();
								if(root == null || StringUtils.isBlank(root.rootName())){//如果泛型类无XmlRootMapping注解，则不创建循环节点
									this.getRecs(recEntity, recordsElement, c);
								}else{
									Element recElement = recordsElement.addElement(root.rootName());//REC
									this.getRecs(recEntity, recElement, c);
								}
							}
						}
						
					}
				} else {
					String text = (String) xmlContentMap.get(fieldDescribe.getAttrName());
					setElementText(element, fieldDescribe, text);
				}
			}
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

	/**
	 * 解析REC部分
	 * @param entity
	 * @param recElement
	 * @param clazz
	 * @throws BaseException
	 */
	@SuppressWarnings("unchecked")
	private <E extends Entity> void getRecs(E entity, Element recElement, Class<? extends Entity> clazz) throws BaseException {
		Map<String, Object> recMap = null;
		try {
			recMap = PropertyUtils.describe(entity);
		} catch (Exception e) {
			throw new BaseException("构建XML文件出错:", e);
		}

		XmlFileDescribe desc = super.getXmlFileMap(clazz);
		List<FieldDescribe> fieldList = desc.getFieldList();
		for (FieldDescribe fieldDescribe : fieldList) {
			//根据注解信息校验数据
			validField(recMap, fieldDescribe);
			
			Object value = recMap.get(fieldDescribe.getAttrName());
			if (!StringUtil.isNullOrEmpty(value)){
				if (value instanceof List) {
					List<? extends Entity> vList = (List<? extends Entity>) value;
					Element detailElement = recElement.addElement(fieldDescribe.getFieldName());//detail
					for (Entity object : vList) {
						Class<? extends Entity> c = vList.get(0).getClass();
						XmlFileDescribe detailDesc = super.getXmlFileMap(c);
						XmlRootMapping root = (XmlRootMapping) detailDesc.getClassAnnotation();
						Element detailEntityElement = detailElement.addElement(root.rootName());//detailEntity
						this.getRecs(object, detailEntityElement, c);
					}
				}else{
					setElementText(recElement, fieldDescribe, (String) value);
				}
			} else {
				recElement.addElement(fieldDescribe.getFieldName());
			}
		}
	}

	/**
	 * 根据注解信息校验数据
	 * @param xmlContentMap
	 * @param fieldDescribe
	 * @throws BaseException
	 */
	private void validField(Map<String, Object> xmlContentMap,
			FieldDescribe fieldDescribe) throws BaseException {
		boolean required = fieldDescribe.isFieldIsNotNull();//该字段是否要求必填
		String fieldLength = fieldDescribe.getFieldLength();//该字段要求的最大长度
		Object value = xmlContentMap.get(fieldDescribe.getAttrName());//该字段的value
		if (required && StringUtil.isNullOrEmpty(value)) {
			throw new BaseException("字段[" + fieldDescribe.getAttrName() + "]为必填项，数据不能为空");
		}
		if (!"Group".equals(fieldDescribe.getFieldType())) {
			if (StringUtils.isNotBlank(fieldLength)) {
				String fLens[] = fieldLength.split(",");//注解定义的字段长度
				if (fLens.length == 2 && !StringUtil.isNullOrEmpty(value)) {
					//该字段为double类型
					BigDecimal bgValue = null;
					try {
						bgValue = new BigDecimal((String) value);
					} catch (Exception e) {
						throw new BaseException("格式化异常：请检查字段[" + fieldDescribe.getAttrName() + "]的注解定义的类型或字段赋值");
					}
					String[] sValues = String.valueOf(bgValue).split("\\.");
					if (sValues[0].length() > Integer.parseInt(fLens[0])) {
						throw new BaseException("字段[" + fieldDescribe.getAttrName() + "]的值的整数位超过了定义的字段长度");
					}
					if (sValues.length == 2){//有小数位时才校验
						if (sValues[1].length() > Integer.parseInt(fLens[1])) {
							throw new BaseException("字段[" + fieldDescribe.getAttrName() + "]的值的小数位超过了定义的字段长度");
						}
					}
				} else {
					//比较字符串类型的长度
					if (CharUtil.getChineseStrLen((String) value) > Integer.parseInt(fLens[0])) {
						throw new BaseException("字段[" + fieldDescribe.getAttrName() + "]的值超过了定义的字段长度");
					}
				}
			}
		}
	}
	
	private void setElementText(Element recElement,
			FieldDescribe fieldDescribe, String text) {
		if (StringUtils.isNotBlank(fieldDescribe.getDateFormatPattern())) {
			Date date = DateTimeUtil.getStrToDateTime(text, "yyyy-MM-dd HH:mm:ss");
			text = DateTimeUtil.getDateToStr(date, fieldDescribe.getDateFormatPattern());
		}
		if ("Double".equals(fieldDescribe.getFieldType()) || "BigDecimal".equals(fieldDescribe.getFieldType())) {
			String fieldLength = fieldDescribe.getFieldLength();
			String[] fieldLengths = fieldLength.split(",");
			String pattern = "0." + "0000000000".substring(0, Integer.parseInt(fieldLengths[1]));
			text = SysUtils.formatAmt(text);
		}
		recElement.addElement(fieldDescribe.getFieldName()).setText(text);
	}
}
