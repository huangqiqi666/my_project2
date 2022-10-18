package com.hikvision.pbg.sitecodeprj.human.service.impl;

import com.hikvision.pbg.sitecodeprj.common.ListBean;
import com.hikvision.pbg.sitecodeprj.es.ElasticClient;
import com.hikvision.pbg.sitecodeprj.human.dto.request.ScanDetailReqDto;
import com.hikvision.pbg.sitecodeprj.human.dto.response.HumanScanDetailDto;
import com.hikvision.pbg.sitecodeprj.human.entity.HumanScanDetail;
import com.hikvision.pbg.sitecodeprj.human.service.HumanScanDetailService;
import com.hikvision.pbg.sitecodeprj.kudu.utils.DateUtil;
import com.hikvision.pbg.sitecodeprj.utils.DynamicBeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.hikvision.pbg.sitecodeprj.utils.DynamicBeanUtil.getDynamicBean;

/**
 * @ClassName HumanScanDetailServiceImpl
 * @Description 人员扫码记录查询服务
 * @Author xiaokai
 * @Date 20:04 2022/9/21
 * @Version 1.0
 **/
@Service
public class HumanScanDetailServiceImpl implements HumanScanDetailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HumanScanDetailService.class);

    @Autowired
    private ElasticClient elasticClient;

    @Value("${es.scan.detail.table.name}")
    private String esScanDetailTableName;

    @Override
    public ListBean<?> queryPage(ScanDetailReqDto param) {
        List<HumanScanDetailDto> resultList;
        SearchResponse response;
        long total;

        try {
            response = elasticClient.queryByConditions(esScanDetailTableName, param, generateQuery(param));
            List<Map<String, Object>> maps = elasticClient.parseSearchResponse(response);
            total = response.getHits().getTotalHits();

            List<HumanScanDetail> humanScanDetailList = (List<HumanScanDetail>) DynamicBeanUtil.getDynamicBean(maps, HumanScanDetail.class);
            resultList = humanScanDetailList.stream().map(HumanScanDetailDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("HumanScanDetailService.queryPage error" + e);
            throw new RuntimeException(e);
        }

        return new ListBean<>(total, resultList);
    }

    @Override
    public ListBean<?> queryAbnormal(String certificateNumber) {
        List<HumanScanDetail> list = new ArrayList<>();
        List<List<HumanScanDetail>> lists = new ArrayList<>();
        long total = 0L;

        try {
            List<Map<String, Object>> responseMap = elasticClient.queryAllByConditions(esScanDetailTableName, new HashMap<String, Object>() {{
                put("visitor_id_num", certificateNumber);
            }});
            total = responseMap.size();
            List<HumanScanDetail> humanScanDetailList = (List<HumanScanDetail>) getDynamicBean(responseMap, HumanScanDetail.class);
            List<HumanScanDetail> sortedList = humanScanDetailList.stream().sorted(Comparator.comparingLong(v -> {
                try {
                    return DateUtil.str2Long2(v.getCreateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            })).collect(Collectors.toList());

            long startMil = 0L;
            long farTime = 0L;
            long interval = 10 * 1000L;
            List<HumanScanDetail> deque = new ArrayList<>();
            for (int i = 0; i < sortedList.size(); i++) {
                int j = i;
                while (j< sortedList.size() && DateUtil.str2Long(sortedList.get(j).getCreateTime()) < farTime) {
                    deque.add(sortedList.get(j));
                    j++;
                }
                if (deque.size() > 1 && deque.stream().distinct().count() > 0) {
                    lists.add(new ArrayList<>(deque));
                }
                deque = new LinkedList<>();

                deque.add(sortedList.get(i));
                startMil = DateUtil.str2Long(sortedList.get(i).getCreateTime());
                farTime = startMil + interval;
            }
        } catch (Exception e) {
            LOGGER.error("HumanScanDetailService.queryAbnormal error" + e);
            throw new RuntimeException(e);
        }
//        if (!CollectionUtils.isEmpty(lists)) {
//            for (List<HumanScanDetail> humanScanDetailList : lists) {
//                if (humanScanDetailList != null)
//                    list.addAll(humanScanDetailList);
//            }
//        }

        return new ListBean<>((long) lists.size(), lists);
    }


    /**
     * 生成查询 query
     *
     * @param param
     * @return
     */
    private QueryBuilder generateQuery(ScanDetailReqDto param) {
        QueryBuilder certificateNumberQuery = null;
        QueryBuilder regionQuery = null;
        // term query
        certificateNumberQuery = QueryBuilders.termQuery("visitor_id_num", param.getCertificateNumber());
        if (StringUtils.isNotBlank(param.getRegionName())) {
            regionQuery = QueryBuilders.termQuery("region_name", param.getRegionName());
        }

        // ranger query
        QueryBuilder timeQuery = null;
        timeQuery = QueryBuilders.rangeQuery("create_time").gte(param.getStartTime()).lt(param.getEndTime());

        // 组合query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(certificateNumberQuery);
        if (!Objects.isNull(regionQuery)) boolQuery.must(regionQuery);
        if (!Objects.isNull(timeQuery)) boolQuery.must(timeQuery);

        return boolQuery;
    }


}
