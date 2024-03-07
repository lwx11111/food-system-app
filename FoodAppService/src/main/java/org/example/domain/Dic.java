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
 * 
 * @since 2024-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dic")
@Schema(name="_Dic对象", description="")
public class Dic extends Model<Dic> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "keyy")
    @TableField("keyy")
    private String keyy;

    @Excel(name = "value")
    @TableField("value")
    private String value;

    @Excel(name = "remark")
    @TableField("remark")
    private String remark;

    @Excel(name = "parentId")
    @TableField("parent_id")
    private String parentId;

    @TableField(exist = false)
    private Map<String,String> params;


}
