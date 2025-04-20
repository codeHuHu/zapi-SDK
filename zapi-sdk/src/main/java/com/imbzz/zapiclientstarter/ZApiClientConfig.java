package com.imbzz.zapiclientstarter;

import com.imbzz.zapiclientstarter.client.ZApiClient;
import com.imbzz.zapiclientstarter.service.ApiService;
import com.imbzz.zapiclientstarter.service.impl.ApiServiceImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author imbzz
 * @Date 2023/12/1 22:32
 */
@Data
@Configuration
@ComponentScan
@ConfigurationProperties("zapi.client")
public class ZApiClientConfig {
    private String accessKey;
    private String secretKey;

    /**
     * 网关
     */
    private String host;

    @Bean
    public ZApiClient qiApiClient() {
        return new ZApiClient(accessKey, secretKey);
    }


    @Bean(name = "apiService")
    public ApiService apiService(ZApiClient zApiClient) {
        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.setZApiClient(zApiClient);
        if (StringUtils.isNotBlank(host)) {
            apiService.setGatewayHost(host);
        }
        return apiService;
    }
}
