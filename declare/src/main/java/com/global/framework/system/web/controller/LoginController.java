package com.global.framework.system.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.exception.BaseException;
import com.global.framework.system.constants.LoginStatusCodeConstants;
import com.global.framework.system.domain.DataRight;
import com.global.framework.system.domain.Menu;
import com.global.framework.system.domain.OperateLog;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.domain.User;
import com.global.framework.system.domain.UserRole;
import com.global.framework.system.service.LogService;
import com.global.framework.system.service.LoginService;
import com.global.framework.system.service.MenuService;
import com.global.framework.system.service.RoleService;
import com.global.framework.system.service.UserService;
import com.global.framework.system.web.common.session.SessionManager;
import com.global.framework.util.IPUtil;
import com.global.framework.util.SysUtils;
import com.global.web.BaseController;
import com.global.workflow.service.TasklistService;

/**
 * @author cqchenf@qq.com
 * @version v1.0
 * @date 2012-8-30 上午11:15:03
 */
@Controller
public class LoginController extends BaseController {
    private final static Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LogService logService;
    @Autowired
    private TasklistService tasklistService;

    @RequestMapping("/logon")
    public String logon() {
        return "logon";
    }

    @RequestMapping("/main")
    public String main(Model model, HttpServletRequest request) {
        try {
            User user = SessionManager.getSession(request);
            model.addAttribute("user", user);
            List<UserRole> userRoleList = user.getRoles();
            //根据角色获取有访问权限的菜单
            Map<String, Set<Menu>> map = this.menuService.getMenuByRole(userRoleList);
            Set<Menu> menuGroupList = map.get("menuGroup");
            Set<Menu> menuList = map.get("menu");

            model.addAttribute("menuGroupList", menuGroupList);
            model.addAttribute("menuHtml", this.menuService.generateNavMenuHtml(menuGroupList, menuList));

            OperateLog sysLog = new OperateLog();
            sysLog.setOperateIp(IPUtil.getIpAddr(request));
            sysLog.setUserId(user.getUserCode());
            sysLog.setUserName(user.getUserName());
            sysLog.setOperateType("4");
            sysLog.setOperateDate(SysUtils.getNowDateTime());
            sysLog.setContent("用户登录");
            logService.insertLog(sysLog);

            return "main";
        } catch (BaseException e) {
            log.error("用户登录失败：", e);
            return "redirect:/logon.do";
        }
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        try {
//			User user = SessionManager.getSession(request);
//			
//			List<UserRole> userRoleList = user.getRoles();
//			//根据角色获取有访问权限的菜单
//			Map<String, Set<Menu>> map = this.menuService.getMenuByRole(userRoleList);
//			Set<Menu> menuGroupList = map.get("menuGroup");
//			Set<Menu> menuList = map.get("menu");

            //model.addAttribute("menuGroupList", menuGroupList);
            //model.addAttribute("menuHtml",this.menuService.generateNavMenuHtml(menuGroupList, menuList));
            //查询上次登录日志
//		UserLoginLogDto loginLog = loginService.getLastLoginLog(user.getUserNo());
//		log.info("loginLog:::"+loginLog);

            //插入用户登录日志
//		UserLoginLog login = new UserLoginLog();
//		login.setUserId(user.getUserId());
//		login.setLoginIp(user.getLoginIp());
//		login.setLoginTime(user.getLoginTime());
//		loginService.insertLoginLog(login);

            //model.addAttribute("user", user);
//		model.addAttribute("loginLog", loginLog);

            System.out.println("***sessionid=" + request.getRequestedSessionId());
        } catch (Exception e) {
            log.error("用户登录失败：", e);
            return "redirect:/logon.do";
        }
        return "redirect:/main.do";
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public Map<String, Object> checkLogin(HttpServletRequest request, User user, String validateCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // 检验验证码
//			HttpSession session = request.getSession(true);
//			String sValidateCode = (String) session.getAttribute("validateCode");
//			if (!sValidateCode.equals(validateCode)) {
//				map.put("code", LoginStatusCodeConstants.VALIDATE_CODE_ERROR.getCode());
//				return map;
//			}

            user = loginService.login(user);

            if (user != null) {
                if ("0".equals(user.getLoginErrorCode())) {
                    //查询该用户的角色列表
                    List<UserRole> userRoleList = this.userService.getRolesByUserId(user.getUserId());
                    if (null != userRoleList && userRoleList.size() > 1) {
                        UserRole ur = new UserRole();
                        ur.setRoleId("-1");
                        ur.setRoleName("所有角色");
                        userRoleList.add(ur);
                    }
                    user.setRoles(userRoleList);

                    if (userRoleList != null && userRoleList.size() > 0) {
                        //缓存菜单权限和按钮权限
                        List<RoleRight> roleRightList = this.roleService.getRightsByRole(userRoleList);
                        user.setRights(roleRightList);

                        //缓存登录用户拥有的角色数据权限
                        List<DataRight> dataRightList = this.loginService.getDataRightByRole(user.getRoles());
                        user.setRoleDataRight(dataRightList);
                    }

                    String code = SessionManager.createSession(user, request);
                    user.setLoginErrorCode(code);
                }
            }

            map.put("code", user.getLoginErrorCode());
        } catch (Exception e) {
            map.put("code", LoginStatusCodeConstants.USERNAME_PWD_ERROR.getCode());
            log.error("用户登录发生异常：", e);
        }
        return map;
    }

    /**
     * 注销
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            User user = SessionManager.getSession(request);
            if (null != user) {
                //插入退出日志
                OperateLog sysLog = new OperateLog();
                sysLog.setOperateIp(IPUtil.getIpAddr(request));
                sysLog.setUserId(user.getUserCode());
                sysLog.setUserName(user.getUserName());
                sysLog.setOperateType("5");
                sysLog.setOperateDate(SysUtils.getNowDateTime());
                sysLog.setContent("用户退出");
                logService.insertLog(sysLog);
            }

            SessionManager.destorySession(request);
        } catch (Exception e) {
            log.error("注销用户异常：", e);
        }
        return "redirect:/logon.do";
    }

    /**
     * 获取菜单左树
     *
     * @return
     */
    @RequestMapping("/left")
    public String left(Model model, HttpServletRequest request) {
        try {
            User user = SessionManager.getSession(request);
            model.addAttribute("user", user);
            List<UserRole> userRoleList = user.getRoles();
            //根据角色获取有访问权限的菜单
            Map<String, Set<Menu>> map = this.menuService.getMenuByRole(userRoleList);
            Set<Menu> menuGroupList = map.get("menuGroup");
            Set<Menu> menuList = map.get("menu");
            model.addAttribute("menuGroupList", menuGroupList);
            model.addAttribute("menuList", menuList);
        } catch (Exception e) {
            log.error("获取菜单异常：", e);
        }
        return "left";
    }

    @RequestMapping("/top")
    public String top(Model model, HttpServletRequest request) {
        try {
            User user = SessionManager.getSession(request);
            model.addAttribute("user", user);
        } catch (Exception e) {
            log.error("获取用户Session信息异常：", e);
        }
        return "top";
    }

    @RequestMapping("/welcome")
    public String welcome(Model model, HttpServletRequest request) {
        try {
            User user = SessionManager.getSession(request);
            //查询任务笔数
            int waitCount = tasklistService.queryTaskCountByUserID(user, "1");
            int partCount = tasklistService.queryTaskCountByUserID(user, "2");
            int finishCount = tasklistService.queryTaskCountByUserID(user, "3");
            model.addAttribute("waitCount", waitCount);
            model.addAttribute("partCount", partCount);
            model.addAttribute("finishCount", finishCount);
            model.addAttribute("user", user);
        } catch (Exception e) {
            log.error("获取用户任务信息异常：", e);
        }
        return "welcome";
    }
}
