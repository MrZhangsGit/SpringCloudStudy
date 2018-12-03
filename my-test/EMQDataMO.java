import lombok.Data;

/**
 * @Auther: jorden
 * @Date: 2018/11/1 14:39
 * @Description:
 */
@Data
public class EMQDataMO {
    /**
     * 流水号
     */
    private Long serial;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 设备ID（网关设备）
     */
    private String deviceId;
    /**
     * Json数据
     */
    private Object data;
    /**
     * 0：表示不需要默认返回，1：表示需要默认返回
     */
    private Integer defaultResponse;
    /**
     * 时间戳
     */
    private Long timestamp;
}
