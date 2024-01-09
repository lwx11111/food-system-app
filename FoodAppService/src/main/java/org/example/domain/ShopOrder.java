package org.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Map;
/**
 * <p>
 * 
 * </p>
 *
 * @author lwx20
 * @since 2024-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_order")
@Schema(name="_ShopOrder对象", description="")
public class ShopOrder extends Model<ShopOrder> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "userId")
    @TableField("user_id")
    private String userId;

    @Excel(name = "shopItemId")
    @TableField("shop_item_id")
    private String shopItemId;

    /**
    * 数量
    */
    @Schema(description = "数量")
    @Excel(name = "数量")
    @TableField("amount")
    private Integer amount;

    @TableField(exist = false)
    private Map<String,String> params;


}
