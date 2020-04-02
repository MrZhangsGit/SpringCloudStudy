package my.elasticsearch.service;

import my.elasticsearch.po.PhoneModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/1
 */
public interface PhoneRepository extends ElasticsearchRepository<PhoneModel, String> {
}
