package com.hinz.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.hinz.elasticsearch.bean.User;
import com.hinz.elasticsearch.config.ElasticSearchConfig;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class ElasticsearchApplicationTests {

	@Resource
	private RestHighLevelClient client;


	@Test
	void contextLoads() throws Exception{

	}


	@Test
	void indexData() throws Exception{
		IndexRequest indexRequest = new IndexRequest("user");
		User user = new User("hinzzz",18,"亚历山大大西北出来",new BigDecimal(1));
		String jsonString = JSON.toJSONString(user);
		//设置要保存的内容
		indexRequest.source(jsonString, XContentType.JSON);
		//执行创建索引和保存数据
		IndexResponse index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);

		System.out.println(index);
		//IndexResponse[index=user,type=_doc,id=76Sb2nYBEfd-vMN2ksrv,version=1,result=created,seqNo=2,primaryTerm=1,shards={"total":2,"successful":1,"failed":0}]
	}

	@Test
	void getData()throws Exception{
		GetRequest getIndexRequest = new GetRequest("user","7aRw1XYBEfd-vMN2XMo_");
		GetResponse getResponse = client.get(getIndexRequest, ElasticSearchConfig.COMMON_OPTIONS);
		System.out.println("getResponse = " + getResponse);
		//getResponse = {"_index":"user","_type":"_doc","_id":"7aRw1XYBEfd-vMN2XMo_","_version":1,"_seq_no":0,"_primary_term":1,"found":true,"_source":{"address":"亚历山大大西北出来","age":18,"name":"hinzzz"}}
		//getResponse.getXXX 可获取索引信息
		String index = getResponse.getIndex();
		System.out.println(index);
		String id = getResponse.getId();
		System.out.println(id);
		if (getResponse.isExists()) {
			long version = getResponse.getVersion();
			System.out.println(version);
			String sourceAsString = getResponse.getSourceAsString();
			System.out.println(sourceAsString);
			Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();//数据转map
			System.out.println(sourceAsMap);
			byte[] sourceAsBytes = getResponse.getSourceAsBytes();
		}
	}


	@Test
	void complexSearch()throws Exception{
		//搜索address中包含北京的所有人的年龄分布以及平均年龄，平均薪资
		SearchRequest searchRequest = new SearchRequest();

		//返回所有结果
		//System.out.println("查询所有数据" + client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS));

		//指定索引
		//searchRequest.indices("user","my_index");
		//System.out.println("查询多个索引" + client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS));


		//查询地址
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("address","北京"));

		//查询年龄分布
		TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("allAge").field("age").size(100);
		searchSourceBuilder.aggregation(termsAggregationBuilder);

		//查询平均年龄
		AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("ageAvg").field("age");
		searchSourceBuilder.aggregation(avgAggregationBuilder);

		//查询平均薪资
		AvgAggregationBuilder avgAggregationBuilder1 = AggregationBuilders.avg("salaryAvg").field("salary");
		searchSourceBuilder.aggregation(avgAggregationBuilder1);

		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

		System.out.println("search = " + searchResponse);


		//将检索结果封装为Bean
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit searchHit : searchHits) {
			String sourceAsString = searchHit.getSourceAsString();
			User user = JSON.parseObject(sourceAsString, User.class);
			System.out.println(user);

		}

		//获取聚合信息
		Aggregations aggregations = searchResponse.getAggregations();

		Terms ageAgg1 = aggregations.get("allAge");

		for (Terms.Bucket bucket : ageAgg1.getBuckets()) {
			String keyAsString = bucket.getKeyAsString();
			System.out.println("年龄："+keyAsString+" ==> "+bucket.getDocCount());
		}
		Avg ageAvg1 = aggregations.get("ageAvg");
		System.out.println("平均年龄："+ageAvg1.getValue());

		Avg balanceAvg1 = aggregations.get("salaryAvg");
		System.out.println("平均薪资："+balanceAvg1.getValue());

	}

}


