package com.hinz.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.hinz.elasticsearch.config.ElasticSearchConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.assertj.core.api.Assert;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ElasticsearchApplicationTests {

	@Resource
	private RestHighLevelClient client;


	@Test
	void contextLoads() throws Exception{
		IndexRequest indexRequest = new IndexRequest("user");
		User user = new User("hinzzz",18,"亚历山大大西北出来");
		String jsonString = JSON.toJSONString(user);
		//设置要保存的内容
		indexRequest.source(jsonString, XContentType.JSON);
		//执行创建索引和保存数据
		IndexResponse index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);

		System.out.println(index);
	}

}

@Data
@AllArgsConstructor
class User{
	private String name;
	private Integer age;
	private String address;
}
