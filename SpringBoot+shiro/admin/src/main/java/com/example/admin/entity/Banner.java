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
 * 轮播
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Getter
@Setter
@TableName("db_banner")
public class Banner extends Model<Banner> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("cover")
    private Integer cover;

    /**
     * 链接地址
     */
    @TableField("url")
    private String url;

    /**
     * 排序 越大越靠前
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态 1正常 0隐藏
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

    public Banner(Banner banner) {
        Optional.ofNullable(banner).ifPresent(e ->{
            this.id = e.getId();
            this.cover = e.getCover();
            this.url = e.getUrl();
            this.sort = e.getSort();
            this.status = e.getStatus();
            this.deleted = e.getDeleted();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
