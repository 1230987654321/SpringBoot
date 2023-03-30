package com.example.admin.common;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 文件类型枚举
 * @create 2023/3/30 17:10
 **/
public enum FileTypeEnum {
    JPEG("JPEG", "ffd8ffe000104a464946"),
    PNG("PNG", "89504e470d0a1a0a0000"),
    GIF("GIF", "47494638396126026f01"),
    TIF("TIF", "49492a00227105008037"),
    ICO("ICO", "00000100000100002000"),
    DOC("DOC", "d0cf11e0a1b11ae10000"),
    DOCX("DOCX", "504b0304140006000800"),
    PDF("PDF", "255044462d312e350d0a25"),
    RMVB("RMVB", "2e524d46000000120001"),
    FLV("FLV", "464c5601050000000900"),
    MP4("MP4", "00000020667479706d70"),
    MP3("MP3", "49443303000000002176"),
    WMV("WMV", "3026b2758e66cf11a6d9"),
    WAV("WAV", "52494646e27807005741"),
    AVI("AVI", "52494646d07d60074156"),
    ZIP("ZIP", "504b0304140000000800"),
    RAR("RAR", "526172211a0700cf9073");

    private String type;

    private String value;

    /**
     * 构造方法
     *
     * @param value 枚举值
     */
    FileTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getValue() {
        return hexStringToByteArray(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

}
