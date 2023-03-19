package com.example.admin.mapper;

import com.example.admin.entity.Controllers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 左侧菜单栏 Mapper 接口
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Mapper
public interface ControllersMapper extends BaseMapper<Controllers> {
}
