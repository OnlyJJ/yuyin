package com.jiujun.voice.common.jdbc.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.jiujun.voice.common.jdbc.exception.UnknownDataSourceException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 动态数据源
 * 
 * @author Coody
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


	private static final ThreadLocal<String> CURRENT_SOURCE = new ThreadLocal<String>();

	public static final String MASTER_FLAG = "master";

	public static final String SLAVE_FLAG = "slave";

	private static List<String> masters = new ArrayList<String>();

	private static List<String> slaves = new ArrayList<String>();

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		for (Object key : targetDataSources.keySet()) {
			String dataSourceName = key.toString();
			if (dataSourceName.startsWith(MASTER_FLAG)) {
				masters.add(dataSourceName);
				this.setDefaultTargetDataSource(targetDataSources.get(dataSourceName));
				continue;
			}
			if (dataSourceName.startsWith(SLAVE_FLAG)) {
				slaves.add(dataSourceName);
				continue;
			}
			throw new UnknownDataSourceException("未知的数据源类型，数据源命名只能以" + MASTER_FLAG + "、" + SLAVE_FLAG + "开头");
		}
		if(StringUtil.isNullOrEmpty(masters)){
			throw new UnknownDataSourceException("缺少主库数据源");
		}
		super.setTargetDataSources(targetDataSources);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceName= getDataSourceName();
		LogUtil.logger.debug("使用数据源>>"+dataSourceName);
		return dataSourceName;
	}


    public static void setDataSourceFlag(String sourceFlag) {
    	CURRENT_SOURCE.set(sourceFlag);
    }

    public static String getDataSourceName() {
    	String flag=CURRENT_SOURCE.get();
    	if(StringUtil.isNullOrEmpty(flag)){
    		return masters.get(0);
    	}
    	if(flag.equals(MASTER_FLAG)){
    		if(masters.size()==1){
    			return masters.get(0);
    		}
    		return masters.get(new Random().nextInt(masters.size()));
    	}
    	if(flag.equals(SLAVE_FLAG)){
    		if(slaves.size()==1){
    			return slaves.get(0);
    		}
    		return slaves.get(new Random().nextInt(slaves.size()));
    	}
    	LogUtil.logger.error("多数据源警告：指定数据源标记错误，只能指定master、slave相关数值");
    	return masters.get(0);
    }

    public static void clearDataSourceFlag() {
    	CURRENT_SOURCE.remove();
    }

}
