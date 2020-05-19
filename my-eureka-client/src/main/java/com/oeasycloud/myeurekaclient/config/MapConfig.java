package com.oeasycloud.myeurekaclient.config;

import com.oeasycloud.myeurekaclient.po.mapConfig.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangs
 * @Description
 * @createDate 2020/3/13
 */
@Component
@ConfigurationProperties(prefix = "test")
@Data
public class MapConfig {
    private int a;
    private int b;
    private List<Properties> propertiesList;
    private Map<String, Integer> limitSizeMap;
}
