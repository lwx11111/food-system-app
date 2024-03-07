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
 * 用户点赞表
 * </p>
 *
 * 
 * @since 2023-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("menu_like")
@Schema(name="用户点赞表_MenuLike对象", description="用户点赞表")
public class MenuLike extends Model<MenuLike> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "userId")
    @TableField("user_id")
    private String userId;

    @Excel(name = "menuId")
    @TableField("menu_id")
    private String menuId;

    @TableField(exist = false)
    private Map<String,String> params;


}
