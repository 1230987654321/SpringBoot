package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Picture;
import com.example.admin.service.PictureService;
import com.example.admin.util.FileCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@Slf4j
@RestController
@RequestMapping("/admin/picture")
public class PictureController {

    private final PictureService pictureService;

    // 获取图片最大上传大小
    @Value("${upload.max-size}")
    private Long maxSize;

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
        if (originalFilename == null) {
            throw new ServiceException(400, "上传文件名不能为空");
        }
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex == -1) {
            throw new ServiceException(422, "文件格式错误,上传文件没有扩展名");
        }
        // 判断文件是否是恶意文件
        try {
            FileCheckUtil.isFileMalicious(file, maxSize);
        } catch (IOException e) {
            throw new ServiceException(500, "检查文件时发生错误");
        }
        // 文件后缀
        String suffix = originalFilename.substring(dotIndex);
        // 新文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 按照日期格式创建目录名
        String dirName = now.format(formatter) + "/";
        // 创建一个目录
        File dir = new File(uploadPath + dirName);
        // 判断目录是否存在
        if (!dir.exists()) {
            // 创建目录
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(uploadPath + dirName + newFileName));
        } catch (IOException e) {
            throw new ServiceException(422, "文件上传失败");
        }
        // 添加图片信息到数据库
        Picture picture = new Picture();
        picture.setUid(uid);
        picture.setSource(source);
        picture.setPath(dirName + newFileName);
        this.pictureService.save(picture);
        Map<String, Object> result = new HashMap<>();
        result.put("id", picture.getId());
        result.put("fileName", dirName + newFileName);
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
