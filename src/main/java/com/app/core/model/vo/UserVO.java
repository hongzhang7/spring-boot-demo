package com.app.core.model.vo;

/**
 * 用户视图信息
 *
 * @author xunwu.zy
 */
public class UserVO extends BaseVO {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
