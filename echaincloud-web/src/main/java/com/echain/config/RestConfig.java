package com.echain.config;

import com.echain.common.util.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * rest接口调用配置类
 */
@Configuration
public class RestConfig {

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "httpsRestTemplate")
    public RestTemplate httpsRestTemplate() {
        CloseableHttpClient httpClient;
        RestTemplate restTemplate = null;
        try {
            Integer overtime = 30000;
            httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);
            if (overtime != null) {
                clientHttpRequestFactory.setConnectionRequestTimeout(overtime);
                clientHttpRequestFactory.setConnectTimeout(overtime);
                clientHttpRequestFactory.setReadTimeout(overtime);
            }
            restTemplate = new RestTemplate(clientHttpRequestFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restTemplate;
    }
}
