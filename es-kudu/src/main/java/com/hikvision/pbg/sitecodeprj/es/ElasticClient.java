package com.hikvision.pbg.sitecodeprj.es;

import com.alibaba.fastjson.JSON;
import com.hikvision.pbg.sitecodeprj.common.BasePageBo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;

/**
 * elasticSearh ?????????
 *
 * @author Stephen
 * @version 1.0
 * @date 2018/09/17 11:12:08
 */
@Component
public class ElasticClient implements Closeable {

    @Value("${es.config.elasticsearch.address}")
    private String esAddress;

    public static final String INDEX_KEY = "_index";
    public static final String TYPE_KEY = "_type";
    public static final String INDEX = "ads_relation_stat_human_vehicle";
    public static final String TYPE = "_doc";
    public static final String HTTP_SCHEME = "http";
    public static final String ORDER_KEY = "create_time";


    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticClient.class);

    //    private String[] hosts = {"15.202.202.66:39200","15.202.202.68:39200","15.202.202.70:39200"};
    private String[] hosts;

    protected RestHighLevelClient client;

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(client)) {
            client.close();
        }
    }

    /**
     * ?????????hosts
     */
    public void initHosts() {
        hosts = esAddress.split(",");
    }

    /**
     * ???????????????
     */
    @PostConstruct
    public void configure() {
        initHosts();
        Validate.noNullElements(hosts, "Elastic???????????????????????????");
        HttpHost[] httpHosts = Arrays.stream(hosts).map(host -> {
            String[] hostParts = host.split(":");
            String hostName = hostParts[0];
            int port = Integer.parseInt(hostParts[1]);
            return new HttpHost(hostName, port, HTTP_SCHEME);
        }).filter(obj -> true).toArray(HttpHost[]::new);
        client = new RestHighLevelClient(RestClient.builder(httpHosts));
    }

    /**
     * ????????????(??????????????????5???????????????1)
     *
     * @param indexName
     * @throws IOException
     */
    public void createIndex(String indexName, XContentBuilder xContentBuilder) throws IOException {
        if (checkIndexExists(indexName)) {
            LOGGER.error("\"index={}\"?????????????????????", indexName);
            return;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.mapping(TYPE, xContentBuilder);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        // ??????????????????????????????????????????
        boolean acknowledged = response.isAcknowledged();
        // ???????????????????????????????????????????????????????????????????????????????????????
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            LOGGER.info("????????????????????????????????????{}", indexName);
        }
    }

    /**
     * ????????????(?????? index ??? type)
     *
     * @param index
     * @param type
     * @throws IOException
     */
    public void createIndex(String index, String type, XContentBuilder xContentBuilder) throws IOException {
        if (checkIndexExists(index)) {
            LOGGER.error("\"index={}\"??????????????????", index);
            return;
        }
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.mapping(type, xContentBuilder);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            LOGGER.info("????????????????????????????????????{}", index);
        }
    }

    /**
     * ????????????(????????????????????????????????????)
     *
     * @param indexName
     * @param shards
     * @param replicas
     * @throws IOException
     */
    public void createIndex(String indexName, int shards, int replicas, XContentBuilder xContentBuilder) throws IOException {
        if (checkIndexExists(indexName)) {
            LOGGER.error("\"index={}\"??????????????????", indexName);
            return;
        }
        Settings.Builder builder = Settings.builder().put("index.number_of_shards", shards).put("index.number_of_replicas", replicas);
        CreateIndexRequest request = new CreateIndexRequest(indexName).settings(builder);
        request.mapping(TYPE, xContentBuilder);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        if (response.isAcknowledged() || response.isShardsAcknowledged()) {
            LOGGER.info("????????????????????????????????????{}", indexName);
        }
    }

    /**
     * ????????????
     *
     * @param indexName
     * @throws IOException
     */
    public void deleteIndex(String indexName) throws IOException {
        try {
            //????????????????????????
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            //????????????
            AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            if (response.isAcknowledged()) {
                LOGGER.info("{} ?????????????????????", indexName);
            }
        } catch (ElasticsearchException ex) {
            if (ex.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("{} ??????????????????", indexName);
            }
            LOGGER.error("???????????????");
        }
    }

    /**
     * ????????????????????????
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean checkIndexExists(String indexName) {
        GetIndexRequest request = new GetIndexRequest().indices(indexName);
        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("??????????????????????????????????????????");
        }
        return false;
    }

    /**
     * ????????????
     *
     * @param indexName
     * @throws IOException
     */
    public void openIndex(String indexName) throws IOException {
        if (!checkIndexExists(indexName)) {
            LOGGER.error("??????????????????");
            return;
        }
        OpenIndexRequest request = new OpenIndexRequest(indexName);
        OpenIndexResponse response = client.indices().open(request, RequestOptions.DEFAULT);
        if (response.isAcknowledged() || response.isShardsAcknowledged()) {
            LOGGER.info("{} ?????????????????????", indexName);
        }
    }

    /**
     * ????????????
     *
     * @param indexName
     * @throws IOException
     */
    public void closeIndex(String indexName) throws IOException {
        if (!checkIndexExists(indexName)) {
            LOGGER.error("??????????????????");
            return;
        }
        CloseIndexRequest request = new CloseIndexRequest(indexName);
        CloseIndexResponse response = client.indices().close(request, RequestOptions.DEFAULT);
        if (response.isAcknowledged()) {
            LOGGER.info("{} ??????????????????", indexName);
        }
    }

    /**
     * ???????????????????????????(???????????? message?????? ??????ik?????????)
     *
     * @param index
     * @param type
     * @throws IOException
     */
    public void setFieldsMapping(String index, String type, XContentBuilder xContentBuilder) {
        PutMappingRequest request = new PutMappingRequest(index).type(type);
        try {
            request.source(xContentBuilder);
            PutMappingResponse response = (PutMappingResponse) client.indices().putMapping(request, RequestOptions.DEFAULT);
            if (response.isAcknowledged()) {
                LOGGER.info("????????????\"index={}, type={}\"??????????????????????????????", index, type);
            }
        } catch (IOException e) {
            LOGGER.error("\"index={}, type={}\"??????????????????????????????????????????????????????", index, type);
        }
    }


    /**
     * ???????????????????????????????????????
     *
     * @param index
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    public Map<String, Object> getDocument(String index, String type, String id) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        GetRequest request = new GetRequest(index, type, id);
        // ??????(???)
        request.realtime(false);
        // ????????????????????????(???)
        request.refresh(true);

        GetResponse response = null;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.warn("????????????????????????????????????");
            }
            if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("???????????????");
            }
            LOGGER.error("???????????????");
        }

        if (Objects.nonNull(response)) {
            if (response.isExists()) { // ????????????      
                resultMap = response.getSourceAsMap();
            } else {
                // ????????????????????????????????? ???????????????????????????????????????404????????????????????????????????????GetResponse????????????????????????
                // ??????????????????????????????????????????????????????isExists????????????false???
                LOGGER.warn("{} ????????????????????????????????????", id);
                return null;
            }
        }
        return resultMap;
    }

    /**
     * ???????????????????????????
     *
     * @param index
     * @param column
     * @param value
     * @param size
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> searchDocument(String index, String column, String value, int size) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        SearchRequest request = new SearchRequest(index);
        request.requestCache(true);

        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.termQuery(column, value);
        searchBuilder.query(queryBuilder);
        searchBuilder.from(0).size(size);
        request.source(searchBuilder);
        SearchResponse response = null;
        response = client.search(request, RequestOptions.DEFAULT);
        int failedShards = response.getFailedShards();
        if (failedShards > 0) {
            LOGGER.error("?????????????????????????????????");
            for (ShardSearchFailure failure : response.getShardFailures()) {
                String reason = failure.reason();
                LOGGER.error("???????????????????????????{}", reason);
            }
        }
        List<Map<String, Object>> list = parseSearchResponse(response);
        if (!list.isEmpty()) {
            resultList.addAll(list);
        }
        return resultList;
    }

    /**
     * ???????????????????????????
     *
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> searchDocument(String index, Map<String, Object> map) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        SearchRequest request = new SearchRequest(index);
        request.requestCache(true);

        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            QueryBuilder queryBuilder = QueryBuilders.termQuery(entry.getKey(), entry.getValue());
            boolQueryBuilder.must(queryBuilder);
        }
        searchBuilder.query(boolQueryBuilder);
        searchBuilder.from(0);
        request.source(searchBuilder);
        SearchResponse response = null;
        response = client.search(request, RequestOptions.DEFAULT);
        int failedShards = response.getFailedShards();
        if (failedShards > 0) {
            LOGGER.error("?????????????????????????????????");
            for (ShardSearchFailure failure : response.getShardFailures()) {
                String reason = failure.reason();
                LOGGER.error("???????????????????????????{}", reason);
            }
        }
        List<Map<String, Object>> list = parseSearchResponse(response);
        if (!list.isEmpty()) {
            resultList.addAll(list);
        }
        return resultList;
    }


    /**
     * ??????????????????
     *
     * @param params
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> multiGet(List<Map<String, String>> params) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        MultiGetRequest request = new MultiGetRequest();
        for (Map<String, String> dataMap : params) {
            String index = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String type = dataMap.getOrDefault(TYPE_KEY, TYPE);
            String id = dataMap.get("id");
            if (StringUtils.isNotBlank(id)) {
                request.add(new MultiGetRequest.Item(index, type, id));
            }
        }
        request.realtime(false);
        request.refresh(true);
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> list = parseMGetResponse(response);
        if (!list.isEmpty()) {
            resultList.addAll(list);
        }
        return resultList;
    }

    /**
     * ????????????????????????(??????"ctx._source.posttime=\"2018-09-18\"")????????????
     *
     * @param index
     * @param type
     * @param id
     * @param script
     */
    public void updateDocByScript(String index, String type, String id, String script) throws IOException {
        Script inline = new Script(script);
        UpdateRequest request = new UpdateRequest(index, type, id).script(inline);
        try {
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("?????????????????????");
            } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
                LOGGER.error("\"index={},type={},id={}\"???????????????????????????????????????", response.getIndex(), response.getType(), response.getId());
            } else if (response.getResult() == DocWriteResponse.Result.NOOP) {
                LOGGER.error("????????????????????????");
            }

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                LOGGER.error("???????????????????????????");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    LOGGER.error("??????????????????{}", reason);
                }
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("??????????????????????????????????????????");
            } else if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("?????????????????????");
            }
            LOGGER.error("???????????????");
        }
    }

    /**
     * ????????????JSON?????????????????????(????????????????????????????????????????????????????????????)
     *
     * @param index
     * @param type
     * @param id
     * @param jsonString
     * @throws IOException
     */
    public void updateDocByJson(String index, String type, String id, String jsonString) throws IOException {
        if (!checkIndexExists(index)) {
            LOGGER.error("??????????????????");
        }
        UpdateRequest request = new UpdateRequest(index, type, id);
        request.doc(jsonString, XContentType.JSON);
        // ??????????????????????????????????????????????????????????????????????????????
        request.docAsUpsert(true);
        try {
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            String indexName = response.getIndex();
            String typeName = response.getType();
            String documentId = response.getId();
            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                LOGGER.info("?????????????????????index: {}, type: {}, id: {}", indexName, typeName, documentId);
            } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("?????????????????????");
            } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
                LOGGER.error("\"index={},type={},id={}\"???????????????????????????????????????", indexName, typeName, documentId);
            } else if (response.getResult() == DocWriteResponse.Result.NOOP) {
                LOGGER.error("????????????????????????");
            }

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                LOGGER.error("???????????????????????????");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    LOGGER.error("??????????????????{}", reason);
                }
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("??????????????????????????????????????????");
            } else if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("?????????????????????");
            }
            LOGGER.error("???????????????");
        }
    }

    /**
     * ??????????????????
     *
     * @param params
     * @throws IOException
     */
    public void bulkAdd(List<Map<String, Object>> params, String index, String type) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> dataMap : params) {
            String id = dataMap.get("human_id_new").toString();
            String jsonString = JSON.toJSONString(dataMap);
            if (StringUtils.isNotBlank(id)) {
                IndexRequest request = new IndexRequest(index, type, id).source(jsonString, XContentType.JSON);
                bulkRequest.add(request);
            }
        }
        // ????????????(2??????)
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        // ????????????
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);

        if (bulkRequest.numberOfActions() == 0) {
            LOGGER.error("??????????????????????????????????????????");
            return;
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // ??????????????????
        if (!bulkResponse.hasFailures()) {
            LOGGER.info("???????????????????????????");
        } else {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    LOGGER.error("\"index={}, type={}, id={}\"????????????????????????", failure.getIndex(), failure.getType(), failure.getId());
                    LOGGER.error("??????????????????: {}", failure.getMessage());
                } else {
                    LOGGER.info("\"index={}, type={}, id={}\"????????????????????????", bulkItemResponse.getIndex(), bulkItemResponse.getType(), bulkItemResponse.getId());
                }
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param params
     * @throws IOException
     */
    public void bulkUpdate(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, String> dataMap : params) {
            String index = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String type = dataMap.getOrDefault(TYPE_KEY, TYPE);
            String id = dataMap.get("id");
            String jsonString = dataMap.get("json");
            if (StringUtils.isNotBlank(id)) {
                UpdateRequest request = new UpdateRequest(index, type, id).doc(jsonString, XContentType.JSON);
                request.docAsUpsert(true);
                bulkRequest.add(request);
            }
        }
        if (bulkRequest.numberOfActions() == 0) {
            LOGGER.error("??????????????????????????????????????????");
            return;
        }
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!bulkResponse.hasFailures()) {
            LOGGER.info("???????????????????????????");
        } else {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    LOGGER.error("\"index={}, type={}, id={}\"????????????????????????", failure.getIndex(), failure.getType(), failure.getId());
                    LOGGER.error("??????????????????: {}", failure.getMessage());
                } else {
                    LOGGER.info("\"index={}, type={}, id={}\"????????????????????????", bulkItemResponse.getIndex(), bulkItemResponse.getType(), bulkItemResponse.getId());
                }
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param params
     * @throws IOException
     */
    public void bulkDelete(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, String> dataMap : params) {
            String index = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String type = dataMap.getOrDefault(TYPE_KEY, TYPE);
            String id = dataMap.get("id");
            if (StringUtils.isNotBlank(id)) {
                DeleteRequest request = new DeleteRequest(index, type, id);
                bulkRequest.add(request);
            }
        }
        if (bulkRequest.numberOfActions() == 0) {
            LOGGER.error("?????????????????????????????????");
            return;
        }
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!bulkResponse.hasFailures()) {
            LOGGER.info("???????????????????????????");
        } else {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    LOGGER.error("\"index={}, type={}, id={}\"????????????????????????", failure.getIndex(), failure.getType(), failure.getId());
                    LOGGER.error("??????????????????: {}", failure.getMessage());
                } else {
                    LOGGER.info("\"index={}, type={}, id={}\"????????????????????????", bulkItemResponse.getIndex(), bulkItemResponse.getType(), bulkItemResponse.getId());
                }
            }
        }
    }


    private List<Map<String, Object>> parseMGetResponse(MultiGetResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        MultiGetItemResponse[] responses = response.getResponses();
        for (MultiGetItemResponse item : responses) {
            GetResponse getResponse = item.getResponse();
            if (Objects.nonNull(getResponse)) {
                if (!getResponse.isExists()) {
                    LOGGER.error("\"index={}, type={}, id={}\"??????????????????????????????????????????", getResponse.getIndex(), getResponse.getType(), getResponse.getId());
                } else {
                    list.add(getResponse.getSourceAsMap());
                }
            } else {
                MultiGetResponse.Failure failure = item.getFailure();
                ElasticsearchException e = (ElasticsearchException) failure.getFailure();
                if (e.status() == RestStatus.NOT_FOUND) {
                    LOGGER.error("\"index={}, type={}, id={}\"?????????????????????", failure.getIndex(), failure.getType(), failure.getId());
                } else if (e.status() == RestStatus.CONFLICT) {
                    LOGGER.error("\"index={}, type={}, id={}\"????????????????????????", failure.getIndex(), failure.getType(), failure.getId());
                }
            }
        }
        return list;
    }

    /**
     * ??????????????????
     *
     * @return
     * @throws IOException
     */
    public SearchResponse queryByConditions(String index, BasePageBo params, QueryBuilder query)
            throws IOException {

        FieldSortBuilder order = SortBuilders.fieldSort(ORDER_KEY).order(SortOrder.DESC);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.timeout(TimeValue.timeValueMinutes(2L)).query(query).sort(order)
                .from((params.getPageNum() - 1) * params.getPageSize())
                .size(params.getPageSize());

        SearchRequest request = new SearchRequest(index);
        request.source(searchBuilder);
        System.out.println(JSON.toJSONString(searchBuilder));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        int failedShards = response.getFailedShards();
        if (failedShards > 0) {
            LOGGER.error("?????????????????????????????????");
            for (ShardSearchFailure failure : response.getShardFailures()) {
                String reason = failure.reason();
                LOGGER.error("???????????????????????????{}", reason);
            }
        }
        return response;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    public List<Map<String, Object>> queryAllByConditions(String index, Map<String, Object> map) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            QueryBuilder queryBuilder = QueryBuilders.termQuery(entry.getKey(), entry.getValue());
            boolQuery.must(queryBuilder);
        }
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.size(10000).query(boolQuery);

        // ????????? scroll ?????????
        SearchRequest request = new SearchRequest(index);
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        request.source(searchBuilder).scroll(scroll);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        String scrollId = response.getScrollId();
        SearchHit[] searchHits = response.getHits().getHits();
        // ????????????scroll????????????????????????List???
        for (SearchHit searchHit : searchHits) {
            resultList.add(searchHit.getSourceAsMap());
        }
        // ????????????scrollId??????????????????????????????
        while (searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            response = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = response.getScrollId();
            searchHits = response.getHits().getHits();
            // ???????????????????????????
            for (SearchHit searchHit : searchHits) {
                resultList.add(searchHit.getSourceAsMap());
            }
        }
        // ?????? scroll ?????????
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        return resultList;
    }


    public List<Map<String, Object>> parseSearchResponse(SearchResponse response) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            resultList.add(hit.getSourceAsMap());
        }
        return resultList;
    }
}