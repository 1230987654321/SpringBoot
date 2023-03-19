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
 * 角色
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("db_role")
public class Role extends Model<Role> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户角色
     */
    @TableField("title")
    private String title;

    /**
     * 控制器id
     */
    @TableField("controlId")
    private String controlId;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态 1正常 0冻结
     */
    @TableField("status")
    private Integer status;

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

    public Role(Role role) {
        Optional.ofNullable(role).ifPresent(e -> {
            this.id = e.getId();
            this.title = e.getTitle();
            this.controlId = e.getControlId();
            this.description = e.getDescription();
            this.status = e.getStatus();
            this.deleted = e.getDeleted();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }
}
