package my.elasticsearch.dao;

import my.elasticsearch.po.AutoSnapshotPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangs
 * @Description 自动化执行快照信息数据操作访问接口
 * @createDate 2019/9/23
 */
@Mapper
@Repository
public interface IAutoSnapshotDao {
    /**
     * @param
     * @return
     * @author zhangs
     * @createDate 2019/9/24
     */
    List<AutoSnapshotPO> getAll();
}
