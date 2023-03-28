package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Picture;
import com.example.admin.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 图片表 前端控制器
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@RestController
@RequestMapping("/admin/picture")
public class PictureController {

    private final PictureService pictureService;
    // 获取图片上传路径
    @Value("${upload.path}")
    private String uploadPath;

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
    @PostMapping("/upload")
    public Map<String, Object> upload(MultipartFile file, Integer uid, Integer source) {
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        // 文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 新文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        // 创建一个目录
        File dir = new File(uploadPath);
        // 判断目录是否存在
        if (!dir.exists()) {
            // 创建目录
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(uploadPath + newFileName));
        } catch (IOException e) {
            throw new ServiceException(422, "文件上传失败");
        }
        // 添加图片信息到数据库
        Picture picture = new Picture();
        picture.setUid(uid);
        picture.setSource(source);
        picture.setPath(newFileName);
        this.pictureService.save(picture);
        Map<String, Object> result = new HashMap<>();
        result.put("id", picture.getId());
        result.put("fileName", newFileName);
        return result;
    }

    /**
     * 删除图片
     *
     * @param id 图片id
     * @return boolean true成功 false失败
     */
    @DeleteMapping("/delete")
    public boolean delete(Integer id) {
        return pictureService.removeById(id);
    }

    /**
     * 查询图片
     *
     * @param id 图片id
     * @return Picture
     */
    @GetMapping("/getPicture")
    public Picture getPicture(Integer id) {
        return pictureService.getById(id);
    }

    /**
     * 查询图片列表
     *
     * @param page    页码
     * @param size    每页数量
     * @param picture 图片
     * @return IPage<Picture>
     */
    @GetMapping("/getPictureList")
    public IPage<Picture> getPictureList(Integer page, Integer size, Picture picture) {
        return pictureService.getPictureList(page, size, picture);
    }

}
