package com.jiujun.voice.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ConcurrentReferenceHashMap;

import com.jiujun.voice.common.jdbc.source.DynamicDataSource;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author Coody
 *
 */
@Configuration
public class DataSourceConfig {

	private static final String PREFIX = "spring.datasource";
	@Bean(name="dynamicDataSource")
	@Primary
	public DynamicDataSource dynamicDataSources(ConfigurablePropertyResolver environment) throws SQLException, InterruptedException {
		// 抽取配置容器
		PropertySources propertySources = PropertUtil.getFieldValue(environment, "propertySources");
		List<PropertySource<?>> list = PropertUtil.getFieldValue(propertySources, "propertySourceList");
		PropertySource<?> propertySource = PropertUtil.getByList(list, "name", "configurationProperties");
		ConcurrentReferenceHashMap<PropertySource<?>, ConfigurationPropertySource> propertySourceMap = PropertUtil
				.getFieldValue(propertySource.getSource(), "cache");
		// 筛选yml配置
		List<ConfigurationPropertySource> ymlPropertys = new ArrayList<ConfigurationPropertySource>();
		for (PropertySource<?> key : propertySourceMap.keySet()) {
			if (StringUtil.isNullOrEmpty(propertySourceMap.get(key))) {
				continue;
			}

			ConfigurationPropertySource value = propertySourceMap.get(key);
			if (key.getName().startsWith("applicationConfig:")) {
				ymlPropertys.add(value);
			}
		}
		Map<String, Object> propertyMap = new HashMap<String, Object>();
		// 合并配置
		for (ConfigurationPropertySource source : ymlPropertys) {
			MapPropertySource underlyingSource = (MapPropertySource) source.getUnderlyingSource();
			for (String propertyName : underlyingSource.getPropertyNames()) {
				propertyMap.put(propertyName, underlyingSource.getProperty(propertyName));
			}
		}
		// 整合数据源配置
		Map<String, Properties> dataSourceConfigs = new HashMap<String, Properties>();
		for (String propertyName : propertyMap.keySet()) {
			if (!propertyName.startsWith(PREFIX)) {
				continue;
			}
			String dataSourceName = getDatasourceNameByPropertyName(propertyName);
			String fieldName = getFieldNameByPropertyName(propertyName);
			Object fieldValue = propertyMap.get(propertyName);
			if (!dataSourceConfigs.containsKey(dataSourceName)) {
				Properties properties = new Properties();
				dataSourceConfigs.put(dataSourceName, properties);
			}
			dataSourceConfigs.get(dataSourceName).put(fieldName, fieldValue);
		}
		
		// 创建数据源
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		for (String dataSourceName : dataSourceConfigs.keySet()) {
			Properties properties = dataSourceConfigs.get(dataSourceName);
			HikariConfig configuration = new HikariConfig(properties);
			HikariDataSource dataSource=new HikariDataSource(configuration);
			targetDataSources.put(dataSourceName, dataSource);
		}
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.afterPropertiesSet();
		return dynamicDataSource;
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate instanceJdbcTemplate(DynamicDataSource dynamicDataSource) {
		return new JdbcTemplate(dynamicDataSource);
	}
	@Bean
	public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}
	private static String getFieldNameByPropertyName(String propertyName) {
		propertyName = propertyName.substring(PREFIX.length() + 1);
		String datasourceName = propertyName.substring(0, propertyName.indexOf("."));
		String fieldName = propertyName.substring(datasourceName.length() + 1);
		return fieldName;
	}

	private static String getDatasourceNameByPropertyName(String propertyName) {
		propertyName = propertyName.substring(PREFIX.length() + 1);
		String datasourceName = propertyName.substring(0, propertyName.indexOf("."));
		return datasourceName;
	}

}
