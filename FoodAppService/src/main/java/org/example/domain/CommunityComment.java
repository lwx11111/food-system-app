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
 * 社区帖子评论表
 * </p>
 *
 * 
 * @since 2023-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("community_comment")
@Schema(name="社区帖子评论表_CommunityComment对象", description="社区帖子评论表")
public class CommunityComment extends Model<CommunityComment> {

    private static final long serialVersionUID=1L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Excel(name = "communityId")
    @TableField("community_id")
    private String communityId;

    @Excel(name = "userId")
    @TableField("user_id")
    private String userId;

    /**
    * 内容
    */
    @Schema(description = "内容")
    @Excel(name = "内容")
    @TableField("content")
    private String content;

    /**
    * 发布时间
    */
    @Schema(description = "发布时间")
    @Excel(name = "发布时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("release_time")
        @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    @TableField(exist = false)
    private Map<String,String> params;



}
