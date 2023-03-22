package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 左侧菜单栏
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("db_controllers")
public class Controllers extends Model<Controllers> {

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
     * 状态 1正常 0冻结
     */
    @TableField("status")
    private Boolean status;

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
    @TableField("is_default")
    private Integer isDefault;

    /**
     * 1:管理员后台 0:其他后台
     */
    @TableField("is_admin")
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
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

    public Controllers(Controllers controllers) {
        Optional.ofNullable(controllers).ifPresent(e ->{
            this.id = e.getId();
            this.pid = e.getPid();
            this.path = e.getPath();
            this.component = e.getComponent();
            this.name = e.getName();
            this.status = e.getStatus();
            this.redirect = e.getRedirect();
            this.title = e.getTitle();
            this.icon = e.getIcon();
            this.sort = e.getSort();
            this.isDefault = e.getIsDefault();
            this.isAdmin = e.getIsAdmin();
            this.deleted = e.getDeleted();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }
}
