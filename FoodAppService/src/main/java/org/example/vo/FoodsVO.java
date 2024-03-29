package org.example.vo;

import lombok.Data;
import org.example.domain.Dic;

import java.util.List;

/**
 * 
 * @Date 2024/1/22 23:07
 */
@Data
public class FoodsVO {

    List<Dic> categoryNames;

    List<List<Dic>> foods;
}
