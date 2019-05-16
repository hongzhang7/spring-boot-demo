package com.app.core.model;

/**
 * 角色枚举
 *
 * @author xunwu.zy
 */
public enum RoleEnum {

    /**
     * 平台管理员
     */
    PLATFORM_ADMIN("MINDV_SURVEY_PLATFORM_MANAGER", "平台管理员", 1 + (1 << 1) + (1 << 2), 1),
    /**
     * 项目管理员
     */
    PROJECT_ADMIN("${PROJECT_MANAGER_PERMISSION}", "项目管理员", (1 << 1) + (1 << 2), 1 << 1),
    /**
     * 项目成员
     */
    PROJECT_USER("", "普通用户", 1 << 2, 1 << 2);

    private final String permission;

    private final String permissionName;

    private final int owned;

    private final int flag;

    /**
     * 构造方法
     *
     * @param permission     对应的权限码，被 ${} 包裹的部分需要从 ProjectDO#config 中获取
     * @param permissionName
     * @param owned          角色拥有的权限
     * @param flag           角色标记权限
     */
    RoleEnum(String permission, String permissionName, int owned, int flag) {
        this.permission = permission;
        this.permissionName = permissionName;
        this.owned = owned;
        this.flag = flag;
    }

    /**
     * Getter For permission
     *
     * @return permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Getter For permissionName
     *
     * @return permissionName
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Getter For owned
     *
     * @return owned
     */
    public int getOwned() {
        return owned;
    }

    /**
     * Getter For flag
     *
     * @return flag
     */
    public int getFlag() {
        return flag;
    }
}
