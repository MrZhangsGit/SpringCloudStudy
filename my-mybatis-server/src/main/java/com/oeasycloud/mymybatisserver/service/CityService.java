package com.oeasycloud.mymybatisserver.service;

import com.oeasycloud.mymybatisserver.domain.City;

import java.util.Map;

public interface CityService {
    /**
     * 根据城市名称查询城市具体信息
     * @param cityName
     * @return
     */
    City findByName(String cityName);

    /**
     * 存储城市信息
     * @param params
     * @return
     */
    Integer saveCity(Map<String, Object> params);
}
