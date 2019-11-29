package my.datasource.controller;

import com.alibaba.fastjson.JSON;
import my.datasource.dao.master.DeviceMasterMapper;
import my.datasource.dao.slave.DeviceSlaveMapper;
import my.datasource.dao.third.DeviceThirdMapper;
import my.datasource.po.DeviceInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/11/19
 */
@RestController
public class TestController {
    @Autowired
    private DeviceMasterMapper deviceMasterMapper;

    @Autowired
    private DeviceSlaveMapper deviceSlaveMapper;
//
//    @Autowired
//    private DeviceThirdMapper deviceThirdMapper;

    @RequestMapping("/hi")
    public String home() {
        DeviceInfoPO deviceInfo1 = deviceMasterMapper.getOne("2464c4ded0e44d7c6d0be48d359d45a4");
        System.out.println("Master:" + JSON.toJSONString(deviceInfo1));

        DeviceInfoPO deviceInfo2 = deviceSlaveMapper.getOne("8d7a2f0dad0549f886fffdddb2f5c3f81543286477605576005");
        System.out.println("Slave:" + JSON.toJSONString(deviceInfo2));

//        DeviceInfoPO deviceInfo3 = deviceThirdMapper.getOne("2464c4ded0e44d7c2dea0034aba243df");
//        System.out.println("Third:" + JSON.toJSONString(deviceInfo3));

        return "hello ";

    }
}
