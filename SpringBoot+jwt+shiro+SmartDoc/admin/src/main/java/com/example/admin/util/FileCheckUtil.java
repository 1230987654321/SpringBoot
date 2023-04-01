package com.example.admin.util;

import com.example.admin.common.FileTypeEnum;
import com.example.admin.common.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 文件验证工具类
 * @create 2023/3/30 13:53
 **/
public class FileCheckUtil {

    private static FileTypeEnum FileContentType;

    private static FileTypeEnum FileBytes;

    /**
     * 判断文件是否合法
     *
     * @param file 文件
     * @throws IOException IOException
     */
    public static void isFileMalicious(MultipartFile file, Long maxSize) throws IOException {
        // 检查文件类型
        if (file.getContentType() == null) {
            throw new ServiceException(400, "上传文件类型不能为空");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new ServiceException(400, "暂不支持的文件类型");
        }
        // 检查文件大小
        if (file.getSize() > maxSize) {
            throw new ServiceException(400, "文件大小不能超过" + maxSize);
        }
        if (!isAllowedContentType(file.getContentType()) || !isAllowedFileHeader(file.getBytes())) {
            throw new ServiceException(400, "暂不支持的文件类型!");
        }
        if (FileContentType != FileBytes) {
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
                FileContentType = type;
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
            if (startsWith(fileData, hexStringToByteArray(type.getValue()))) {
                FileBytes = type;
                return true;
            }
        }
        return false;
    }

    /**
     * 将16进制字符串转换为字节数组
     *
     * @param hexString 16进制字符串
     * @return byte[]
     */
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
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
