package org.example.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.example.vo.MenuVO;

import java.util.Arrays;
import java.util.Map;
/**
 * <p>
 * 菜谱信息表
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "menu", autoResultMap = true)
@Schema(name="菜谱信息表_Menu对象", description="菜谱信息表")
public class Menu extends Model<Menu> implements Serializable {

    private static final long serialVersionUID=1L;

    public Menu(MenuVO menuVO) {
        this.id = menuVO.getId();
        this.name = menuVO.getName();
        this.description = menuVO.getDescription();
        JSONArray jsonArray = new JSONArray(menuVO.getIngredients());
        System.out.println(jsonArray);
        this.Ingredients = jsonArray.toString();
        JSONArray jsonArray1 = new JSONArray(menuVO.getSteps());
        System.out.println(jsonArray);
        this.steps = jsonArray1.toString();
        this.type = menuVO.getType();
        this.likes = 0;
    }

    public Menu(){

    }

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 菜谱名
    */
    @Schema(description = "菜谱名")
    @Excel(name = "菜谱名")
    @TableField("name")
    private String name;

    /**
    * 描述
    */
    @Schema(description = "描述")
    @Excel(name = "描述")
    @TableField("description")
    private String description;

    /**
    * 原料JSON
    */
    @Schema(description = "原料JSON")
    @Excel(name = "原料JSON")
    @TableField("Ingredients")
    private String Ingredients;

    /**
    * 步骤JSON
    */
    @Schema(description = "步骤JSON")
    @Excel(name = "步骤JSON")
    @TableField("steps")
    private String steps;

    /**
    * 点赞数
    */
    @Schema(description = "点赞数")
    @Excel(name = "点赞数")
    @TableField("likes")
    private Integer likes;

    @Schema(description = "菜谱图片")
    @Excel(name = "菜谱图片")
    @TableField("image")
    private String image;

    @Schema(description = "类型")
    @Excel(name = "类型")
    @TableField("type")
    private String type;

    @TableField(exist = false)
    private Map<String,String> params;

    @Schema(description = "部件ID")
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray test;

}
