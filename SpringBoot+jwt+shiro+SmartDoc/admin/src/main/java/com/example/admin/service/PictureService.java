package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
    IPage<Picture> getPicturePageList(Integer current, Integer size, Picture picture);

    /**
     * 上传图片
     *
     * @param file   图片
     * @param uid    用户id
     * @param source 来源
     * @return Map<String, Object>
     */
    Map<String, Object> addPicture(MultipartFile file, Integer uid, Integer source);

    /**
     * 删除图片
     *
     * @param id 图片id
     * @return Integer 1成功 0失败
     */
    Integer deletePictureById(Integer id);

    /**
     * 查询图片
     *
     * @param id 图片id
     * @return Picture
     */
    Picture getPictureById(Integer id);
}
