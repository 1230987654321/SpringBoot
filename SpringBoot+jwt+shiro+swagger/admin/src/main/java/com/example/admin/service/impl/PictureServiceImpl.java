package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.entity.Picture;
import com.example.admin.mapper.PictureMapper;
import com.example.admin.service.PictureService;
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

}
