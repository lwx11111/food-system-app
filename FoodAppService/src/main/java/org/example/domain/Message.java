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
 * 
 * </p>
 *
 * @author lwx20
 * @since 2024-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("message")
@Schema(name="_Message对象", description="")
public class Message extends Model<Message> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
        @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
    * 发送方id
    */
    @Schema(description = "发送方id")
    @Excel(name = "发送方id")
    @TableField("send_user_id")
    private String sendUserId;

    /**
    * 接收方id
    */
    @Schema(description = "接收方id")
    @Excel(name = "接收方id")
    @TableField("receive_user_id")
    private String receiveUserId;

    /**
    * 内容
    */
    @Schema(description = "内容")
    @Excel(name = "内容")
    @TableField("content")
    private String content;

    /**
    * 发送时间
    */
    @Schema(description = "发送时间")
    @Excel(name = "发送时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("send_time")
        @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;

    /**
    * 回复id
    */
    @Schema(description = "回复id")
    @Excel(name = "回复id")
    @TableField("parent_id")
    private String parentId;

    @TableField(exist = false)
    private Map<String,String> params;


}
