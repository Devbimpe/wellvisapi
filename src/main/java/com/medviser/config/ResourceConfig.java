package com.medviser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Longbridge on 14/01/2018.
 */
@Configuration
@EnableWebMvc
public class ResourceConfig extends WebMvcConfigurerAdapter{
    @Value("${wellvisimagesfile}")
    private String evidenceImagePath;


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/wellvisimages/**")
                .addResourceLocations(evidenceImagePath);

    }
}
