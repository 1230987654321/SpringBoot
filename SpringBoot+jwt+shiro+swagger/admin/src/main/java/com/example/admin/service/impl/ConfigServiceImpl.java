package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Config;
import com.example.admin.entity.Picture;
import com.example.admin.entity.vo.ConfigVo;
import com.example.admin.mapper.ConfigMapper;
import com.example.admin.mapper.PictureMapper;
import com.example.admin.service.ConfigService;
import com.example.admin.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 基本配置 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Slf4j
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    private final ConfigMapper configMapper;
    private final PictureMapper pictureMapper;

    public ConfigServiceImpl(ConfigMapper configMapper, PictureMapper pictureMapper) {
        this.configMapper = configMapper;
        this.pictureMapper = pictureMapper;
    }

    /**
     * 查询配置
     *
     * @return 配置
     */
    @Override
    public ConfigVo getConfig() {
        // 先查询用户信息
        Config config = configMapper.selectOne(new LambdaQueryWrapper<Config>().eq(Config::getId, 1));
        CheckUtil.checkObjectNotNull(config, 404, "配置不存在");
        // 转化为Vo
        ConfigVo configVo = Optional.ofNullable(config).map(ConfigVo::new).orElse(null);
        // 从其它表查询信息再封装到Vo
        Optional.ofNullable(configVo).ifPresent(this::addPath);
        return configVo;
    }

    /**
     * 修改配置
     *
     * @param config 配置
     * @return int 修改条数
     * @throws ServiceException 业务异常
     */
    @Override
    public int updateConfig(Config config) {
        CheckUtil.checkIntegerNotNull(config.getId(), "Id 不能为空");
        Config configInfo = configMapper.selectById(config.getId());
        CheckUtil.checkObjectNotNull(configInfo, 404, "配置不存在");
        return configMapper.updateById(config);
    }

    /**
     * 补充配置图片路径
     *
     * @param configVo 配置
     */
    private void addPath(ConfigVo configVo) {
        // 查询图片路径
        LambdaQueryWrapper<Picture> logoWrapper = Wrappers.lambdaQuery(Picture.class).eq(Picture::getId, configVo.getLogo()).select(Picture::getPath);
        Picture logoPath = pictureMapper.selectOne(logoWrapper);
        Optional.ofNullable(logoPath).ifPresent(e -> configVo.setLogoPath(e.getPath()));
        // 查询图片路径
        LambdaQueryWrapper<Picture> ShareImgWrapper = Wrappers.lambdaQuery(Picture.class).eq(Picture::getId, configVo.getShareImg()).select(Picture::getPath);
        Picture shareImgPath = pictureMapper.selectOne(ShareImgWrapper);
        Optional.ofNullable(shareImgPath).ifPresent(e -> configVo.setShareImgPath(e.getPath()));
    }
}
