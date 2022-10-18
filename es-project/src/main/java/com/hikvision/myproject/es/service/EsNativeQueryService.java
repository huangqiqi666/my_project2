package com.hikvision.myproject.es.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.myproject.es.entity.data.Book;
import com.hikvision.myproject.es.entity.es.DiscussPost;
import com.hikvision.myproject.es.entity.es.Goods;
import com.hikvision.myproject.es.entity.es.User;
import com.hikvision.myproject.es.repository.es.GoodsRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Classname EsNativeQueryService
 * @Description TODO
 * @Date 2022/8/26 9:17
 * @Created by huangqiqi
 */
@Service
public class EsNativeQueryService {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    @Qualifier("elasticsearchClient")
    RestHighLevelClient restHighLevelClient;


    /**
     * 构建基本查询 - 搜索关键字、分类、品牌、规格、价格
     * @param searchMap
     * @return
     */
    private BoolQueryBuilder buildBasicQuery(Map searchMap) {
        // 构建布尔查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 关键字查询
        boolQueryBuilder.must(QueryBuilders.matchQuery("name",searchMap.get("keywords")));
        // 分类、品牌、规格 都是需要精准查询的，无需分词
        // 商品分类过滤
        if (searchMap.get("category") != null){
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("categoryName",searchMap.get("category")));
        }
        // 商品品牌过滤
        if(searchMap.get("brand") != null){
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("brandName",searchMap.get("brand")));
        }
        // 规格过滤
        if(searchMap.get("spec") != null){
            Map<String,String> map = (Map) searchMap.get("spec");
            for(Map.Entry<String,String> entry : map.entrySet()){
                // 规格查询[spec.xxx],因为规格是不确定的，所以需要精确查找，加上.keyword，如spec.颜色.keyword
                boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("spec." + entry.getKey() + ".keyword",entry.getValue()));
            }
        }
        // 价格过滤
        if(searchMap.get("price") != null){
            // 价格： 0-500  0-*
            String[] prices = ((String)searchMap.get("price")).split("-");
            if(!prices[0].equals("0")){  // 加两个0是，因为价格转换成分
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gt(prices[0] + "00"));
            }
            if(!prices[1].equals("*")){  // 价格有上限
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lt(prices[1] + "00"));
            }
        }
        return boolQueryBuilder;
    }



    /**
     * 查询分类列表
     * @param searchMap
     * @return
     */
    public List<String> searchCategoryList(Map searchMap) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 构建查询
        BoolQueryBuilder boolQueryBuilder = buildBasicQuery(searchMap);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        // 分类聚合名
        String groupName = "sku_category";
        // 构建聚合查询
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms(groupName).field("categoryName");
        nativeSearchQueryBuilder.addAggregation(termsAggregationBuilder);
        // 获取聚合分页结果
        AggregatedPage<Goods> goodsList = (AggregatedPage<Goods>) goodsRepository.search(nativeSearchQueryBuilder.build());
        // 在查询结果中找到聚合 - 根据聚合名称
        StringTerms stringTerms = (StringTerms) goodsList.getAggregation(groupName);
        // 获取桶
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        // 使用流Stream 将分类名存入集合
        List<String> categoryList = buckets.stream().map(bucket -> bucket.getKeyAsString()).collect(Collectors.toList());
        // 打印分类名称
        categoryList.forEach(System.out::println);
        return categoryList;
    }


    /**
     * @Description TODO
     * @param
     * @return void
     * @date   2022/8/26 10:26
     * @Author huangqiqi
     */
    public void testSearchByTemplate(){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        //elasticsearchTemplate.queryForPage()
        //elasticsearchRestTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchRe)

        SearchHits<DiscussPost> search = elasticsearchRestTemplate.search(searchQuery, DiscussPost.class);
        // 得到查询结果返回的内容
        List<SearchHit<DiscussPost>> searchHits = search.getSearchHits();
        // 设置一个需要返回的实体类集合
        List<DiscussPost> discussPosts = new ArrayList<>();
        // 遍历返回的内容进行处理
        for(SearchHit<DiscussPost> searchHit : searchHits){
            // 高亮的内容
            Map<String, List<String>> highLightFields = searchHit.getHighlightFields();
            // 将高亮的内容填充到content中
            searchHit.getContent().setTitle(highLightFields.get("title") == null ? searchHit.getContent().getTitle() : highLightFields.get("title").get(0));
            searchHit.getContent().setTitle(highLightFields.get("content") == null ? searchHit.getContent().getContent() : highLightFields.get("content").get(0));
            // 放到实体类中
            discussPosts.add(searchHit.getContent());
        }
        System.out.println(discussPosts.size());
        for(DiscussPost discussPost : discussPosts){
            System.out.println(discussPost);
        }
    }



    /**
     * @Description 1.QueryBuilders使用:构建查询；2.SearchSourceBuilder：接收查询结果 3.SearchRequest：查询
     * 参考:https://www.jianshu.com/p/2e85a9a7eee5
     * @param
     * @return void
     * @date   2022/8/29 13:38
     * @Author huangqiqi
     */
    public void testNativeQuery() throws IOException {
        //TODO:1.构建查询条件QueryBuilder
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //作者条件
        BoolQueryBuilder authorBoolQuery = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("author", "张三")).should(QueryBuilders.termQuery("author", "李四"));
        //标题条件
        MatchQueryBuilder titleMatchQuery = QueryBuilders.matchQuery("title", "钢铁");
        //构建BoolQueryBuilder：查询author是张三，并且price大于等于10小于等于50的数据
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(authorBoolQuery)
                .must(titleMatchQuery)
                .must(new RangeQueryBuilder("price").gte(10).lt(50));
        //TODO:2.构建SearchSourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(boolQueryBuilder)
                //排序
                .sort("price",SortOrder.DESC)
                //从第几行查询多少条
                .from(0).size(20)
                //返回的字段
                .fetchSource(new String[]{"title","price"},new String[]{})
                //高亮
                .highlighter(new HighlightBuilder().field("author").preTags("<span style='color:red'>").postTags("</span>"))
                //超时时间
                .timeout(new TimeValue(10, TimeUnit.SECONDS));

        //TODO:3.构建SearchRequest
        SearchRequest request = new SearchRequest("book");
        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        for (org.elasticsearch.search.SearchHit hit : response.getHits()) {
            // 提取高亮的字段内容，因为查询出来的文档数据和高亮字段的数据是分开的
            String highlightName = hit.getHighlightFields().get("name").fragments()[0].toString();
            // 提取查询出的文档数据，并转成对象
            Book book = JSONObject.parseObject(hit.getSourceAsString(), Book.class);
            // 用高亮的字段内容覆盖覆盖原文档字段
            book.setAuthor(highlightName);
            System.out.println(JSON.toJSONString(book));
        }



    }


    //测试案例
    public void searchDocument() throws IOException {
        SearchRequest request = new SearchRequest("user");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // school 是清华或北大的
        BoolQueryBuilder schoolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("school.keyword", "北大"))
                // .should(QueryBuilders.matchPhraseQuery("school", "北大"))
                .should(QueryBuilders.termQuery("school.keyword", "清华"));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(schoolQueryBuilder)
                // name 以王开头的
                .must(QueryBuilders.matchPhrasePrefixQuery("name", "王"))
                // age 大于等于10小于等于70
                .must(QueryBuilders.rangeQuery("age").gte(10).lte(70));

        // 设置查询条件
        searchSourceBuilder.query(boolQueryBuilder);
        // 字段过滤
        String[] includeFields = new String[]{"name", "age", "school"};
        searchSourceBuilder.fetchSource(includeFields, new String[]{});
        // 设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("name")
                .preTags("<span style='color:red'>")
                .postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        // 排序
        searchSourceBuilder.sort("age", SortOrder.DESC);
        // 分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        // 超时时间
        searchSourceBuilder.timeout(new TimeValue(10, TimeUnit.SECONDS));
        request.source(searchSourceBuilder);
        // 发起查询请求
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        for (org.elasticsearch.search.SearchHit hit : response.getHits()) {
            // 提取高亮的字段内容，因为查询出来的文档数据和高亮字段的数据是分开的
            String highlightName = hit.getHighlightFields().get("name").fragments()[0].toString();
            // 提取查询出的文档数据，并转成对象
            User user = JSONObject.parseObject(hit.getSourceAsString(), User.class);
            // 用高亮的字段内容覆盖覆盖原文档字段
            user.setName(highlightName);
            System.out.println(JSON.toJSONString(user));
        }
    }


}
