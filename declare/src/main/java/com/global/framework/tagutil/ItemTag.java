package com.global.framework.tagutil;

//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.Toolkit;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;



@SuppressWarnings("serial")
public abstract class ItemTag extends TagSupport{

	@Autowired
	protected  JdbcTemplate jdbcTemplate;;
	
	@Override
	public int doStartTag() throws JspException {
		jdbcTemplate = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean(JdbcTemplate.class);
		return super.doStartTag();
	}
}
