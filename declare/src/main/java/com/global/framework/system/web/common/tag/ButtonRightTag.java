package com.global.framework.system.web.common.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.web.common.session.SessionManager;

/**
 * 按钮权限标签
 * @author chen.feng
 * @date 2013-4-25 上午11:01:39
 */
public class ButtonRightTag extends TagSupport {
	private static final Log log = LogFactory.getLog(ButtonRightTag.class);

	private static final long serialVersionUID = 2673358951280181854L;
	private String menuId;
	private String btnCode;
	private String btnName;
	private String btnReqUrl;
	private String onClick;
	
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getBtnCode() {
		return btnCode;
	}

	public void setBtnCode(String btnCode) {
		this.btnCode = btnCode;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnReqUrl() {
		return btnReqUrl;
	}

	public void setBtnReqUrl(String btnReqUrl) {
		this.btnReqUrl = btnReqUrl;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}
	
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		User user = SessionManager.getSession(request);
		System.out.println("user:"+user);
		JspWriter out = this.pageContext.getOut();
		try {
			out.print(this.generateButtonHtml(user.getRights()));
		} catch (IOException e) {
			log.error("根据用户按钮权限生成页面按钮失败", e);
		} finally {
			try {
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}
	
	private String generateButtonHtml(List<RoleRight> btnRights) {
		StringBuilder sb = new StringBuilder();
//		for (RoleRight roleRight : btnRights) {
//			if (this.menuId.equals(roleRight.get))
//		}
//		sb.append("<a id=\""+this.btnCode+"\" href=\"###\" class=\"easyui-linkbutton\" plain=\"true\" iconCls=\"icon-"+this.btnCode+"\" onclick=\""+this.onClick+"\">"+this.btnName+"</a>");
		return sb.toString();
	}
}
