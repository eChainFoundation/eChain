package com.echain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @version V1.0
 * @Title: CorsFilterConfig
 * @Description: 解决跨域问题
 * @author: lijun
 * @date: 2017/8/28 15:47
 */
@Configuration
public class CorsFilterConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 原始请求的域名
        corsConfiguration.addAllowedOrigin("*");
        // 服务器支持的所有跨域请求的方法
        corsConfiguration.addAllowedMethod("*");
        // 添加请求头字段
        corsConfiguration.addAllowedHeader("*");
       /* // 允许请求发送Cookie
        corsConfiguration.setAllowCredentials(true);*/
        // 预检请求的有效期，单位为秒。
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }
}
