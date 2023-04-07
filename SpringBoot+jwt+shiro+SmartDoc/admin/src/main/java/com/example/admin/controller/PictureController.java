package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.entity.Picture;
import com.example.admin.service.PictureService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 图片表
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@RestController
@RequestMapping("/admin/picture")
public class PictureController {
    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    /**
     * 上传图片
     *
     * @param file   图片
     * @param uid    用户id
     * @param source 来源
     * @return Map<String, Object>
     */
    @PostMapping("/addPicture")
    public Map<String, Object> addPicture(MultipartFile file, @RequestParam(name = "uid") Integer uid, @RequestParam(name = "source") Integer source) {
        return pictureService.addPicture(file, uid, source);
    }

    /**
     * 删除图片
     *
     * @param id 图片id
     * @return Integer 1成功 0失败
     */
    @DeleteMapping("/deletePictureById")
    public Integer deletePictureById(@RequestParam(name = "id") Integer id) {
        return pictureService.deletePictureById(id);
    }

    /**
     * 查询图片
     *
     * @param id 图片id
     * @return Picture
     */
    @GetMapping("/getPictureById")
    public Picture getPictureById(@RequestParam(name = "id") Integer id) {
        return pictureService.getPictureById(id);
    }

    /**
     * 查询图片列表
     *
     * @param current 页码
     * @param size    每页数量
     * @param picture 图片
     * @return IPage<Picture>
     */
    @GetMapping("/getPicturePageList")
    public IPage<Picture> getPicturePageList(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, Picture picture) {
        return pictureService.getPicturePageList(current, size, picture);
    }
}
