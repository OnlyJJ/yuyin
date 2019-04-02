package com.jiujun.voice.modules.redis.suber;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.exception.VoiceException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.model.RootModel;
import com.jiujun.voice.common.threadpool.SysThreadHandle;
import com.jiujun.voice.modules.caller.iface.VoiceCaller;
import com.jiujun.voice.modules.redis.entity.RedisSuberEntity;

/**
 * 
 * @author Coody
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRedisSuber<T extends RootModel> implements VoiceCaller<T>, InitializingBean {

	@Override
	public abstract void execute(T entity);

	private static Map<String, AbstractRedisSuber<RootModel>> callers = new HashMap<String, AbstractRedisSuber<RootModel>>();

	
	public static void dispat(RedisSuberEntity entity){
		SysThreadHandle.THREAD_POOL.execute(new Runnable() {
			
			@Override
			public void run() {
				AbstractRedisSuber<RootModel> suber=callers.get(entity.getAction());
				if(suber==null){
					throw new VoiceException("redis订阅>>该指令不存在:"+entity.getAction());
				}
				Type paramType= suber.getClass().getGenericSuperclass();
				ParameterizedType pt=(ParameterizedType)paramType;  
				Class<RootModel> clazz=(Class<RootModel>) pt.getActualTypeArguments()[0];
				RootModel param=(RootModel) JSON.parseObject(entity.getData(), clazz);
				suber.execute(param);
			}
		});
		
		
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		LogUtil.logger.debug("加入调度器>>" + this.getClass().getName());
		if(callers.containsKey(this.getClass().getSimpleName())){
			throw new VoiceException("redis订阅>>该调度器已存在:"+this.getClass().getSimpleName());
		}
		callers.put(this.getClass().getSimpleName(), (AbstractRedisSuber<RootModel>) this);
	}
	
	
	public static void main(String[] args) throws Exception {
	}
}
