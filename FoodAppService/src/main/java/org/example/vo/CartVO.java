package org.example.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.example.domain.ShopItem;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * 
 * @since 2024-01-09
 */
@Data
public class CartVO extends Model<CartVO> {

    private String id;

    private String userId;

    private ShopItem shopItem;

    private Integer amount;

}
