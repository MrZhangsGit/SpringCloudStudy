package com.my.rabbitmq.sender.po;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/10/23
 */
@Data
public class YardBasicBO {
    /**
     * 数据内容
     */
    private JSONObject data;

    /**
     * 时间戳
     */
    private Long timestamp;
}
