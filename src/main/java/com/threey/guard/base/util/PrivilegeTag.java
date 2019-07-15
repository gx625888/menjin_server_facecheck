package com.threey.guard.base.util;

import com.threey.guard.base.service.LoginService;
import org.apache.commons.lang.StringUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * 系统权限标签处理类
 * 
 * @author ZL
 * 
 */
public class PrivilegeTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3933222811626904317L;
	private static final String SEPARATOR = ",";

	private String privCodeList;
	private String relationFlag;

	public String getRelationFlag() {
		return relationFlag;
	}

	public void setRelationFlag(String relationFlag) {
		this.relationFlag = relationFlag;
	}

	public String getPrivCodeList() {
		return privCodeList;
	}

	public void setPrivCodeList(String privCodeList) throws JspException {
		// 对EL表达式的支持
		this.privCodeList = ExpressionEvaluatorManager.evaluate("privCodeList", privCodeList, String.class,
				this, pageContext).toString();
	}

	@Override
	public int doStartTag() throws JspException {
	    	LoginService loginService = CtxFactory.getCtx().getBean(LoginService.class);
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		List<String> privilegeCodeList = loginService.getLoginUserPrivilegeCodeList(req);
		boolean showHtmlFlag = true;
		if ("or".equals(relationFlag)) {
			showHtmlFlag = false;
		}
		String privCodeStr = privCodeList.toString();
		if (StringUtils.isNotEmpty(privCodeStr)) {
			String[] privilegeCodeArr = privCodeStr.split(SEPARATOR);

			if (privilegeCodeArr != null && privilegeCodeArr.length > 0) {
				for (String pCode : privilegeCodeArr) {
					if ("or".equals(relationFlag)) {
						if (privilegeCodeList.contains(pCode)) {
							showHtmlFlag = true;
							break;
						}
					} else {
						if (!privilegeCodeList.contains(pCode)) {
							showHtmlFlag = false;
							break;
						}
					}
				}
			}
		}
		return showHtmlFlag ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
}
