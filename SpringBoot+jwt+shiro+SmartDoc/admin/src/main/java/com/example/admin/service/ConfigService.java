package com.example.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Config;
import com.example.admin.entity.vo.ConfigVo;

/**
 * <p>
 * 基本配置 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
public interface ConfigService extends IService<Config> {
    /**
     * 查询配置
     *
     * @return ConfigVo
     */
    ConfigVo getConfig();

    /**
     * 修改配置
     *
     * @param config 配置
     * @return Config
     */
    int updateConfigById(Config config);

}
