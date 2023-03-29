package com.example.admin.util;

import com.example.admin.common.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 校验工具类
 * @create 2023/3/29 13:55
 **/
@Slf4j
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
            // 记录日志
            log.error(msg + " =======>" + str);
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
            // 记录日志
            log.error(msg + " =======>" + integer);
            throw new ServiceException(400, msg);
        }
    }

    /**
     * 判断对象是否为null
     *
     * @param object 对象
     * @param msg    提示信息
     * @throws ServiceException 业务异常
     */
    public static void checkObjectNotNull(Object object, String msg) {
        if (object == null) {
            // 记录日志
            log.error(msg + " =======>" + object);
            throw new ServiceException(400, msg);
        }
    }
}
