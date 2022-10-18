package com.example.config;//package springcloud.config;
//
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
//import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.mapping.DatabaseIdProvider;
//import org.apache.ibatis.plugin.Interceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.DefaultResourceLoader;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
//@Configuration
//public class MybatisPlusConfig {
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
////    private MybatisProperties properties;//com.test.mybatis
//    private MybatisPlusProperties properties;//MybatisPlus
//
//    @Autowired
//    private ResourceLoader resourceLoader = new DefaultResourceLoader();
//
//    @Autowired(required = false)
//    private Interceptor[] interceptors;
//
//    @Autowired(required = false)
//    private DatabaseIdProvider databaseIdProvider;
//
//    //此处好像也必须，否则mp的3.x版本会报错，需要指定DialectType这个玩意
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor page = new PaginationInterceptor();
//        page.setDialectType("mysql");
//        return page;
//    }
//    //尤其是这个方法，必须重写，使用MybatisSqlSessionFactoryBean这个Bean
//    @Bean
//    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() throws IOException {
//        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
//        mybatisPlus.setDataSource(dataSource);
//        mybatisPlus.setVfs(SpringBootVFS.class);
//        if (StringUtils.hasText(this.properties.getConfigLocation())) {
//            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
//        }
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            mybatisPlus.setPlugins(this.interceptors);
//        }
//        MybatisConfiguration mc = new MybatisConfiguration();
//        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//        //数据库字段设计为驼峰命名，默认开启的驼峰转下划线会报错字段找不到
//        mc.setMapUnderscoreToCamelCase(this.properties.getConfiguration().isMapUnderscoreToCamelCase());
//        mybatisPlus.setConfiguration(mc);
//        if (this.databaseIdProvider != null) {
//            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
//        }
//        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
//            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
//        }
//        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
//            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
//        }
//        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
//            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
//        }
//        return mybatisPlus;
//
//    }
//}
//
