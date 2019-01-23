package my.elastic.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/21
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "elasticJob")
public class ElasticJobConfig {
    private String serverList;
    private String namespace;
}
