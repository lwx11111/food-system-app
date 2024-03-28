package org.example.service.auth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dao.auth.SysVerificationCodeCacheMapper;
import org.example.domain.auth.SysVerificationCodeCache;
import org.example.service.auth.SysVerificationCodeCacheService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class SysVerificationCodeCacheServiceImpl extends ServiceImpl<SysVerificationCodeCacheMapper, SysVerificationCodeCache> implements SysVerificationCodeCacheService {

    @Override
    public SysVerificationCodeCache getByUUID(String uuid) {
        QueryWrapper<SysVerificationCodeCache> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysVerificationCodeCache::getId, uuid);
        return getOne(queryWrapper, false);
    }
}
