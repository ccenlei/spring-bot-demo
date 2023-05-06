package com.spring.bot.demo.configuration;

import org.jodconverter.DocumentConverter;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnlineViewConfig {

    @Bean(destroyMethod = "stop")
    OfficeManager officeManager(
            @Value("${jodconverter.office.home}") String officeHome,
            @Value("${jodconverter.office.port-numbers}") Integer portNumbers) throws OfficeException {
        LocalOfficeManager officeManager = LocalOfficeManager.builder()
                .officeHome(officeHome)
                .portNumbers(portNumbers)
                .build();
        officeManager.start();
        return officeManager;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({ OfficeManager.class })
    public DocumentConverter jodConverter(OfficeManager officeManager) {
        return LocalConverter.make(officeManager);
    }
}
