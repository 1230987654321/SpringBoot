package com.example.admin.util;

import com.example.admin.common.FileTypeEnum;
import com.example.admin.common.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 文件验证工具类
 * @create 2023/3/30 13:53
 **/
@Slf4j
public class FileCheckUtil {

    /**
     * 判断文件是否合法
     *
     * @param file 文件
     * @throws IOException IOException
     */
    public static void isFileMalicious(MultipartFile file, Long maxSize) throws IOException {
        // 检查文件类型
        if (file.getContentType() == null) {
            log.error("上传文件类型不能为空");
            throw new ServiceException(400, "上传文件类型不能为空");
        }
        if (!file.getContentType().startsWith("image/")) {
            log.error("暂不支持的文件类型======>{}", file.getContentType());
            throw new ServiceException(400, "暂不支持的文件类型");
        }
        String[] arr = file.getContentType().split("/");
        if (arr.length < 2) {
            log.error("文件类型非法,请检查文件类型======>{}", file.getContentType());
            throw new ServiceException(400, "文件类型非法,请检查文件类型");
        }
        String fileType = arr[1];
        // 检查文件大小
        if (file.getSize() > maxSize) {
            log.error("文件大小不能超过{}======>{}", maxSize, file.getSize());
            throw new ServiceException(400, "文件大小不能超过" + maxSize);
        }
        if (!isAllowedContentType(fileType) || !isAllowedFileHeader(file.getBytes())) {
            log.error("上传文件有异常,已被系统禁止!");
            throw new ServiceException(400, "上传文件有异常,已被系统禁止!");
        }
    }

    /**
     * 判断文件类型是否合法
     *
     * @param contentType 文件类型
     * @return boolean
     */
    private static boolean isAllowedContentType(String contentType) {
        // 获取所有文件类型
        FileTypeEnum[] fileTypes = FileTypeEnum.values();
        // 遍历文件类型
        for (FileTypeEnum type : fileTypes) {
            // 判断文件类型是否合法
            if (type.getType().equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断文件头是否合法
     *
     * @param fileData 文件字节数组
     * @return boolean
     */
    private static boolean isAllowedFileHeader(byte[] fileData) {
        // 获取所有文件类型
        FileTypeEnum[] fileTypes = FileTypeEnum.values();
        for (FileTypeEnum type : fileTypes) {
            // 判断文件头是否合法
            if (startsWith(fileData, type.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字节数组是否以指定的字节数组开头
     *
     * @param data   字节数组
     * @param prefix 指定的字节数组
     * @return boolean
     */
    private static boolean startsWith(byte[] data, byte[] prefix) {
        if (data == null || prefix == null || data.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (data[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}
