package com.echain.task;

import com.echain.manager.RedisKeyMag;
import com.echain.service.WalletService;
import com.okcoin.rest.vo.TickerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时同步美元兑人民币汇率和 行情
 */
@Profile(value = "prod")
@Service
public class SyncWalletInfo {

    @Autowired
    WalletService walletService;

    @Autowired
    RedisKeyMag redisKeyMag;

    @Scheduled(cron = "0/30 * * * * ?")
    public void syncRate() {
        Float rate = walletService.getRate();

        redisKeyMag.setValue("walletRate", rate);
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void syncTicker() throws Exception {
        TickerVo vo = walletService.ticker();
        Float result = Float.parseFloat(vo.getTicker().getLast());

        redisKeyMag.setValue("walletTicker", result);
    }
}
