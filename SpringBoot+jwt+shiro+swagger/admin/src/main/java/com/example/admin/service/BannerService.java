package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Banner;
import com.example.admin.entity.vo.BannerVo;

/**
 * <p>
 * 轮播 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
public interface BannerService extends IService<Banner> {
    /**
     * 添加轮播图
     *
     * @param bannerVo 轮播图
     * @return BannerVo
     */
    int addBanner(BannerVo bannerVo);

    /**
     * 查询轮播图
     *
     * @param id 轮播图id
     * @return BannerVo
     */
    BannerVo getBannerById(Integer id);

    /**
     * 查询轮播图列表
     *
     * @param current  当前页
     * @param size     每页条数
     * @param bannerVo 条件
     * @return IPage<BannerVo>
     */
    IPage<BannerVo> getBannerList(Integer current, Integer size, BannerVo bannerVo);

    /**
     * 修改轮播图状态
     *
     * @param id     轮播图id
     * @param status 状态
     * @return Banner
     */
    int updateBannerStatus(Integer id, Integer status);

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return Banner
     */
    int updateBanner(Banner banner);

    /**
     * 删除轮播图
     *
     * @param id 轮播图id
     * @return BannerVo
     */
    int deleteBanner(Integer id);
}
