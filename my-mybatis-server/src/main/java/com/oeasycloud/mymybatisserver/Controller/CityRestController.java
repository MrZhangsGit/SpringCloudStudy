package com.oeasycloud.mymybatisserver.Controller;

import com.oeasycloud.mymybatisserver.dao.CityDao;
import com.oeasycloud.mymybatisserver.domain.City;
import com.oeasycloud.mymybatisserver.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
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
        this.delById(id);
        return "test";
    }

    @Transactional
    public void delById(String id) throws Exception {
        cityDao.del(id);
        int i = 1/0;
    }
}
