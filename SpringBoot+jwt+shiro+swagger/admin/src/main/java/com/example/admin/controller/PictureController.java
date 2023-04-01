package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.entity.Picture;
import com.example.admin.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 图片表 前端控制器
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Api(tags = "图片管理", value = "图片管理")
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
    @ApiOperation(value = "上传图片", notes = "上传图片")
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
    @ApiOperation(value = "删除图片", notes = "删除图片")
    @DeleteMapping("/deletePictureId")
    public Integer deletePictureId(@RequestParam(name = "id") Integer id) {
        return pictureService.deletePictureId(id);
    }

    /**
     * 查询图片
     *
     * @param id 图片id
     * @return Picture
     */
    @ApiOperation(value = "查询图片", notes = "查询图片")
    @GetMapping("/getPictureId")
    public Picture getPictureId(@RequestParam(name = "id") Integer id) {
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
    @ApiOperation(value = "查询图片列表", notes = "查询图片列表")
    @GetMapping("/getPictureList")
    public IPage<Picture> getPictureList(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, Picture picture) {
        return pictureService.getPictureList(current, size, picture);
    }
}
