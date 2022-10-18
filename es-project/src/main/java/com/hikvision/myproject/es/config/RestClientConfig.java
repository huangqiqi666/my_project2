package com.hikvision.myproject.es.config;

import lombok.Data;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * ElasticSearch 客户端配置
 *
 * @author geng
 * 2020/12/19
 */
//@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
@EnableElasticsearchRepositories
@Data
public class RestClientConfig extends AbstractElasticsearchConfiguration {

//    private String url;
    @Value("${elasticsearch.url}")
    private String url;


    //方式1
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }


    //方式2：localhost:9200 写在配置文件中就可以了
//    @Bean
//    RestHighLevelClient client() {
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(esUrl)//elasticsearch地址
//                .build();
//        return RestClients.create(clientConfiguration).rest();
//    }

}
