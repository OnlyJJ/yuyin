package com.jiujun.voice;

import java.lang.reflect.Modifier;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ClassUtils;

import com.jiujun.voice.common.jdbc.factory.BeanDataBillerFactory;
import com.jiujun.voice.common.threadpool.SysThreadHandle;
import com.jiujun.voice.common.utils.ClassScaner;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.property.PropertUtil;

/**
 * 程序入口
 * 
 * @author Coody
 *
 */
@SpringBootApplication
@EnableScheduling
public class VoiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		// 开启字节码引擎对容器进行加速
		accelerateEngine(VoiceApplication.class);
		SpringApplication.run(VoiceApplication.class, args);
	}

	protected static void accelerateEngine(Class<?> initialingClazz) {
		SysThreadHandle.THREAD_POOL.execute(new Runnable() {
			@Override
			public void run() {
				String packager = ClassUtils.getPackageName(initialingClazz);
				Set<Class<?>> initialingClazzs = ClassScaner.scan(packager);
				// 对全包下的Class进行预加载
				try {
					for (Class<?> clazz : initialingClazzs) {
						if (Modifier.isAbstract(clazz.getModifiers())) {
							continue;
						}
						if (Modifier.isInterface(clazz.getModifiers())) {
							continue;
						}
						PropertUtil.getBeanFields(clazz);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 对全包下的数据模型进行预加载
				try {
					BeanDataBillerFactory.accelerateEngine(initialingClazzs);
				} catch (Exception e) {
					PrintException.printException(e);
				}
			}
		});
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VoiceApplication.class);
	}

}
