package com.oeasycloud.mymybatisserver.service.impl;

import com.oeasycloud.mymybatisserver.dao.CityDao;
import com.oeasycloud.mymybatisserver.domain.City;
import com.oeasycloud.mymybatisserver.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CityServiceImpl implements CityService{
    @Autowired
    private CityDao cityDao;

    @Override
    public City findByName(String cityName) {
        return cityDao.findByName(cityName);
    }

    @Override
    public Integer saveCity(Map<String, Object> params) {
        return cityDao.saveCity(params);
    }
}
