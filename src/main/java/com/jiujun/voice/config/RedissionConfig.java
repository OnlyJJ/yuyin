package com.jiujun.voice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置
 * 
 * @author Shao.x
 * @date 2018年11月23日
 */
@Configuration
public class RedissionConfig {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private String port;

	@Value("${spring.redis.password}")
	private String password;

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);
		return Redisson.create(config);
	}
}
