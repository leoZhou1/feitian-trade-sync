package com.feitian.trade.sync;

import com.feitian.trade.sync.config.TaobaoConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({TaobaoConfig.class})
@MapperScan("com.feitian.trade.sync.dao")
@EnableScheduling
public class FeitianTradeSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeitianTradeSyncApplication.class, args);
    }
}
