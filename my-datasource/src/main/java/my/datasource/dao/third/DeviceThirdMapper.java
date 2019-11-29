package my.datasource.dao.third;

import my.datasource.dao.BaseMapper;
import my.datasource.po.DeviceInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/11/19
 */
@Mapper
@Repository
public interface DeviceThirdMapper extends BaseMapper<DeviceInfoPO> {
    @Select("SELECT * FROM device_info WHERE device_id = #{deviceId}")
    @Results(id = "device_info", value = {
            @Result(property = "deviceId", column = "device_id"),
            @Result(property = "deviceType", column = "device_type"),
            @Result(property = "deviceName", column = "device_name")
    })
    DeviceInfoPO getOne(String deviceId);
}
