package org.example.controller;

import org.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 刘文轩
 * @Date 2023/10/2 22:08
 */
@RestController
public class TestController {
    @Autowired
    private IMenuService menuService;

    @GetMapping("/test")
    public void test(){
        System.out.println("test");
        System.out.println(menuService.list().get(0));
    }
}
