package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Setter
@Getter
@NoArgsConstructor
@TableName("db_admin")
public class Admin extends Model<Admin> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    @JsonIgnore
    private String password;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 0冻结1正常
     */
    @TableField("status")
    private Integer status;

    /**
     * 1后台管理员
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

    public Admin(Admin admin) {
        Optional.ofNullable(admin).ifPresent(e ->{
            this.id = e.getId();
            this.username = e.getUsername();
            this.password = e.getPassword();
            this.roleId = e.getRoleId();
            this.status = e.getStatus();
            this.isAdmin = e.getIsAdmin();
            this.deleted = e.getDeleted();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }
}
