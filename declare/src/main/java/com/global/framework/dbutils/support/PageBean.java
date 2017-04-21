//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.global.framework.dbutils.support;

import java.util.ArrayList;
import java.util.List;

public class PageBean {
    private Long totalRows = Long.valueOf(0L);
    private List<?> dataList;
    private Integer page = Integer.valueOf(1);
    private Integer rows = Integer.valueOf(20);
    private String sort;
    private String order = "asc";

    public PageBean() {
    }

    public PageBean(Long totalRows, List<?> dataList) {
        this.totalRows = totalRows;
        this.dataList = dataList;
    }

    public PageBean(Long totalRows, List<?> dataList, Integer rows) {
        this.totalRows = totalRows;
        this.dataList = dataList;
        this.rows = rows;
    }

    public PageBean(Long totalRows, List<?> dataList, Integer page, Integer rows) {
        this.totalRows = totalRows;
        this.dataList = dataList;
        this.page = page;
        this.rows = rows;
    }

    public Long getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(Long totalRows) {
        this.totalRows = totalRows;
    }

    public List<?> getDataList() {
        if(this.dataList == null) {
            this.dataList = new ArrayList();
        }

        return this.dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return this.rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
