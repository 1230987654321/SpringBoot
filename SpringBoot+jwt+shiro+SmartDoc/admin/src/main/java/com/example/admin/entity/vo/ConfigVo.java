package com.example.admin.entity.vo;

import com.example.admin.entity.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author 贲玉柱
 * @program workspace
 * @description
 * @create 2023/3/28 15:30
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class ConfigVo extends Config {

    /**
     * logo图片路径
     */
    private String logoPath;

    /**
     * 分享图片路径
     */
    private String shareImgPath;

    public ConfigVo(Config config) {
        super(config);
    }
}
