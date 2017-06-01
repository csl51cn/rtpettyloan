package com.global.framework.system.web.common;

import com.global.framework.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 系统初始化Servlet
 *
 * @author cqchenf@qq.com
 * @version v1.0
 * @date 2011-7-2 下午01:38:20
 */
public class InitServlet extends HttpServlet {

    private static final long serialVersionUID = -9052210569868988388L;
    private static final Logger logger = LoggerFactory
            .getLogger(InitServlet.class);
    public static boolean isSystemStrat = false;

    public InitServlet() {

    }

    public void init(ServletConfig config) throws ServletException {
        try {
            this.initCacheService();
            //new TimeoutTask().start();
            //new Receiver().start();// 启动SOCKET接口服务
            //AutoImportSafeExRateThread.start();//定时任务服务
        } catch (Exception e) {
            logger.error("系统启动失败:", e);
            System.exit(0);
        }
        logger.info("系统启动成功...");
    }

    /**
     * 加载缓存服务
     *
     * @throws BaseException
     */
    private void initCacheService() throws BaseException {
        CacheService.getInstance();
    }

    public void destroy() {
        super.destroy();
    }

}
