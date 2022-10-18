package com.hikvision.myproject.es;

import com.hikvision.myproject.es.entity.es.Goods;
import com.hikvision.myproject.es.repository.es.GoodsRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private GoodsRepository goodsRepository;


    @Test
    public void createIndex(){
        elasticsearchRestTemplate.createIndex(Goods.class);
    }

    //先添加一点数据
    @Test
    public void createDoc(){
//        Map map1 = new HashMap();
//        map1.put("颜色","蓝色");
//        map1.put("套餐","标准套餐");
//        Goods goods1 = new Goods(2L,"Redmi Note7秘境黑优惠套餐16G+64G",100,"xxxx",new Date(),2L,"手机","小米",map1,100);
//
//        Map map2 = new HashMap();
//        map2.put("颜色","蓝色");
//        map2.put("套餐","标准套餐");
//        Goods goods2 = new Goods(3L,"Redmi Note7秘境黑优惠套餐16G+64G",500,"xxxx",new Date(),3L,"手机","小米",map2,100);
//
//        Map map3 = new HashMap();
//        map3.put("颜色","黑色");
//        map3.put("尺寸","64寸");
//        Goods goods3 = new Goods(4L,"小米电视 黑色 64寸 优惠套餐",1000,"xxxx",new Date(),4L,"电视","小米",map3,100);
//
//        Map map4 = new HashMap();
//        map4.put("颜色","金色");
//        map4.put("尺寸","46寸");
//        Goods goods4 = new Goods(5L,"华为电视 金色 46寸 优惠套餐",1500,"xxxx",new Date(),5L,"电视","华为",map4,100);
//
//        Map map5 = new HashMap();
//        map5.put("颜色","白金色");
//        map5.put("网络制式","全网通5G");
//        Goods goods5 = new Goods(6L,"华为P30 金色 全网通5G 优惠套餐",2000,"xxxx",new Date(),6L,"手机","华为",map5,100);
//        List<Goods> list = new ArrayList<>();
//        list.add(goods1);
//        list.add(goods2);
//        list.add(goods3);
//        list.add(goods4);
//        list.add(goods5);
//        goodsRepository.saveAll(list);
        Map map1 = new HashMap();
        map1.put("颜色","紫色");
        map1.put("套餐","标准套餐");
        Goods goods1 = new Goods(7L,"小米 Mini9秘境黑优惠套餐16G+64G",100,"xxxx",new Date(),2L,"手机","小米",map1,100);
        goodsRepository.save(goods1);
//                Map map1 = new HashMap();
//        map1.put("颜色","蓝色");
//        map1.put("套餐","标准套餐");
//        Goods goods1 = new Goods(2L,"Redmi Note7秘境黑优惠套餐16G+64G",100,"xxxx",new Date(),2L,"手机","小米",map1,100);
//        goodsRepository.save(goods1);
    }


    /**
     * 搜索方法 - searchMap应该由前端传过来
     * searchMap里封装了一些条件，根据条件进行过滤
     */
    @Test
    public void search(){
        // 搜索条件Map
        Map searchMap = new HashMap();
        searchMap.put("keywords","小米");
//        searchMap.put("category","手机");
//        searchMap.put("brand","小米");
        Map map = new HashMap();
        map.put("颜色","紫色");
//        map.put("","");   // 其他规格类型
        searchMap.put("spec",map);
//        searchMap.put("price","0-3000");

        // 返回结果Map
        Map resultMap = new HashMap();
        // 查询商品列表
//        resultMap.putAll(searchSkuList(searchMap));
        // 查询分类列表
        List<String> categoryList = searchCategoryList(searchMap);
        resultMap.put("categoryList",categoryList);
    }



    /**
     * 查询分类列表
     * @param searchMap
     * @return
     */
    private List<String> searchCategoryList(Map searchMap) {
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
}

