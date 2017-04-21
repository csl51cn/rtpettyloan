package com.global.framework.tagutil;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.global.framework.system.domain.DataDict;
import com.global.framework.system.web.common.CacheService;

public class SelectItem extends ItemTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String diccode;
	private Object value="";
	private String name;
	private String clazz;
	private boolean showval;
	private String style;
	private String expAttribute="";
	private String id;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiccode() {
		return diccode;
	}

	public void setDiccode(String diccode) {
		this.diccode = diccode;
	}

	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public boolean isShowval() {
		return showval;
	}
	public void setShowval(boolean showval) {
		this.showval = showval;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public int doEndTag(){
		Random rd = new Random();
		StringBuilder outputString = new StringBuilder();//输出内容
			outputString.append("<select diccode=").append(diccode)
			.append(" name=\"").append(name).append("\"")
			.append(" "+expAttribute)
			.append(value==null||"".equals(value)?" ":" value=\""+value+"\"")
			.append(" class=\"").append(clazz).append("\"")
			.append(style!=null && !"".equals(style.trim())?("style=\""+style+"\""):"")
			.append(" id=\"").append((id==null||"".equals(id.trim()))?(new StringBuilder(diccode).append("_").append(rd.nextInt(99999999))):id).append("\" >");
//			String sql = "select a.* from sys_datadict a inner join sys_datadictclass b on a.classid = b.classid where b.classcode=? order by a.sortno asc ";
//			List<Map<String, Object>> items = this.jdbcTemplate.queryForList(sql,new Object[]{diccode});
			List<DataDict> items = CacheService.getDataDictListByCatalogCode(diccode);
			outputString.append("<option></option>");
			if(items!=null){
				for(DataDict item:items){
					if(item!=null){
						String itemvalue = String.valueOf(item.getDictCode());
						outputString.append("<option value=").append(itemvalue).append((value!=null&&String.valueOf(value).equals(itemvalue))?" selected":"").append(" >").append(showval?itemvalue+"-":"").append(item.getDictName()).append("</option>");
					}
				}
			}
			outputString.append("</select>");
		try {
			pageContext.getOut().write(outputString.toString());
			super.doEndTag();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	public String getExpAttribute() {
		return expAttribute;
	}
	public void setExpAttribute(String expAttribute) {
		this.expAttribute = expAttribute;
	}
}
