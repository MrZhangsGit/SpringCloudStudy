package my.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import my.elasticsearch.dao.IAutoSnapshotDao;
import my.elasticsearch.po.AutoSnapshotPO;
import my.elasticsearch.po.PageableMO;
import my.elasticsearch.po.PhoneModel;
import my.elasticsearch.service.CrawlData;
import my.elasticsearch.service.SnapshotRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/1
 */
@RestController
@RequestMapping(value = "/snapshot")
@CrossOrigin
@Slf4j
public class SnapshotController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private IAutoSnapshotDao iAutoSnapshotDao;
    @Autowired
    private SnapshotRepository snapshotRepository;

    @GetMapping("/getAllNum")
    public String full() {
        List<AutoSnapshotPO> list = iAutoSnapshotDao.getAll();
        log.info("...list is empty:{}", CollectionUtils.isEmpty(list));
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            long start = System.currentTimeMillis();
            for (AutoSnapshotPO autoSnapshotPO:list) {
                snapshotRepository.save(autoSnapshotPO);
                if (i != 0 && ((i * 100) % list.size()) == 0) {
                    log.info("存储进度:{}%", ((i * 100) / list.size()));
                }
                i++;
            }
            log.info("存储{}数据，总耗时:{}ms", list.size(), (System.currentTimeMillis() - start));
        }
        return JSON.toJSONString(list.size());
    }

    @GetMapping("/getAll")
    public String getAll() {
        Iterable<AutoSnapshotPO> iterable = snapshotRepository.findAll();
        List<AutoSnapshotPO> list = new ArrayList<>();
        iterable.forEach(list::add);
        return JSON.toJSONString(list);
    }

    @GetMapping("/getByPage")
    public PageableMO<AutoSnapshotPO> getAllByPage(@RequestParam("automaticId") String automaticId, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 0) {
            return null;
        }
        if (pageSize == null || pageSize < 0) {
            return null;
        }
        if (StringUtils.isEmpty(automaticId)) {
            return null;
        }
        PageableMO<AutoSnapshotPO> result = new PageableMO<>();
        result.setPageNumber(pageNum);
        result.setPageSize(pageSize);
        Pageable pageable = new PageRequest(pageNum, pageSize);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withPageable(pageable);
        searchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(automaticId));
        SearchQuery searchQuery = searchQueryBuilder.build();
        /**
         * 此处直接返回Page<AutoSnapshotPO>会报NullPoint
         */
        Page<AutoSnapshotPO> pageTemp = elasticsearchTemplate.queryForPage(searchQuery, AutoSnapshotPO.class);
        result.setTotal(pageTemp.getTotalElements());
        result.setTotalPages(pageTemp.getTotalPages());
        result.setContent(pageTemp.getContent());
        return result;
    }

    @GetMapping("/getByAId")
    public String getByDevId(@RequestParam("automaticId") String automaticId) {
        if (StringUtils.isEmpty(automaticId)) {
            return "error";
        }
        return JSON.toJSONString(snapshotRepository.findByAutomaticIdLike(automaticId));
    }

    @GetMapping("/getByTriggerContent")
    public String getByTriggerContent(@RequestParam("content") String content) {
        if (StringUtils.isEmpty(content)) {
            return "error";
        }
        long start = System.currentTimeMillis();
        StringBuffer result = new StringBuffer(JSON.toJSONString(snapshotRepository.findByTriggerDataLike(content)));
        log.info("根据TriggerContent模糊查询耗时:{}ms", (System.currentTimeMillis() - start));
        return result.toString();
    }

    /**
     * 创建指定索引
     */
    @GetMapping("/createIndexes")
    public String createIndexes(@RequestParam("indexName") String indexName) {
        elasticsearchTemplate.createIndex(indexName);
        return "200";
    }

    /**
     * 删除指定索引
     */
    @GetMapping("/delIndexes")
    public String delIndexes(@RequestParam("indexName") String indexName) {
        elasticsearchTemplate.deleteIndex(indexName);
        return "200";
    }

    /**
     * 自定义查询
     */
    @GetMapping("/matchQuery")
    public PageableMO<AutoSnapshotPO> matchQuery(@RequestParam("key") String key, @RequestParam("value") String value) {
        /**
         * 构建查询条件
         */
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        Pageable pageable = new PageRequest(1, 20);
        queryBuilder.withPageable(pageable);
        /**
         * 添加基本分词查询
         */
        queryBuilder.withQuery(QueryBuilders.matchQuery(key, value));
        SearchQuery searchQuery = queryBuilder.build();

        PageableMO<AutoSnapshotPO> result = new PageableMO<>();
        Page<AutoSnapshotPO> pageTemp = elasticsearchTemplate.queryForPage(searchQuery, AutoSnapshotPO.class);
        result.setPageNumber(pageable.getPageNumber());
        result.setPageSize(pageable.getPageSize());
        result.setTotal(pageTemp.getTotalElements());
        result.setTotalPages(pageTemp.getTotalPages());
        result.setContent(pageTemp.getContent());
        return result;
    }

    /**
     * 比较BoolQueryBuilder和NativeSearchQueryBuilder的添加基本分词查询部分
     * public BoolQueryBuilder must(QueryBuilder queryBuilder) {
     *  this.mustClauses.add(queryBuilder);
     *  return this;
     * }
     * public NativeSearchQueryBuilder withQuery(QueryBuilder queryBuilder) {
     *  this.queryBuilder = queryBuilder;
     *  return this;
     * }
     * 查看源码可知：BoolQueryBuilder可添加多个分词
     */

    /**
     * 高级自定义查询
     */
    @GetMapping("/seniorMatchQuery")
    public PageableMO<AutoSnapshotPO> seniorMatchQuery(
            @RequestParam("id") String id, @RequestParam("automaticId") String automaticId,
            @RequestParam("triggerData") String triggerData, @RequestParam("outputData") String outputData,
            Integer complete, Integer status) {
        /**
         * 构建查询条件
         */
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        Pageable pageable = new PageRequest(1, 20);
        /**
         * 添加基本分词查询
         */
        if (StringUtils.isNotEmpty(id)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("id", id));
        }
        if (StringUtils.isNotEmpty(automaticId)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("automaticId", automaticId));
        }
        if (StringUtils.isNotEmpty(triggerData)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("triggerData", triggerData));
        }
        if (StringUtils.isNotEmpty(outputData)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("outputData", outputData));
        }
        if (complete != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("complete", complete));
        }
        if (status != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("status", status));
        }
        /*if (!StringUtils.isEmpty(start)) {
            Date startTime = null;
            try {
                startTime = DateTimeUtil.stringToDate(start, DateTimeFormat.yyyy_MM_dd_HH_mm_ss);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createTime").gt(startTime.getTime()));
        }

        if (!StringUtils.isEmpty(end)) {
            Date endTime = null;
            try {
                endTime = DateTimeUtil.stringToDate(end, DateTimeFormat.yyyy_MM_dd_HH_mm_ss);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createTime").lt(endTime.getTime()));
        }*/

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(boolQueryBuilder)
                .build();

        PageableMO<AutoSnapshotPO> result = new PageableMO<>();
        Page<AutoSnapshotPO> pageTemp = elasticsearchTemplate.queryForPage(searchQuery, AutoSnapshotPO.class);
        result.setPageNumber(pageable.getPageNumber());
        result.setPageSize(pageable.getPageSize());
        result.setTotal(pageTemp.getTotalElements());
        result.setTotalPages(pageTemp.getTotalPages());
        result.setContent(pageTemp.getContent());
        return result;
    }

    @GetMapping("/findAllByPage")
    public PageableMO<AutoSnapshotPO> findAllByPage(Integer pageNum, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        Page<AutoSnapshotPO> pageTemp = this.snapshotRepository.findAll(pageable);
        PageableMO<AutoSnapshotPO> result = new PageableMO<>();
        result.setPageNumber(pageNum);
        result.setPageSize(pageSize);
        result.setTotal(pageTemp.getTotalElements());
        result.setTotalPages(pageTemp.getTotalPages());
        result.setContent(pageTemp.getContent());
        return result;
    }
}
