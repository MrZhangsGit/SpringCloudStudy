package my.elasticsearch.service;

import my.elasticsearch.po.BlogModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/3/31
 */
public interface BlogRepository extends ElasticsearchRepository<BlogModel, String> {
    List<BlogModel> findByTitleLike(String keyword);

    /**
     * match_phrase:查询匹配模式
     * ?0:代表变量
     * @param keyword
     * @return
     */
    @Query("{\"match_phrase\":{\"title\":\"?0\"}}")
    List<BlogModel> findByTitle(String keyword);
}
