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
import lombok.Setter;

/**
 * <p>
 * 图片表
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Getter
@Setter
@TableName("db_picture")
public class Picture extends Model<Picture> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 0后台 1前端
     */
    @TableField("source")
    private Integer source;

    /**
     * 图片路径
     */
    @TableField("path")
    private String path;

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

    public Picture(Picture picture) {
        Optional.ofNullable(picture).ifPresent(e -> {
            this.id = e.getId();
            this.uid = e.getUid();
            this.source = e.getSource();
            this.path = e.getPath();
            this.status = e.getStatus();
            this.deleted = e.getDeleted();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }
}
