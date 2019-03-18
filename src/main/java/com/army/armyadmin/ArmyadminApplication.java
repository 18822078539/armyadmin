package com.army.armyadmin;

import com.army.armyadmin.common.config.FebsProperies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.army.armyadmin.*.dao")
@EnableConfigurationProperties({FebsProperies.class})
@EnableCaching
@EnableAsync
public class ArmyadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArmyadminApplication.class, args);
    }

}
