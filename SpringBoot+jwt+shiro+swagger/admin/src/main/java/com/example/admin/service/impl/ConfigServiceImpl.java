package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Config;
import com.example.admin.mapper.ConfigMapper;
import com.example.admin.service.ConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基本配置 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

}
