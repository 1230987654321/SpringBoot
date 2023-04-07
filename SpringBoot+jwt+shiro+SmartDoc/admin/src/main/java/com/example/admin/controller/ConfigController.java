package com.example.admin.controller;

import com.example.admin.entity.Config;
import com.example.admin.entity.vo.ConfigVo;
import com.example.admin.service.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基本配置
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@RestController
@RequestMapping("/admin/config")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 查询配置
     *
     * @return ConfigVo
     */
    @GetMapping("/getConfig")
    public ConfigVo getConfig() {
        return configService.getConfig();
    }

    /**
     * 修改配置
     *
     * @param config 配置
     * @return int 1成功 0失败
     */
    @PutMapping("/updateConfigById")
    public int updateConfigById(Config config) {
        return configService.updateConfigById(config);
    }


}
