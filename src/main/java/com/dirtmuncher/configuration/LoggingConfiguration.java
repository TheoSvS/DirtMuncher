package com.dirtmuncher.configuration;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class LoggingConfiguration {

    @Bean
    public CustomLoggingFilter logFilter() {
        CustomLoggingFilter filter = new CustomLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setBeforeMessagePrefix("Incoming Request: ");
        filter.setAfterMessageSuffix(" [END]");
        return filter;
    }

}