package com.example.admin.entity.vo;

import com.example.admin.entity.Admin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class AdminVo extends Admin {

    /**
     * 角色名称
     */
    private String RoleName;

    public AdminVo(Admin admin) {
        super(admin);
    }
}
