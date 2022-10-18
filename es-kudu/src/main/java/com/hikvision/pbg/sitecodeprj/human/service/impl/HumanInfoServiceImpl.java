package com.hikvision.pbg.sitecodeprj.human.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.pbg.sitecodeprj.common.AnalyspBigdataErrorCode;
import com.hikvision.pbg.sitecodeprj.common.BaseResult;
import com.hikvision.pbg.sitecodeprj.common.ListBean;
import com.hikvision.pbg.sitecodeprj.enums.DataSourceType;
import com.hikvision.pbg.sitecodeprj.es.ElasticClient;
import com.hikvision.pbg.sitecodeprj.human.dto.request.HumanQueryReqDto;
import com.hikvision.pbg.sitecodeprj.human.dto.request.HumanReqDto;
import com.hikvision.pbg.sitecodeprj.human.dto.response.HumanInfoDto;
import com.hikvision.pbg.sitecodeprj.human.entity.HumanInfo;
import com.hikvision.pbg.sitecodeprj.human.service.HumanInfoService;
import com.hikvision.pbg.sitecodeprj.kudu.config.kudu.OperationEnum;
import com.hikvision.pbg.sitecodeprj.kudu.template.KuduTemplate;
import com.hikvision.pbg.sitecodeprj.utils.DynamicBeanUtil;
import com.hikvision.pbg.sitecodeprj.utils.ListUtils;
import com.hikvision.pbg.sitecodeprj.utils.humanId.GenerateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kudu.client.KuduException;
import org.apache.kudu.shaded.com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


/**
 * @ClassName HumanInfoServiceImpl
 * @Description 人员信息操作服务
 * @Author xiaokai
 * @Date 19:29 2022/9/21
 * @Version 1.0
 **/
@Service
public class HumanInfoServiceImpl implements HumanInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HumanInfoService.class);

    @Autowired
    private ElasticClient elasticClient;
    @Autowired
    private KuduTemplate kuduTemplate;

    @Value("${es.human.info.table.name}")
    private String esHumanInfoTableName;

    @Value("${kudu.human.info.table.name}")
    private String kuduHumanInfoTableName;

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public ListBean<HumanInfoDto> getHumanInfoPage(HumanQueryReqDto param) {
        List<HumanInfoDto> resultList = new ArrayList<>();
        SearchResponse response = null;
        long total = 0L;

        try {
            //humanId is not null, get by id
            if (StringUtils.isNotBlank(param.getHumanId())) {
                Map<String, Object> document = elasticClient.getDocument(esHumanInfoTableName, esHumanInfoTableName, param.getHumanId());

                //if person flag satisfied
                if (null == document || !isOtherFactorSatisfied(document, param)) {
                    return new ListBean<>();
                }

                List<HumanInfo> humanInfos = (List<HumanInfo>) DynamicBeanUtil.getDynamicBean(Lists.newArrayList(document), HumanInfo.class);
                HumanInfoDto humanInfoDto = new HumanInfoDto();
                BeanUtils.copyProperties(humanInfos.get(0), humanInfoDto);
                humanInfoDto.setPersonFlag(StringUtils.isNotBlank(humanInfos.get(0).getFlag()) ? humanInfos.get(0).getFlag() : "");
                resultList.add(humanInfoDto);
                return new ListBean<>((long) resultList.size(), resultList);
            }

            // query by conditions
            response = elasticClient.queryByConditions(esHumanInfoTableName, param, generateQuery(param));
            List<Map<String, Object>> maps = elasticClient.parseSearchResponse(response);
            total = response.getHits().getTotalHits();

            List<HumanInfo> humanInfos = (List<HumanInfo>) DynamicBeanUtil.getDynamicBean(maps, HumanInfo.class);

            resultList = humanInfos.stream().map(HumanInfoDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("HumanInfoService.getHumanInfoPage error" + e);
            throw new RuntimeException(e);
        }

        return new ListBean<>(total, resultList);
    }

    /**
     * 判断是否满足其他查询条件
     *
     * @param document
     * @param param
     * @return
     */
    private boolean isOtherFactorSatisfied(Map<String, Object> document, HumanQueryReqDto param) {
        if (StringUtils.isNotBlank(param.getCertificateNumber())) {
            String cert = document.getOrDefault("certificate_number", "").toString();
            if (!cert.equals(param.getCertificateNumber())) return false;
        }
        if (StringUtils.isNotBlank(param.getName())) {
            String name = document.getOrDefault("name", "").toString();
            if (!name.contains(param.getName())) return false;
        }
        if (StringUtils.isNotBlank(param.getPhone())) {
            String phone = document.getOrDefault("phone", "").toString();
            if (!phone.equals(param.getPhone())) return false;
        }
        if (!CollectionUtils.isEmpty(param.getPersonFlag())) {
            String flag = document.getOrDefault("flag", "").toString();
            if (StringUtils.isBlank(flag)) return false;
            for (String f : param.getPersonFlag()) {
                // intersection is empty, return false
                if (ListUtils.getIntersection(Arrays.asList(f.split("@")), Arrays.asList(flag.split("@"))).isEmpty())
                    return false;
            }
        }

        return true;
    }

    private QueryBuilder generateQuery(HumanQueryReqDto param) {
        QueryBuilder certificateNumberQuery = null;
        QueryBuilder nameQuery = null;
        QueryBuilder phoneQuery = null;
        // term query
        if (StringUtils.isNotBlank(param.getCertificateNumber())) {
            certificateNumberQuery = QueryBuilders.termQuery("certificate_number", param.getCertificateNumber());
        }
        if (StringUtils.isNotBlank(param.getName())) {
            nameQuery = QueryBuilders.wildcardQuery("name", "*" + param.getName() + "*");
        }
        if (StringUtils.isNotBlank(param.getPhone())) {
            phoneQuery = QueryBuilders.termQuery("phone", param.getPhone());
        }

        // match query
        List<QueryBuilder> flagQueryList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(param.getPersonFlag())) {
            for (String f : param.getPersonFlag()) {
                flagQueryList.add(QueryBuilders.matchQuery("flag", f));
            }
        }

        // assemble query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!Objects.isNull(certificateNumberQuery)) boolQuery.must(certificateNumberQuery);
        if (!Objects.isNull(nameQuery)) boolQuery.must(nameQuery);
        if (!Objects.isNull(phoneQuery)) boolQuery.must(phoneQuery);
        for (QueryBuilder matchQuery : flagQueryList) {
            boolQuery.must(matchQuery);
        }

        return boolQuery;
    }

    @Override
    public BaseResult<?> save(HumanReqDto humanReqDto, OperationEnum operationEnum) {

        // if add person, judge person existed by certificate type and number,
        // if existed notify
        if (operationEnum.equals(OperationEnum.INSERT)) {
            HumanQueryReqDto reqDto = new HumanQueryReqDto();
            reqDto.setCertificateType(humanReqDto.getCertificateType());
            reqDto.setCertificateNumber(humanReqDto.getCertificateNumber());
            ListBean<HumanInfoDto> humanInfoPage = getHumanInfoPage(reqDto);
            boolean humanExisted = !(humanInfoPage == null || humanInfoPage.getTotalNum() == 0L || humanInfoPage.getList().isEmpty());
            if (humanExisted)
                return BaseResult.error(AnalyspBigdataErrorCode.ERR_DB_INSERT_HUMAN_EXISTED.getCode(), "人员已存在");
        }

        // assemble json object
        JSONObject object = new JSONObject();
        object = assembleJsonObj(humanReqDto, DataSourceType.ELASTIC_SEARCH);

        // es upsert
        try {
            if (StringUtils.isNotBlank(humanReqDto.getHumanId())) {
                elasticClient.updateDocByJson(esHumanInfoTableName, esHumanInfoTableName, humanReqDto.getHumanId(), JSON.toJSONString(object));
            } else {
                String humanId = GenerateUtils.cardToHumanId(Integer.parseInt(humanReqDto.getCertificateType()), humanReqDto.getCertificateNumber());
                object.put("human_id", humanId);
                elasticClient.updateDocByJson(esHumanInfoTableName, esHumanInfoTableName, humanId, JSON.toJSONString(object));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final JSONObject obj = object;
        // kudu upsert
        executorService.submit(()->{
            try {
                kuduTemplate.upsert(kuduHumanInfoTableName, obj);
            } catch (KuduException e) {
                e.printStackTrace();
            }
        });

        return BaseResult.success(null);
    }

    /**
     * assemble es and kudu upsert json object
     *
     * @param humanReqDto
     * @param type
     */
    private JSONObject assembleJsonObj(HumanReqDto humanReqDto, DataSourceType type) {
        JSONObject object = new JSONObject();
        object.put("certificate_number", humanReqDto.getCertificateNumber());
        object.put("certificate_type", humanReqDto.getCertificateType());
        object.put("name", humanReqDto.getName());
        object.put("gender", humanReqDto.getGender());
        object.put("phone", StringUtils.isNotBlank(humanReqDto.getPhone()) ? humanReqDto.getPhone() : null);
        object.put("face_url", StringUtils.isNotBlank(humanReqDto.getFaceUrl()) ? humanReqDto.getFaceUrl() : null);
        object.put("resident_address", StringUtils.isNotBlank(humanReqDto.getResidentAddress()) ? humanReqDto.getResidentAddress() : null);
        object.put("source", StringUtils.isNotBlank(humanReqDto.getSource()) ? humanReqDto.getSource() : null);
        object.put("nation", StringUtils.isNotBlank(humanReqDto.getNation()) ? humanReqDto.getNation() : null);
        object.put("district", StringUtils.isNotBlank(humanReqDto.getDistrict()) ? humanReqDto.getDistrict() : null);
        object.put("town", StringUtils.isNotBlank(humanReqDto.getTown()) ? humanReqDto.getTown() : null);
        object.put("village", StringUtils.isNotBlank(humanReqDto.getVillage()) ? humanReqDto.getVillage() : null);
        object.put("court_name", StringUtils.isNotBlank(humanReqDto.getCourtName()) ? humanReqDto.getCourtName() : null);
        object.put("flag", StringUtils.isNotBlank(humanReqDto.getPersonFlag()) ? humanReqDto.getPersonFlag() : null);
        object.put("human_id", StringUtils.isNotBlank(humanReqDto.getHumanId()) ? humanReqDto.getHumanId() : null);
        object.put("note", StringUtils.isNotBlank(humanReqDto.getNote())? humanReqDto.getNote(): null);
        object.put("city", StringUtils.isNotBlank(humanReqDto.getCity())? humanReqDto.getCity(): null);
        object.put("province", StringUtils.isNotBlank(humanReqDto.getProvince())? humanReqDto.getProvince(): null);
        return object;
    }

    @Override
    public void delete(List<String> idList) {
        // es delete
        List<Map<String, String>> params = idList.stream().map(id -> new HashMap<String, String>() {{
            put("id", id);
            put(ElasticClient.INDEX_KEY, esHumanInfoTableName);
            put(ElasticClient.TYPE_KEY, esHumanInfoTableName);
        }}).collect(Collectors.toList());

        try {
            elasticClient.bulkDelete(params);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // kudu delete
        executorService.submit(()->{
            JSONObject jsonObject = null;
            for (String s : idList) {
                jsonObject = new JSONObject();
                jsonObject.put("human_id", s);
                try {
                    kuduTemplate.delete(kuduHumanInfoTableName, jsonObject);
                } catch (KuduException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

}
