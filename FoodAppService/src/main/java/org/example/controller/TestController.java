package org.example.controller;

import org.example.dao.DicMapper;
import org.example.domain.Dic;
import org.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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

    @Autowired
    private DicMapper dicMapper;

    @PostMapping("/test")
    @Transactional
    public void test(){
        try {
            Dic dic = new Dic();
            dic.setKeyy("123");
            dicMapper.insert(dic);
            this.fun();
            int i = 1/0;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
    }


    public void fun() {

        Dic dic = new Dic();
        dic.setKeyy("456");
        dicMapper.insert(dic);
        int i = 1/0;

    }
}
