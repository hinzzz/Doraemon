package com.hinz.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/5 15:48
 * @Description : No Description
 */
@Configuration
public class ElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient(){
        /**集群可以配置多个HttpHost*/
        /*RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9201, "http"))
                        new HttpHost("localhost", 9200, "http"),
        );*/
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("120.79.48.191", 9200, "http"))
        );
        return client;
    }
}
