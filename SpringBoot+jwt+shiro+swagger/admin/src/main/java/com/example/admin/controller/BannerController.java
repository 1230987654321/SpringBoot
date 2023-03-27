package com.example.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.admin.entity.vo.BannerVo;
import com.example.admin.service.BannerService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 轮播 前端控制器
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-27 11:53:00
 */
@RestController
@RequestMapping("/admin/banner")
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    /**
     * 添加轮播图
     *
     * @param bannerVo 轮播图
     * @return int 1成功 0失败
     */
    @PostMapping("/addBanner")
    public int addBanner(BannerVo bannerVo) {
        return bannerService.addBanner(bannerVo);
    }

    /**
     * 查询轮播图详情
     *
     * @param id 轮播图id
     * @return BannerVo
     */
    @GetMapping("/getBannerById")
    public BannerVo getBannerById(Integer id) {
        return bannerService.getBannerById(id);
    }

    /**
     * 查询轮播图列表
     *
     * @param current  当前页
     * @param size     每页条数
     * @param bannerVo 轮播图
     * @return BannerVo
     */
    @GetMapping("/getBannerList")
    public IPage<BannerVo> getBannerList(Integer current, Integer size, BannerVo bannerVo) {
        return bannerService.getBannerList(current, size, bannerVo);
    }

    /**
     * 修改轮播图状态
     *
     * @param id     轮播图id
     * @param status 状态
     * @return int 1成功 0失败
     */
    @PutMapping("/updateBannerStatus")
    public int updateBannerStatus(Integer id, Integer status) {
        return bannerService.updateBannerStatus(id, status);
    }

    /**
     * 删除轮播图
     *
     * @param id 轮播图id
     * @return int 1成功 0失败
     */
    @DeleteMapping("/deleteBanner")
    public int deleteBanner(Integer id) {
        return bannerService.deleteBanner(id);
    }
}
