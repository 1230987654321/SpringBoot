package com.example.admin.service.impl;

import com.example.admin.entity.AdminLog;
import com.example.admin.mapper.AdminLogMapper;
import com.example.admin.service.AdminLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员操作日志 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
@Service
public class AdminLogServiceImpl extends ServiceImpl<AdminLogMapper, AdminLog> implements AdminLogService {

}
