package org.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * 点单表
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order")
@Schema(name="点单表_Order对象", description="点单表")
public class Order extends Model<Order> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @Excel(name = "shopId")
    @TableField("shop_id")
    private Integer shopId;

    @Excel(name = "userId")
    @TableField("user_id")
    private Integer userId;

    /**
    * 物品JSON
    */
    @Schema(description = "物品JSON")
    @Excel(name = "物品JSON")
    @TableField("shop_item")
    private String shopItem;

    /**
    * 创建时间
    */
    @Schema(description = "创建时间")
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("create_time")
        @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
    * 状态
    */
    @Schema(description = "状态")
    @Excel(name = "状态")
    @TableField("status")
    private String status;

    /**
    * 订单金额
    */
    @Schema(description = "订单金额")
    @Excel(name = "订单金额")
    @TableField("price")
    private Double price;

    @TableField(exist = false)
    private Map<String,String> params;


//    @Override
//    protected Serializable pkVal(){
//            return this.id;
//        }

}
