package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.entity.AdminLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员操作日志 Mapper 接口
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Mapper
public interface AdminLogMapper extends BaseMapper<AdminLog> {

}
