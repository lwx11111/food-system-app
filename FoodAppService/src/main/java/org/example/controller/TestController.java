package org.example.controller;

import org.example.dao.TestMapper;
import org.example.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 刘文轩
 * @Date 2023/10/2 22:08
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @GetMapping("/test")
    public void test(){
//        List<Menu> list = testMapper.test();
        System.out.println(testMapper.selectById("1505812481"));

    }
}
