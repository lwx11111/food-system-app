package org.example.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @Date 2023/11/10 19:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="菜谱信息展示对象", description="菜谱展示")
public class Steps {

    private String description;
    private String img;
}
