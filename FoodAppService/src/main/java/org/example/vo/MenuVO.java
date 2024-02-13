package org.example.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.example.domain.Menu;

import java.util.List;
import java.util.Map;

/**
 * @Author 刘文轩
 * @Date 2023/11/10 19:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="菜谱信息展示对象", description="菜谱展示")
public class MenuVO {

    public MenuVO(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.description = menu.getDescription();
        this.likes = menu.getLikes();
        if (menu.getIngredients() != null) {
            String jsonArray = menu.getIngredients();
            try {
                List<Ingredients> ingredients = JSONObject.parseArray(jsonArray.toString(), Ingredients.class);
//                this.ingredients = ingredients.toArray(new Ingredients[ingredients.size()]);
                this.ingredients = ingredients;
            } catch (Exception e) {
                e.printStackTrace();
                this.ingredients = null;
            }
        }

        if (menu.getSteps() != null) {
            String jsonArray = menu.getSteps();
            try {
                List<Steps> steps = JSONObject.parseArray(jsonArray.toString(), Steps.class);
//                this.steps = steps.toArray(new Steps[steps.size()]);
                this.steps = steps;
            } catch (Exception e) {
                e.printStackTrace();
                this.steps = null;
            }
        }

//        this.userId = menu.getUserId();
        this.image = menu.getImage();
        this.type = menu.getType();
    }

    public MenuVO(){

    }

    private String id;

    /**
     * 菜谱名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 原料JSON
     */
//    private Ingredients[] ingredients;
    private List<Ingredients> ingredients;
    /**
     * 步骤JSON
     */
    private List<Steps> steps;

    private String userId;

    private String image;

    private String type;

    private String likes;

}
