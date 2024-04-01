package org.example.job;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.dao.MenuMapper;
import org.example.domain.Dic;
import org.example.domain.Menu;
import org.example.service.impl.DicServiceImpl;
import org.example.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date 2024/4/1 13:47
 */
@Component
public class ScheduledJob {

    @Autowired
    private DicServiceImpl dicService;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 每天执行一次
     */
//    @Scheduled(cron="0 0/1 * * * ? ")
    @Scheduled(cron = "0 0 12 * * ?")
    public void dailyRecommended(){
        Dic dic = dicService.getDicByKeyy("每日推荐id");
        List<String> randomIds = menuMapper.getRandomIds();
        String value = "(";
        for (int i = 0; i < randomIds.size(); i++) {
            if (i == randomIds.size() - 1) {
                value += "'" + randomIds.get(i) + "')";
            } else {
                value += "'" + randomIds.get(i) + "',";
            }
        }
        dic.setValue(value);
        dicService.updateById(dic);
    }


}
