package org.example.controller;

import org.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 刘文轩
 * @Date 2023/10/2 22:08
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private IMenuService menuService;

    @PostMapping("/test")
    public void test(@RequestBody String jsonString){
        TestController testController = new TestController();
        try {
            testController.fun();
            System.out.println("234");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("456");
        }
    }


    public void fun() throws Exception {

        System.out.println(123);
        throw new Exception("111");

    }
}
