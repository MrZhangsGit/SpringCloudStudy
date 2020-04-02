package my.elasticsearch.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/3/31
 */
@Data
@Accessors(chain = true)
/**
 * @Document 标记为文档类型，indexName：对应索引库名称；type：对应在索引库中的类型；
 * shards：分片数量，默认5；replicas：副本数量，默认1
 */
@Document(indexName = "blog", type = "java")
public class BlogModel implements Serializable {
    private static final long serialVersionUID = 6320548148250372657L;

    @Id
    private String id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
}
