package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * 管理员操作日志
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Setter
@Getter
@NoArgsConstructor
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

    public AdminLog(AdminLog adminLog) {
        Optional.ofNullable(adminLog).ifPresent(log -> {
            this.id = log.getId();
            this.adminId = log.getAdminId();
            this.type = log.getType();
            this.desc = log.getDesc();
            this.changeId = log.getChangeId();
            this.json = log.getJson();
            this.createdAt = log.getCreatedAt();
            this.updatedAt = log.getUpdatedAt();
        });
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
