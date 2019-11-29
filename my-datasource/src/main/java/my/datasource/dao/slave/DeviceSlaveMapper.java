package my.datasource.dao.slave;

import my.datasource.po.DeviceInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/11/19
 */
@Mapper
@Repository
public interface DeviceSlaveMapper {
    DeviceInfoPO getOne(String deviceId);
}
