package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 左侧菜单栏
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-29 05:00:18
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("db_menu")
public class Menu extends Model<Menu> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父id 0:顶级
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 路径
     */
    @TableField("path")
    private String path;

    /**
     * 页面地址
     */
    @TableField("component")
    private String component;

    /**
     * 名称 要唯一
     */
    @TableField("name")
    private String name;

    /**
     * 是否隐藏 0:隐藏 1:显示
     */
    @TableField("hidden")
    private Byte hidden;

    /**
     * 重定向
     */
    @TableField("redirect")
    private String redirect;

    /**
     * 菜单栏的名字
     */
    @TableField("title")
    private String title;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 越小越靠前
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 是否默认 1: 默认
     */
    @TableField("isDefault")
    private Integer isDefault;

    /**
     * 1:管理员后台 0:其他后台
     */
    @TableField("isAdmin")
    private Integer isAdmin;

    /**
     * 是否删除 1是 0否
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "createdAt", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "updatedAt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private List<Menu> children;

    public Menu(Menu menu) {
        Optional.ofNullable(menu).ifPresent(m -> {
            this.id = m.getId();
            this.pid = m.getPid();
            this.path = m.getPath();
            this.component = m.getComponent();
            this.name = m.getName();
            this.hidden = m.getHidden();
            this.redirect = m.getRedirect();
            this.title = m.getTitle();
            this.icon = m.getIcon();
            this.sort = m.getSort();
            this.isDefault = m.getIsDefault();
            this.isAdmin = m.getIsAdmin();
            this.deleted = m.getDeleted();
            this.createdAt = m.getCreatedAt();
            this.updatedAt = m.getUpdatedAt();
        });
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
