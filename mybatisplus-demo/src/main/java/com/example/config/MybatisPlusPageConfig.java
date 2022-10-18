package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname MybatisPlusPageConfig
 * @Description TODO
 * @Date 2021/9/13 16:33
 * @Created by huangqiqi
 */
@Configuration
@MapperScan("springcloud.mapper")
public class MybatisPlusPageConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}

