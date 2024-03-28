package org.example.service.auth.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.auth.SysAccountMapper;
import org.example.domain.auth.SysAccount;
import org.example.service.auth.SysAccountService;
import org.example.utils.AesUtil;
import org.example.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {


    @Override
    @Transactional
    public SysAccount login(String loginName, String loginPass) {
        QueryWrapper<SysAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysAccount::getLoginName, loginName);
        List<SysAccount> accountList = list(queryWrapper);
        if (accountList.size() > 0) {
            SysAccount account = accountList.get(0);
            // 数据库加密密码
            String pass = account.getLoginPass();
            try {
                //对传入的密码进行aes解密，再用MD5加密，然后与库中进行比对
                loginPass = AesUtil.decrypt(loginPass, Constants.AES_KEY);
                byte[] md5Str = DigestUtils.getMd5Digest().digest(loginPass.getBytes(StandardCharsets.UTF_8));
                loginPass =  new BigInteger(1, md5Str).toString(16);

            } catch (Exception e) {
                log.error("aes加密出错", e);
            }

            if (StringUtils.equals(loginPass, pass)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void modifyPass(String accountId, String oldPass, String newPass) {
        //对传入的密码进行aes解密，再用MD5加密，然后与库中进行比对
        try{
            SysAccount account = getById(accountId);
            String pass = account.getLoginPass();
            oldPass = AesUtil.decrypt(oldPass, Constants.AES_KEY);
            byte[] md5Str = DigestUtils.getMd5Digest().digest(oldPass.getBytes(StandardCharsets.UTF_8));
            oldPass =  new BigInteger(1, md5Str).toString(16);
            // 比较
            if (StringUtils.equals(oldPass, pass)) {
                newPass = AesUtil.decrypt(newPass, Constants.AES_KEY);
                byte[] md5Str1 = DigestUtils.getMd5Digest().digest(newPass.getBytes(StandardCharsets.UTF_8));
                newPass =  new BigInteger(1, md5Str1).toString(16);
                account.setLoginPass(newPass);
                updateById(account);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
