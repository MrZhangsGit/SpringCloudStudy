package com.oeasycloud.myredisserver.service.impl;

import com.oeasycloud.myredisserver.domain.City;
import com.oeasycloud.myredisserver.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService{
    private static final Logger Logger = LoggerFactory.getLogger(CityServiceImpl.class);
    @Override
    public City findCityById(Long id) {
        return null;
    }

    @Override
    public Long saveCity(City city) {
        return null;
    }

    @Override
    public Long updateCity(City city) {
        return null;
    }

    @Override
    public Long deleteCity(Long id) {
        return null;
    }
}
