package com.example.admin.entity.vo;

import com.example.admin.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class RoleVo extends Role {
    /**
     *
     */
    private List<Integer> controlIdList;

    public RoleVo(Role role) {
        super(role);
    }
}
