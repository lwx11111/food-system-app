package org.example.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.domain.auth.SysVerificationCodeCache;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface SysVerificationCodeCacheService extends IService<SysVerificationCodeCache> {

    SysVerificationCodeCache getByUUID(String uuid);
}
