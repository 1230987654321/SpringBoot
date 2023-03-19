package com.example.admin.mapper;

import com.example.admin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:51
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
