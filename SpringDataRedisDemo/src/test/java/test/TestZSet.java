package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class TestZSet {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void setValue(){
		redisTemplate.boundZSetOps("namezset").add("zs",1);
		redisTemplate.boundZSetOps("namezset").add("ls",2);
		redisTemplate.boundZSetOps("namezset").add("ww",3);
	}
	
	@Test
	public void getValue(){
		Set set = redisTemplate.boundZSetOps("namezset").range(0, 10);
		System.out.println(set);
	}
	
	@Test
	public void removeValue(){
		redisTemplate.boundZSetOps("namezset").removeRange(0,0);
		//redisTemplate.boundZSetOps("namezset").remove("zs");

	}
	
	@Test
	public void delete(){
		redisTemplate.delete("namezset");
	}

}
