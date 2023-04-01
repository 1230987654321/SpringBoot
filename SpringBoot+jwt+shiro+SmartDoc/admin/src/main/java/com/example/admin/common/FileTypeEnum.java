package com.example.admin.common;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 文件类型枚举
 * @create 2023/3/30 17:10
 **/
public enum FileTypeEnum {

    JPEG("IMAGE/JPEG", "ffd8ffe000104a46"),
    PNG("IMAGE/PNG", "89504e470d0a1a0a"),
    GIF("IMAGE/GIF", "4749463839612602"),
    TIF("IMAGE/TIF", "49492a0022710500"),
    ICO("IMAGE/ICO", "0000010000010000"),
    BMP("IMAGE/BMP", "424d228c01000000"),
    BMP2("IMAGE/BMP", "424d824009000000"),
    BMP3("IMAGE/BMP", "424d8e1b03000000"),
    RMVB("VIDEO/RMVB", "2e524d4600000012"),
    FLV("VIDEO/FLV", "464c560105000000"),
    MP4("VIDEO/MP4", "0000002066747970"),
    WMV("VIDEO/WMV", "3026b2758e66cf11"),
    WAV("VIDEO/WAV", "52494646e2780700"),
    AVI("VIDEO/AVI", "52494646d07d6007"),
    MP3("AUDIO/MP3", "4944330300000000"),
    MP31("AUDIO/MPEG", "fffb900000000000"),
    DOC("APPLICATION/MSWORD", "d0cf11e0a1b11ae1"),
    DOCX("APPLICATION/VND.OPENXMLFORMATS-OFFICEDOCUMENT.WORDPROCESSINGML.DOCUMENT", "504b030414000600"),
    XLS("APPLICATION/VND.MSEXCEL", "d0cf11e0a1b11ae1"),
    XLSX("APPLICATION/VND.OPENXMLFORMATS-OFFICEDOCUMENT.SPREADSHEETML.SHEET", "504b030414000600"),
    PDF("APPLICATION/PDF", "255044462d312e350d"),
    ZIP("APPLICATION/ZIP", "504b030414000000"),
    RAR("APPLICATION/VND.RAR", "526172211a0700cf");

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
