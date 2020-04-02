package my.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import my.elasticsearch.po.BlogModel;
import my.elasticsearch.po.PhoneModel;
import my.elasticsearch.service.CrawlData;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/1
 */
@RestController
@RequestMapping(value = "/phone")
@CrossOrigin
public class PhoneController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private CrawlData crawlData;

    @GetMapping("/crawl")
    public String crawl() {
        crawlData.crawl();
        return "200";
    }

    @GetMapping("/full")
    public String full(String keyword, Integer page, Integer size) {
        List<PhoneModel> list = new ArrayList<>();
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 0;
        }
        if (StringUtils.isNotEmpty(keyword)) {
            NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(keyword));
            SearchQuery searchQuery = searchQueryBuilder.build();
            list = elasticsearchTemplate.queryForList(searchQuery, PhoneModel.class);
        }
        return JSON.toJSONString(list);
    }
}
