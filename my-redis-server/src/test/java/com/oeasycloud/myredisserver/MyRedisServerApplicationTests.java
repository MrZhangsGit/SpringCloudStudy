package com.oeasycloud.myredisserver;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyRedisServerApplicationTests {
	@Resource
	StringRedisTemplate stringRedisTemplate;

	/**
	 * stringRedisTemplate.getExpire("test")//根据key获取过期时间
	 *
	 * stringRedisTemplate.getExpire("test",TimeUnit.SECONDS)//根据key获取过期时间并换算成指定单位
	 *
	 * stringRedisTemplate.expire("red_123",1000 , TimeUnit.MILLISECONDS);//设置过期时间
	 *
	 * stringRedisTemplate.hasKey("546545");//检查key是否存在，返回boolean值
	 *
	 * stringRedisTemplate.opsForSet().add("red_123", "1","2","3");//向指定key中存放set集合
	 *
	 * stringRedisTemplate.opsForSet().isMember("red_123", "1")//根据key查看集合中是否存在指定数据
	 *
	 * stringRedisTemplate.opsForSet().members("red_123");//根据key获取set集合
	 */

	@Test
	public void contextLoads() {
	}

	/**
	 * Redis Value
	 */
	@Test
	public void valueAddRedis() {
		stringRedisTemplate.opsForValue().set("key", "value");
	}

	@Test
	public void valueGetRedis() {
		System.out.println(stringRedisTemplate.opsForValue().get("key"));
	}

	@Test
	public void valueTimeoutRedis() {
		stringRedisTemplate.opsForValue().set("timeStep", JSON.toJSONString(new Date().getTime()), 10, TimeUnit.SECONDS);
	}

	@Test
	public void valueDelRedis() {
		stringRedisTemplate.delete("key");
	}

	/**
	 * Redis List
	 * list数据类型适合于消息队列的场景,即在高并发的场景可用
	 */
	@Test
	public void listPushRedis() {
		//右侧插入(队尾插入)
		stringRedisTemplate.opsForList().rightPush("list", "1");
		stringRedisTemplate.opsForList().rightPush("list", "2");
		stringRedisTemplate.opsForList().rightPush("list", "A");
		stringRedisTemplate.opsForList().rightPush("list", "B");
		stringRedisTemplate.opsForList().rightPush("list", "3");
		stringRedisTemplate.opsForList().rightPush("list", "4");
		stringRedisTemplate.opsForList().rightPush("list", "A");
		stringRedisTemplate.opsForList().rightPush("list", "B");

		//左侧插入(队首插入)
		stringRedisTemplate.opsForList().leftPush("list", "a");
	}

	@Test
	public void listGetRedis() {
		//查询所有
		List<String> listAll = stringRedisTemplate.opsForList().range("list", 0, -1);
		System.out.println(JSON.toJSONString(listAll));

		//查询前三个
		List<String> list = stringRedisTemplate.opsForList().range("list", 0, 3);
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void listRemoveOneRedis() {
		//删除队列中前一个A元素
		stringRedisTemplate.opsForList().remove("list", 1, "A");
	}

	@Test
	public void listRemoveTwoRedis() {
		//删除队列中前2个A元素
		stringRedisTemplate.opsForList().remove("list", 2, "A");
	}

	@Test
	public void listRemoveAllRedis() {
		//删除所有的A元素
		stringRedisTemplate.opsForList().remove("list", 0, "B");
	}

	/**
	 * Redis Hash
	 */
	@Test
	public void hashPutRedis() {
		stringRedisTemplate.opsForHash().put("map", "mapKey1", "mapValue1");
		stringRedisTemplate.opsForHash().put("map", "mapKey2", "mapValue2");
		stringRedisTemplate.opsForHash().put("map", "mapKey1", "mapValue1");
	}

	@Test
	public void hashGetEntriesRedis() {
		Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("map");
		System.out.println(JSON.toJSONString(map));
	}

	@Test
	public void hashGetKeysRedis() {
		Set<Object> keySet = stringRedisTemplate.opsForHash().keys("map");
		System.out.println(JSON.toJSONString(keySet));
	}

	@Test
	public void hashGetValueListRedis() {
		List<Object> values = stringRedisTemplate.opsForHash().values("map");
		System.out.println(JSON.toJSONString(values));
	}

	@Test
	public void hashSize() {
		System.out.println(stringRedisTemplate.opsForHash().size("map"));
	}
}
