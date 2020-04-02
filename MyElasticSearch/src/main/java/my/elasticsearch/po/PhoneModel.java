package my.elasticsearch.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/4/1
 */
@Data
@Accessors(chain = true)
@Document(indexName = "my-elasticsearch", type = "my.elasticsearch.po.PhoneModel")
public class PhoneModel implements Serializable {
    private static final long serialVersionUID = -5087658155687251393L;

    @Id
    private String id;

    private String name;

    /**
     * 颜色，用英文逗号分割
     */
    private String colors;

    /**
     * 颜色，用英文逗号分割
     */
    private String sellingPoints;

    private String price;

    /**
     * 产量
     */
    private Long yield;

    /**
     * 销售量
     */
    private Long sale;

    /**
     * 上市时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date marketTime;

    /**
     * 数据抓取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
