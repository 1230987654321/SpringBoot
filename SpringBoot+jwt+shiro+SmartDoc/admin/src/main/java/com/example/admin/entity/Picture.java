package com.example.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 * 图片表
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Setter
@Getter
@NoArgsConstructor
@TableName("db_picture")
public class Picture extends Model<Picture> {

    /**
     * 主键
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "createdAt", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "updatedAt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    public Picture(Picture picture) {
        Optional.ofNullable(picture).ifPresent(p -> {
            this.id = p.getId();
            this.uid = p.getUid();
            this.source = p.getSource();
            this.path = p.getPath();
            this.status = p.getStatus();
            this.deleted = p.getDeleted();
            this.createdAt = p.getCreatedAt();
            this.updatedAt = p.getUpdatedAt();
        });
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
