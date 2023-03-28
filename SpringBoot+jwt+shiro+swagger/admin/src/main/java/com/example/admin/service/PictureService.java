package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Picture;

/**
 * <p>
 * 图片表 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
public interface PictureService extends IService<Picture> {
    /**
     * 查询图片列表
     *
     * @param current 当前页
     * @param size    每页条数
     * @param picture 条件
     * @return IPage<Picture>
     */
    IPage<Picture> getPictureList(Integer current, Integer size, Picture picture);
}
