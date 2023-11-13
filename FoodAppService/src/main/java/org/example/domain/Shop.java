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
 * 店铺信息
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop")
@Schema(name="店铺信息_Shop对象", description="店铺信息")
public class Shop extends Model<Shop> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    @Excel(name = "userId")
    @TableField("user_id")
    private Integer userId;

    /**
    * 店铺名
    */
    @Schema(description = "店铺名")
    @Excel(name = "店铺名")
    @TableField("name")
    private String name;

    /**
    * 省
    */
    @Schema(description = "省")
    @Excel(name = "省")
    @TableField("province")
    private String province;

    /**
    * 县
    */
    @Schema(description = "县")
    @Excel(name = "县")
    @TableField("county")
    private String county;

    /**
    * 具体地址
    */
    @Schema(description = "具体地址")
    @Excel(name = "具体地址")
    @TableField("localtion")
    private String localtion;

    @TableField(exist = false)
    private Map<String,String> params;


//    @Override
//    protected Serializable pkVal(){
//            return this.id;
//        }

}
