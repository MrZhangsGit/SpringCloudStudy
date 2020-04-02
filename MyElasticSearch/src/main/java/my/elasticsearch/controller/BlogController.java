package my.elasticsearch.controller;

import com.alibaba.fastjson.JSON;
import my.elasticsearch.po.BlogModel;
import my.elasticsearch.service.BlogRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/3/31
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("/add")
    public String add(@RequestBody BlogModel blogModel) {
        blogRepository.save(blogModel);
        return "200";
    }

    @GetMapping("/getAll")
    public String getAll() {
        Iterable<BlogModel> iterable = blogRepository.findAll();
        List<BlogModel> list = new ArrayList<>();
        iterable.forEach(list::add);
        return JSON.toJSONString(list);
    }

    @PostMapping("/update")
    public String updateById(@RequestBody BlogModel blogModel) {
        String id = blogModel.getId();
        if (StringUtils.isEmpty(id)) {
            return "error";
        }
        blogRepository.save(blogModel);
        return JSON.toJSONString(blogRepository.findOne(id));
    }

    /**
     * 关键词模糊查询
     * @param title
     * @return
     */
    @GetMapping("/search/title")
    public String searchTitleLike(@RequestParam("title") String title) {
        if (StringUtils.isEmpty(title)) {
            return "error";
        }
        return JSON.toJSONString(blogRepository.findByTitleLike(title));
    }

    /**
     * 关键词精确查询
     * @param title
     * @return
     */
    @GetMapping("/search/title2")
    public String searchTitleExact(@RequestParam("title") String title) {
        if (StringUtils.isEmpty(title)) {
            return "error";
        }
        return JSON.toJSONString(blogRepository.findByTitle(title));
    }

    @GetMapping("/search/title3")
    public String searchTitleByTemplate(@RequestParam("title") String title) {
        if (StringUtils.isEmpty(title)) {
            return "error";
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(title)).build();
        List<BlogModel> list = elasticsearchTemplate.queryForList(searchQuery, BlogModel.class);
        return JSON.toJSONString(list);
    }
}
