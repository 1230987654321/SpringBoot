package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.AdminLog;
import com.example.admin.mapper.AdminLogMapper;
import com.example.admin.service.AdminLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员操作日志 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Service
public class AdminLogServiceImpl extends ServiceImpl<AdminLogMapper, AdminLog> implements AdminLogService {

}
