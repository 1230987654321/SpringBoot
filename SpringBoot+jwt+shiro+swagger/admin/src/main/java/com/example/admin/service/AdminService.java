package com.example.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.common.ServiceException;
import com.example.admin.entity.Admin;
import com.example.admin.entity.vo.AdminVo;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author 贲玉柱
 * @since 2023-03-04 05:12:50
 */
public interface AdminService extends IService<Admin> {

    /**
     * 添加管理员
     *
     * @param admin 管理员信息
     * @throws ServiceException 业务异常
     */
    Integer addAdmin(Admin admin);

    /**
     * 查询管理员列表
     *
     * @param current 当前页
     * @param size    每页显示条数
     * @param adminVo 管理员信息
     * @return IPage<Admin>
     */
    IPage<AdminVo> getAdminList(Integer current, Integer size, AdminVo adminVo);

    /**
     * 根据id查询管理员
     *
     * @param id 管理员id
     * @return Admin
     * @throws ServiceException 业务异常
     */
    AdminVo getAdminById(Integer id);

    /**
     * 根据用户名和密码查询管理员
     *
     * @param username 用户名
     * @param password 密码
     * @throws ServiceException 业务异常
     */
    void getUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查询管理员
     *
     * @param username 用户名
     * @return Admin
     * @throws ServiceException 业务异常
     */
    AdminVo getAdminByUsername(String username);

    /**
     * 修改管理员状态
     *
     * @param id     管理员id
     * @param status 管理员状态 0：禁用 1：启用
     * @throws ServiceException 业务异常
     */
    Integer updateStatus(Integer id, Integer status);

    /**
     * 修改管理员密码
     *
     * @param id       管理员id
     * @param password 管理员密码
     * @throws ServiceException 业务异常
     */
    Integer updatePassword(Integer id, String password);

    /**
     * 修改管理员信息
     *
     * @param admin 管理员信息
     * @throws ServiceException 业务异常
     */
    Integer updateAdmin(Admin admin);

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @throws ServiceException 业务异常
     */
    Integer deleteAdmin(Integer id);
}
