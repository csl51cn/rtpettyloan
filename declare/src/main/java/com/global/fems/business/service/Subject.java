package com.global.fems.business.service;

import com.global.framework.dbutils.support.Entity;

/**
 * @author: Senlin.Deng
 * @Description:
 * @date: Created in 2018/9/7 10:26
 * @Modified By:
 */
public interface Subject {

    /**
     * 通知观察者
     * @param entity
     */
    void notifyObservers(Entity entity);

}
