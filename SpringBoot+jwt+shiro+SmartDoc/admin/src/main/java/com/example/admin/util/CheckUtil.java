package com.example.admin.util;

import com.example.admin.common.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 校验工具类
 * @create 2023/3/29 13:55
 **/
public class CheckUtil {
    /**
     * 判断字符串是否为空或null
     *
     * @param str 字符串
     * @param msg 提示信息
     * @throws ServiceException 业务异常
     */
    public static void checkStringNotEmpty(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new ServiceException(400, msg);
        }
    }

    /**
     * 判断整数参数是否为null
     *
     * @param integer 整数
     * @param msg     提示信息
     * @throws ServiceException 业务异常
     */
    public static void checkIntegerNotNull(Integer integer, String msg) {
        if (integer == null) {
            throw new ServiceException(400, msg);
        }
    }

    /**
     * 判断整数参数是否为0或者null
     *
     * @param integer 整数
     * @param msg     提示信息
     * @throws ServiceException 业务异常
     */
    public static void checkIntegerNotZero(Integer integer, String msg) {
        if (integer == null || integer == 0) {
            throw new ServiceException(400, msg);
        }
    }

    /**
     * 判断对象是否为null
     *
     * @param object 对象
     * @param code   错误码
     * @param msg    提示信息
     * @throws ServiceException 业务异常
     */
    public static void checkObjectNotNull(Object object, Integer code, String msg) {
        if (object == null) {
            throw new ServiceException(code, msg);
        }
    }

    /**
     * 判断List集合是否为空
     *
     * @param list List集合
     * @param code 错误码
     * @param msg  提示信息
     * @throws ServiceException 业务异常
     */
    public static <T> void checkListNotNull(List<T> list, Integer code, String msg) {
        if (list == null || list.size() == 0) {
            throw new ServiceException(code, msg);
        }
    }

    /**
     * 判断当前页和每页大小是否为正整数
     *
     * @param current 当前页
     * @param size    每页大小
     * @throws ServiceException 业务异常
     */
    public static void checkPage(Integer current, Integer size) {
        if (current <= 0 || size <= 0) {
            throw new ServiceException(400, "当前页和每页大小必须为正整数");
        }
        // 假设最大每页大小为 500
        int maxPageSize = 500;
        if (size > maxPageSize) {
            throw new ServiceException(400, "每页大小不能超过" + maxPageSize);
        }
    }
}
