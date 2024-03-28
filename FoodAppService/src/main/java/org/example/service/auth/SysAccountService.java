package org.example.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.auth.SysAccount;



public interface SysAccountService extends IService<SysAccount> {

    SysAccount login(String loginName, String loginPass);

    void modifyPass(String accountId,String oldPass, String newPass);

}

