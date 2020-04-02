package my.elasticsearch.service;

import my.elasticsearch.po.AutoSnapshotPO;
import my.elasticsearch.po.PhoneModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/1
 */
public interface SnapshotRepository extends ElasticsearchRepository<AutoSnapshotPO, String> {
    /**
     * 注意方法中的命名 by后面跟的需要是类中的字段名
     * 此处
     */
    List<AutoSnapshotPO> findByAutomaticIdLike(String automaticId);

    List<AutoSnapshotPO> findByTriggerDataLike(String automaticId);
}
