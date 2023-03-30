package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Picture;
import com.example.admin.mapper.PictureMapper;
import com.example.admin.service.PictureService;
import com.example.admin.util.CheckUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    private final PictureMapper pictureMapper;

    public PictureServiceImpl(PictureMapper pictureMapper) {
        this.pictureMapper = pictureMapper;
    }

    /**
     * 查询图片列表
     *
     * @param current 当前页
     * @param size    每页条数
     * @param picture 条件
     * @return IPage<Picture>
     */
    public IPage<Picture> getPictureList(Integer current, Integer size, Picture picture) {
        CheckUtil.checkPage(current, size);
        Page<Picture> page = new Page<>(current, size);
        LambdaQueryWrapper<Picture> wrapper = Wrappers.lambdaQuery(Picture.class);
        wrapper.eq(picture.getSource() != null, Picture::getSource, picture.getSource());
        wrapper.eq(Picture::getStatus, 1);
        return pictureMapper.selectPage(page, wrapper);
    }
}
