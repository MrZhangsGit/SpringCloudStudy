package my.datasource.po;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * <desc>
 *      设备信息实体类。
 * </desc>
 * @createDate 2018/4/4
 * @author zhangs
 */
@Table(name = "device_info")
public class DeviceInfoPO {

    /**
     * 设备唯一码
     */
    @Column(name = "device_id")
    private String deviceId;

    /**
     * 设备原始sn码
     */
    @Column(name = "sn")
    private String sn;

    /**
     * 是否为直连设备(0:否|1:是)
     */
    @Column(name = "is_direct")
    private Integer isDirect;

    /**
     * 设备产品序列编码(平台的设备sn码，由厂商标识+设备原始sn码组成)
     */
    @Column(name = "device_sn")
    private String deviceSn;

    /**
     * 厂商标识
     */
    @Column(name = "fac_sign")
    private String facSign;

    /**
     * 设备模型Id
     */
    @Column(name = "product_key")
    private String productKey;

    /**
     * 设备类型(对应设备建模信息表中的productCode)
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设备名称
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 设备的mac
     */
    @Column(name = "mac")
    private String mac;

    /**
     * ip
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 端口号
     */
    @Column(name = "port_num")
    private String portNum;

    /**
     * 设备描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 产品版本
     */
    @Column(name = "product_version")
    private String productVersion;

    /**
     * 固件版本
     * 厂商Id(3位数字)+协议类型(1位字母)+设备类型(3位数字)+版本信息(2位数字，00:修复bug|01:强制升级|02:Beta测试版|)+-(横杠)+设备原有固件版本
     */
    @Column(name = "firmware_version")
    private String firmwareVersion;

    /**
     * 经度
     */
    @Column(name = "longitude")
    private String longitude;

    /**
     * 纬度
     */
    @Column(name = "latitude")
    private String latitude;

    /**
     * 海拔
     */
    @Column(name = "alititude")
    private String altitude;

    /**
     * 布防状态
     * (0:撤防|1:布防)
     */
    @Column(name = "deploy_status")
    private Integer deployStatus;

    /**
     * 设备在离线状态(true-在线|false-离线)
     */
    @Column(name = "online_state")
    private String onlineState;

    /**
     * 数据状态
     * 0 删除|1 正常
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    public DeviceInfoPO() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(Integer isDirect) {
        this.isDirect = isDirect;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getFacSign() {
        return facSign;
    }

    public void setFacSign(String facSign) {
        this.facSign = facSign;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPortNum() {
        return portNum;
    }

    public void setPortNum(String portNum) {
        this.portNum = portNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public Integer getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(Integer deployStatus) {
        this.deployStatus = deployStatus;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
