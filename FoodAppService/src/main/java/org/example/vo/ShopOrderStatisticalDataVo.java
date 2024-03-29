package org.example.vo;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

/**
 * 
 * @Date 2024/2/22 10:56
 */
@Data
public class ShopOrderStatisticalDataVo {

    private String id;

    private String shopItemName;

    private Integer amount;

    private Double price;
}
