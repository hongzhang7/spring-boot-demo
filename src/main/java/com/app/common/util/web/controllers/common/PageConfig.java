package com.app.common.util.web.controllers.common;

/**
 * Page 分页查询相关配置信息
 *
 * @author xunwu.zy
 */
public class PageConfig {

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int DEFAULT_PAGE_NUM = 1;

    private final int pageSize;

    private final int pageNum;

    public PageConfig() {
        pageSize = DEFAULT_PAGE_SIZE;
        pageNum = DEFAULT_PAGE_NUM;
    }

    public PageConfig(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }
}
