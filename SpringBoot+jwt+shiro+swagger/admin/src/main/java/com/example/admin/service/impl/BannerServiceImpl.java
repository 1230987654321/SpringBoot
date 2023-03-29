package com.example.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Banner;
import com.example.admin.entity.Picture;
import com.example.admin.entity.vo.BannerVo;
import com.example.admin.mapper.BannerMapper;
import com.example.admin.mapper.PictureMapper;
import com.example.admin.service.BannerService;
import com.example.admin.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * <p>
 * 轮播 服务实现类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@Slf4j
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    private final BannerMapper bannerMapper;
    private final PictureMapper pictureMapper;

    public BannerServiceImpl(BannerMapper bannerMapper, PictureMapper pictureMapper) {
        this.bannerMapper = bannerMapper;
        this.pictureMapper = pictureMapper;
    }

    /**
     * 添加轮播图
     *
     * @param bannerVo 轮播图
     * @return int
     * @throws ServiceException 业务异常
     */
    @Override
    public int addBanner(BannerVo bannerVo) {
        Banner banner = new Banner();
        banner.setCover(bannerVo.getCover());
        banner.setUrl(bannerVo.getUrl());
        banner.setSort(bannerVo.getSort());
        banner.setStatus(bannerVo.getStatus());
        try {
            return bannerMapper.insert(banner);
        } catch (Exception e) {
            log.error("添加轮播图失败 =======>", e);
            throw new ServiceException(500, "添加失败");
        }
    }


    /**
     * 查询轮播图
     *
     * @param id 轮播图id
     * @return BannerVo
     */
    @Override
    public BannerVo getBannerById(Integer id) {
        LambdaQueryWrapper<Banner> wrapper = Wrappers.lambdaQuery(Banner.class).eq(Banner::getId, id);
        // 先查询用户信息
        Banner banner = bannerMapper.selectOne(wrapper);
        // 转化为Vo
        BannerVo bannerVo = Optional.ofNullable(banner).map(BannerVo::new).orElse(null);
        // 从其它表查询信息再封装到Vo
        Optional.ofNullable(bannerVo).ifPresent(this::addCoverPath);
        return bannerVo;
    }

    /**
     * 查询轮播图列表
     *
     * @param current  当前页
     * @param size     每页条数
     * @param bannerVo 查询条件
     * @return IPage<BannerVo>
     */
    @Override
    public IPage<BannerVo> getBannerList(Integer current, Integer size, BannerVo bannerVo) {
        Page<Banner> page = new Page<>(current, size);
        LambdaQueryWrapper<Banner> wrapper = Wrappers.lambdaQuery(Banner.class).orderByDesc(Banner::getSort).orderByDesc(Banner::getCreatedAt);
        // 查询轮播图列表
        IPage<Banner> bannerPage = bannerMapper.selectPage(page, wrapper);
        IPage<BannerVo> bannerVoIPage = bannerPage.convert(BannerVo::new);
        // 补充轮播图封面路径
        if (bannerVoIPage.getRecords().size() > 0) {
            addCoverPath(bannerVoIPage);
        }
        return bannerVoIPage;
    }

    /**
     * 修改轮播图状态
     *
     * @param id     轮播图id
     * @param status 状态
     * @return int 修改条数
     * @throws ServiceException 业务异常
     */
    @Override
    public int updateBannerStatus(Integer id, Integer status) {
        CheckUtil.checkIntegerNotNull(id, "轮播图id不能为空");
        CheckUtil.checkIntegerNotNull(status, "轮播图状态不能为空");
        Banner banner = bannerMapper.selectById(id);
        CheckUtil.checkObjectNotNull(banner, 404, "轮播图不存在");
        banner.setStatus(status);
        return bannerMapper.updateById(banner);
    }

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return int 修改条数
     * @throws ServiceException 业务异常
     */
    @Override
    public int updateBanner(Banner banner) {
        CheckUtil.checkIntegerNotNull(banner.getId(), "轮播图id不能为空");
        try {
            return bannerMapper.updateById(banner);
        } catch (Exception e) {
            log.error("修改轮播图失败 =======>", e);
            throw new ServiceException(500, "更新失败");
        }
    }

    /**
     * 删除轮播图
     *
     * @param id 轮播图id
     * @return int 删除条数
     * @throws ServiceException 业务异常
     */
    @Override
    public int deleteBanner(Integer id) {
        CheckUtil.checkIntegerNotNull(id, "轮播图id不能为空");
        try {
            return bannerMapper.deleteById(id);
        } catch (Exception e) {
            log.error("删除轮播图失败 =======>", e);
            throw new ServiceException(500, "删除失败");
        }
    }

    /**
     * 补充轮播图封面路径
     *
     * @param bannerVo 轮播图
     */
    private void addCoverPath(BannerVo bannerVo) {
        LambdaQueryWrapper<Picture> wrapper = Wrappers.lambdaQuery(Picture.class).eq(Picture::getId, bannerVo.getCover());
        Picture picture = pictureMapper.selectOne(wrapper);
        Optional.ofNullable(picture).ifPresent(e -> bannerVo.setCoverPath(e.getPath()));
    }

    /**
     * 补充轮播图封面路径
     *
     * @param bannerVoIPage 轮播图列表
     */
    private void addCoverPath(IPage<BannerVo> bannerVoIPage) {
        // 提取用户userId，方便批量查询
        Set<Integer> coverIds = bannerVoIPage.getRecords().stream().map(Banner::getCover).collect(toSet());
        // 根据deptId查询deptName
        List<Picture> picture = pictureMapper.selectList(Wrappers.lambdaQuery(Picture.class).in(Picture::getId, coverIds));
        // 构造映射关系，方便匹配deptId与deptName
        Map<Integer, String> hashMap = picture.stream().collect(toMap(Picture::getId, Picture::getPath));
        // 将查询补充的信息添加到Vo中
        bannerVoIPage.convert(e -> e.setCoverPath(hashMap.get(e.getCover())));
    }

}
