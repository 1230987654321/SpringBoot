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
 * 用户
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:51
 */
@Getter
@Setter
@TableName("db_user")
public class User extends Model<User> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * openid
     */
    @TableField("openid")
    private String openid;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 头像
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 性别 0未知 1男 2女
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 手机号(授权)
     */
    @TableField("tel")
    private String tel;

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

    public User(User user) {
        Optional.ofNullable(user).ifPresent(e -> {
            this.id = e.getId();
            this.openid = e.getOpenid();
            this.nickName = e.getNickName();
            this.avatarUrl = e.getAvatarUrl();
            this.gender = e.getGender();
            this.tel = e.getTel();
            this.status = e.getStatus();
            this.deleted = e.getDeleted();
            this.createdAt = e.getCreatedAt();
            this.updatedAt = e.getUpdatedAt();
        });
    }
}
