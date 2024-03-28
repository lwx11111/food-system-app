package org.example.controller.auth;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.domain.auth.SysAccount;
import org.example.domain.auth.SysVerificationCodeCache;
import org.example.service.auth.SysAccountService;
import org.example.service.auth.SysVerificationCodeCacheService;
import org.example.utils.AesUtil;
import org.example.utils.Constants;
import org.example.utils.RandImageUtils;
import org.example.web.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/account")
@Slf4j
@CrossOrigin(origins = "*",maxAge = 3600)
public class AccountController {

    @Autowired
    private SysAccountService accountService;

    @Autowired
    private SysVerificationCodeCacheService verificationCodeCacheService;



//    @PostMapping("/updateInfo")
//    public SimpleResponse updateInfo(@RequestBody SysAccount account) {
//        LambdaUpdateWrapper<SysAccount> updateWrapper = new LambdaUpdateWrapper<SysAccount>()
//                .eq(SysAccount::getAccountId, account.getAccountId())
//                .set(SysAccount::getPhone, account.getPhone())
//                .set(SysAccount::getAvatar, account.getAvatar());
//        accountService.update(updateWrapper);
//        return createResponse().build();
//    }
//
//
//    @Operation(description = "账号登出")
//    @PostMapping("/logout")
//    public SimpleResponse logout(HttpServletRequest request) {
//        String token = request.getHeader(Constants.TOKEN_NAME);
//        SysTokenBlackList tokenBlackList = new SysTokenBlackList();
//        tokenBlackList.setCreateTime(LocalDateTime.now());
//        tokenBlackList.setToken(token);
//        tokenBlackListService.save(tokenBlackList);
//        return createResponse().build();
//    }

    @PostMapping("/save")
    public SimpleResponse saveByUser(@RequestParam("username") String username,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("avatar") String avatar,
                                     @RequestParam("password") String password,
                                     @RequestParam("verify") String verify,
                                     @RequestParam("uuid") String uuid) {
        SimpleResponse response = new SimpleResponse();
        try {
            // s1 系统生产的验证码
            String s1 = "";
            SysVerificationCodeCache verificationCodeCache = verificationCodeCacheService.getByUUID(uuid);
            if (verificationCodeCache != null) {
                s1 = verificationCodeCache.getCode().toLowerCase();
            }
            String s2 = verify.toLowerCase();
            if (verificationCodeCache == null || !StringUtils.equals(s1, s2)) {
                response.setCode(500);
                response.setMessage("验证码不正确！");
                return response;
            }

            // 用户信息
            SysAccount account = new SysAccount();
            account.setAccountName(username);
            account.setLoginName(username);
            account.setLoginPass(password);
            account.setStatus("0");
            account.setLoginFailTimes(0);
            account.setInitialPasswordFlag("0");
            account.setPhone(phone);
            account.setAvatar(avatar);
            accountService.save(account);

        }catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Operation(description = "验证码账号登陆")
    @PostMapping("/login")
    public SimpleResponse loginWithCode(HttpServletRequest request,
                                        @RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        @RequestParam("verify") String verify,
                                        @RequestParam("uuid") String uuid) {
        SimpleResponse response = new SimpleResponse();
        if (StringUtils.isBlank(verify)) {
            response.setCode(500);
            response.setMessage("验证码不能为空！");
            return response;
        }
        // s1 系统生产的验证码
        String s1 = "";
        SysVerificationCodeCache verificationCodeCache = verificationCodeCacheService.getByUUID(uuid);
        if (verificationCodeCache != null) {
            s1 = verificationCodeCache.getCode().toLowerCase();
        }
        String s2 = verify.toLowerCase();
        if (verificationCodeCache == null || !StringUtils.equals(s1, s2)) {
            response.setCode(500);
            response.setMessage("验证码不正确！");
            return response;
        }
        return login(username,password);
    }

    @Operation(description = "账号登陆")
    @PostMapping("/anon/login")
    public SimpleResponse login(@RequestParam("username") String username,
                                @RequestParam("password") String password){
        //对前台加密的账号进行解密
        try {
            username = AesUtil.decrypt(username, Constants.AES_KEY);
            log.info("========================aes解密后的username:"+username);
        } catch (Exception e) {
            log.error("aes解密出错", e);
        }
        SimpleResponse response = new SimpleResponse();
        // 获取账号信息
        SysAccount account = accountService.login(username, password);
        if(null == account){
            response.setMessage("账号或密码错误");
            response.setCode(500);
            return response;
        }

        response.setData(account);
        return response;
    }

    /**
     * 生成验证码
     * @param response
     * @param uuid
     * @param width
     * @param height
     * @throws IOException
     */
    @RequestMapping(value = "/anon/verification-code/create", method = {RequestMethod.POST, RequestMethod.GET})
    public void createCodeImage(HttpServletResponse response,
                                @RequestParam("uuid") String uuid,
                                @RequestParam(value = "width", required = false, defaultValue = "105") int width,
                                @RequestParam(value = "height", required = false, defaultValue = "35") int height) throws IOException {

        String code = RandomStringUtils.random(4, "2345678abcdefhijkmnpqrstuvwxyzABCDEFGHJKLMNPQRTUVWXY");
        SysVerificationCodeCache verificationCodeCache = new SysVerificationCodeCache();
        verificationCodeCache.setCode(code.toLowerCase());
        verificationCodeCache.setId(uuid);
        verificationCodeCache.setCreateTime(LocalDateTime.now());
        verificationCodeCacheService.save(verificationCodeCache);
        RandImageUtils.generate(response, code, width, height);
    }

    @PostMapping("/modify-password")
    public SimpleResponse modifyPassword(String accountId, String oldPass, String newPass, HttpServletRequest request) {
        accountService.modifyPass(accountId, oldPass, newPass);
        return new SimpleResponse();
    }


}
