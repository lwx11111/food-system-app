package org.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.domain.Menu;
import org.example.domain.ShopOrder;

import java.util.List;

/**
 * 
 * @Date 2024/2/12 22:27
 */
@Mapper
public interface TestMapper extends BaseMapper<Menu> {

    @Select("select * from menu")
    List<Menu> test();

    @Select("select * from menu")
    List<Menu> test1();
}
