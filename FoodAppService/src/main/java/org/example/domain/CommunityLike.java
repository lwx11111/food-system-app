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
 * 社区帖子点赞表
 * </p>
 *
 * 
 * @since 2023-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("community_like")
@Schema(name="社区帖子点赞表_CommunityLike对象", description="社区帖子点赞表")
public class CommunityLike extends Model<CommunityLike> {

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

    @TableField(exist = false)
    private Map<String,String> params;

//
//    @Override
//    protected Serializable pkVal(){
//            return this.id;
//        }

}
