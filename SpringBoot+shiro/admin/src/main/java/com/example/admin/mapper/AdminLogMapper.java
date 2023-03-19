package com.example.admin.mapper;

import com.example.admin.entity.AdminLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员操作日志 Mapper 接口
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Mapper
public interface AdminLogMapper extends BaseMapper<AdminLog> {

}
