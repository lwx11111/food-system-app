package org.example.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.example.domain.ShopItem;

/**
 * <p>
 *
 * </p>
 *
 * 
 * @since 2024-01-09
 */
@Data
public class ShopOrderVO extends Model<ShopOrderVO> {

    private String id;

    private String userId;

    private ShopItem shopItem;

    private Integer amount;

    private Double price;

}
