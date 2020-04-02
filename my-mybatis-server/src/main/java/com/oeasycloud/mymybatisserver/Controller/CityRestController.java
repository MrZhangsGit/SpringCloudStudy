package com.oeasycloud.mymybatisserver.Controller;

import com.alibaba.fastjson.JSON;
import com.oeasycloud.mymybatisserver.dao.CityDao;
import com.oeasycloud.mymybatisserver.domain.City;
import com.oeasycloud.mymybatisserver.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CityRestController {
    @Autowired
    private CityService cityService;
    @Autowired
    private CityDao cityDao;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findByName(@RequestParam(value = "cityName") String cityName) throws Exception {
        return cityService.findByName(cityName);
    }

    @RequestMapping(value = "/api/saveCity", method = RequestMethod.GET)
    public Integer saveCity(
            @RequestParam(value = "provinceId") String provinceId,
            @RequestParam(value = "cityName") String cityName,
            @RequestParam(value = "description") String description) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("provinceId", provinceId);
        params.put("cityName", cityName);
        params.put("description", description);
        return cityService.saveCity(params);
    }

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public String test(@RequestParam(value = "id") String id) throws Exception {
        this.transactionToThread(id);
        return "test";
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void transactionToThread(String id) throws Exception {
        /**
         * 验证：
         * 1.事务中，先修改后查询是否查询的是修改后的数据
         * 结论：同一事物中，先修改后查询的是更新后的数据
         * 2.事务是否传递进线程
         * 结论：事务无法传递进新线程中
         */
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("description", "现居地-深圳");
        cityDao.updateById(params);

        City city = cityDao.findByName("深圳");
        System.out.println("1：" + JSON.toJSONString(city));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> params = new HashMap<>();
                params.put("id", id);
                params.put("description", "现居地-深圳-Thread");
                cityDao.updateById(params);
            }
        });
        thread.start();
        thread.join();

        throw new Exception("Error!");
    }

    @Transactional
    public void delById(String id) throws Exception {
        cityDao.del(id);
        int i = 1/0;
    }
}
