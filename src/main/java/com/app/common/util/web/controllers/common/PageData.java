package com.app.common.util.web.controllers.common;

import com.alibaba.common.lang.Paginator;
import com.iwallet.biz.common.util.PageList;

import java.util.List;

/**
 * 分页化的结果数据，用于展示列表型数据
 *
 * @author xunwu.zy
 */
public class PageData<T> {

    private int total;
    private int currentPage;
    private int pageSize;
    private List<T> pageData;

    /**
     * 构造器
     */
    public PageData() {
    }

    /**
     * 构造器
     *
     * @param list
     */
    public PageData(PageList list) {
        Paginator paginator = list.getPaginator();
        this.total = paginator.getItems();
        this.currentPage = paginator.getPage();
        this.pageSize = paginator.getItemsPerPage();
        this.pageData = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
