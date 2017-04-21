package com.global.framework.tagutil;

import java.util.Map;



public class DicItem extends ItemTag{
	
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String diccode;
	private Object value;
	
	
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

	@Override
	public int doEndTag(){
		String msg="";
		Map<String, Object> item = this.jdbcTemplate.queryForMap("select a.* from sys_datadict a inner join sys_datadictclass b on a.classid = b.classid where b.classcode=? and a.dictcode=?",new Object[]{diccode,value});
		if(item!=null) msg = (String) item.get("dictname");
		try {
			pageContext.getOut().write(msg);
			super.doEndTag();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
