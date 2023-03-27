package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
