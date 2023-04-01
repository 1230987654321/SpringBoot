package com.example.admin.entity.vo;

import com.example.admin.entity.Banner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author 贲玉柱
 * @program workspace
 * @description
 * @create 2023/3/27 16:38
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class BannerVo extends Banner {

    /**
     * 封面图片路径
     */
    private String coverPath;

    public BannerVo(Banner banner) {
        super(banner);
    }
}
