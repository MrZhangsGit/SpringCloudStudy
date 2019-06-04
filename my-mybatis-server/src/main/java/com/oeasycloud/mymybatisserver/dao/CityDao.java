package com.oeasycloud.mymybatisserver.dao;

import com.oeasycloud.mymybatisserver.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface CityDao {
    /**
     * 根据城市名称查询城市具体信息
     * @param cityName
     * @return
     */
    City findByName(@Param("cityName") String cityName);

    /**
     * 存储城市信息
     * @param params
     * @return
     */
    Integer saveCity(Map<String, Object> params);

    Integer del(@Param("id") String id);
}
