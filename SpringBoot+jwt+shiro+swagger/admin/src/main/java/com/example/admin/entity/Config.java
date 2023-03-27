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
 * 基本配置
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Setter
@Getter
@NoArgsConstructor
@TableName("db_config")
public class Config extends Model<Config> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 平台名称
     */
    @TableField("title")
    private String title;

    /**
     * logo
     */
    @TableField("logo")
    private Integer logo;

    /**
     * 默认转发标题
     */
    @TableField("share_title")
    private String shareTitle;

    /**
     * 默认转发描述
     */
    @TableField("share_desc")
    private String shareDesc;

    /**
     * 默认转发图片（支持PNG及JPG。显示图片长宽比是 5:4）
     */
    @TableField("share_img")
    private Integer shareImg;

    /**
     * 客服电话
     */
    @TableField("tel")
    private String tel;

    /**
     * 关于我们
     */
    @TableField("about_us")
    private String aboutUs;

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

    public Config(Config config) {
        Optional.ofNullable(config).ifPresent(e -> {
            this.id = e.getId();
            this.title = e.getTitle();
            this.logo = e.getLogo();
            this.shareTitle = e.getShareTitle();
            this.shareDesc = e.getShareDesc();
            this.shareImg = e.getShareImg();
            this.tel = e.getTel();
            this.aboutUs = e.getAboutUs();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
