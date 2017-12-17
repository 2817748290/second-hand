package com.liangliang.bookmanager.config;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: YannYao
 * @Description:
 * @Date Created in 21:04 2017/12/14
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${com.liangliang.custom.images-path}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations(path);
        super.addResourceHandlers(registry);
    }
}