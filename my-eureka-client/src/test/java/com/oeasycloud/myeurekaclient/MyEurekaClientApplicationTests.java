package com.oeasycloud.myeurekaclient;

import com.alibaba.fastjson.JSON;
import com.oeasycloud.myeurekaclient.config.MapConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({MapConfig.class})
public class MyEurekaClientApplicationTests {

	@Autowired
	private MapConfig mapConfig;

	@Test
	public void contextLoads() {
		Map<String, Integer> map = mapConfig.getLimitSizeMap();
		System.out.println(mapConfig.getA());
		System.out.println(JSON.toJSONString(map));
	}
}
