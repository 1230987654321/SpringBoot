package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 管理员操作日志
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Getter
@Setter
@TableName("db_admin_log")
public class AdminLog extends Model<AdminLog> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 管理员ID
     */
    @TableField("admin_id")
    private Integer adminId;

    /**
     * 1:新增  2:编辑  3:删除 0:登录
     */
    @TableField("type")
    private Integer type;

    /**
     * 表描述
     */
    @TableField("desc")
    private String desc;

    /**
     * 改变的ID
     */
    @TableField("change_id")
    private String changeId;

    /**
     * 操作的数据
     */
    @TableField("json")
    private String json;

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

    public AdminLog(AdminLog adminLog) {
        Optional.ofNullable(adminLog).ifPresent(e -> {
            this.id = e.getId();
            this.adminId = e.getAdminId();
            this.type = e.getType();
            this.desc = e.getDesc();
            this.changeId = e.getChangeId();
            this.json = e.getJson();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }
}
