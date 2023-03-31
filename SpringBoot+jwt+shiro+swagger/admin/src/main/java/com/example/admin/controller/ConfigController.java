package com.example.admin.controller;

import com.example.admin.entity.Config;
import com.example.admin.entity.vo.ConfigVo;
import com.example.admin.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 基本配置 前端控制器
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Api(tags = "基本配置管理", value = "基本配置管理")
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
    @ApiOperation(value = "查询配置", notes = "查询配置")
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
    @ApiOperation(value = "修改配置", notes = "修改配置")
    @PutMapping("/updateConfig")
    public int updateConfig(Config config) {
        return configService.updateConfig(config);
    }


}
