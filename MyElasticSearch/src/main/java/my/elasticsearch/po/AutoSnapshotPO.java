package my.elasticsearch.po;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * @author zhangs
 * @Description 自动化执行快照信息
 * @createDate 2019/9/18
 */
@Data
@Accessors(chain = true)
@Document(indexName = "my-elasticsearch", type = "my.elasticsearch.po.AutoSnapshotPO")
public class AutoSnapshotPO {
    /**
     * 任务Id(数据Id，数据库自增)
     */
    private String id;

    /**
     * 多边控制自动化Id(双控类型自动化)
     */
    private String automaticId;

    /**
     * 自动化触发条件快照
     */
    private String triggerData;

    /**
     * 自动化响应输出快照
     */
    private String outputData;

    /**
     * 完成标记(0-完成|1-未完成)
     */
    private Integer complete;

    /**
     * 数据状态
     * 0 删除|1 正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
}
