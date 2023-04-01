package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Picture;
import com.example.admin.mapper.PictureMapper;
import com.example.admin.service.PictureService;
import com.example.admin.util.CheckUtil;
import com.example.admin.util.FileCheckUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
 * 图片表 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {

    private final PictureMapper pictureMapper;
    // 获取图片最大上传大小
    @Value("${spring.servlet.multipart.max-file-size}")
    private Long maxSize;
    // 获取图片上传路径
    @Value("${upload.path}")
    private String uploadPath;

    public PictureServiceImpl(PictureMapper pictureMapper) {
        this.pictureMapper = pictureMapper;
    }

    /**
     * 查询图片列表
     *
     * @param current 当前页
     * @param size    每页条数
     * @param picture 条件
     * @return IPage<Picture> 图片列表
     */
    @Override
    public IPage<Picture> getPictureList(Integer current, Integer size, Picture picture) {
        CheckUtil.checkPage(current, size);
        Page<Picture> page = new Page<>(current, size);
        LambdaQueryWrapper<Picture> wrapper = Wrappers.lambdaQuery(Picture.class);
        wrapper.eq(picture.getSource() != null, Picture::getSource, picture.getSource());
        wrapper.eq(Picture::getStatus, 1);
        return pictureMapper.selectPage(page, wrapper);
    }

    /**
     * 添加图片
     *
     * @param file   图片
     * @param uid    用户id
     * @param source 来源
     * @return Map<String, Object> 图片id和图片路径
     */
    @Override
    public Map<String, Object> addPicture(MultipartFile file, Integer uid, Integer source) {
        // 参数校验
        CheckUtil.checkIntegerNotZero(uid, "用户id不能为空");
        CheckUtil.checkIntegerNotNull(source, "图片来源不能为空");
        // 校验文件是否为空,并上传,返回新文件名
        String newFileName = upload(file);
        // 保存图片信息
        Picture picture = new Picture();
        picture.setUid(uid);
        picture.setSource(source);
        picture.setPath(newFileName);
        pictureMapper.insert(picture);
        // 返回图片信息
        Map<String, Object> map = new HashMap<>(2);
        map.put("id", picture.getId());
        map.put("fileName", newFileName);
        return map;
    }

    /**
     * 删除图片
     *
     * @param id 图片id
     * @return Integer 删除结果 1成功 0失败
     * @throws ServiceException 业务异常
     */
    @Override
    public Integer deletePictureId(Integer id) {
        // 参数校验
        CheckUtil.checkIntegerNotZero(id, "图片id不能为空");
        // 查询图片信息
        Picture picture = pictureMapper.selectById(id);
        CheckUtil.checkObjectNotNull(picture, 404, "图片不存在");
        // 删除图片
        try {
            return pictureMapper.deleteById(picture);
        } catch (Exception e) {
            throw new ServiceException(500, "删除图片失败");
        }
    }

    /**
     * 根据id查询图片信息
     *
     * @param id 图片id
     * @return Picture 图片信息
     * @throws ServiceException 业务异常
     */
    @Override
    public Picture getPictureById(Integer id) {
        // 参数校验
        CheckUtil.checkIntegerNotZero(id, "图片id不能为空");
        // 查询图片信息
        Picture picture = pictureMapper.selectById(id);
        CheckUtil.checkObjectNotNull(picture, 404, "图片不存在");
        return picture;
    }

    /**
     * 上传图片
     *
     * @param file 图片
     * @return String 新文件名
     * @throws ServiceException 业务异常
     */
    public String upload(MultipartFile file) {
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        CheckUtil.checkStringNotEmpty(originalFilename, "上传文件名不能为空");
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
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 按照日期格式创建目录名
        String dirName = LocalDateTime.now().format(formatter) + "/";
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
        return dirName + newFileName;
    }

}
