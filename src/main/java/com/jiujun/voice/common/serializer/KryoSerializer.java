package com.jiujun.voice.common.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.jiujun.voice.common.logger.util.LogUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class KryoSerializer{

	// 每个线程的 Kryo 实例
	private static final ThreadLocal<Kryo> 	KRYO_LOCAL = new ThreadLocal<Kryo>() {
		@Override
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();

			/**
			 * 不要轻易改变这里的配置！更改之后，序列化的格式就会发生变化， 上线的同时就必须清除 Redis 里的所有缓存， 否则那些缓存再回来反序列化的时候，就会报错
			 */
			// 支持对象循环引用（否则会栈溢出）
			kryo.setReferences(true); 
			// 不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
			kryo.setRegistrationRequired(false);
			// Fix the NPE bug when deserializing Collections.
			((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
					.setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
			return kryo;
		}
	};

	/**
	 * 获得当前线程的 Kryo 实例
	 *
	 * @return 当前线程的 Kryo 实例
	 */
	public static Kryo getInstance() {
		return KRYO_LOCAL.get();
	}

	/**
	 * 将对象【及类型】序列化为字节数组
	 *
	 * @param obj 任意对象
	 * @param     <T> 对象的类型
	 * @return 序列化后的字节数组
	 */
	public static <T> byte[] serialize(T obj) {
		ByteArrayOutputStream byteArrayOutputStream = null;
		Output output = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			output = new Output(byteArrayOutputStream);
			Kryo kryo = getInstance();
			kryo.writeClassAndObject(output, obj);
			output.flush();
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			LogUtil.logger.error("###kryo-序列化失败!");
		} finally {
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
				}
			}
			if (output != null) {
				output.close();
			}
		}
		return null;
	}

	/**
	 * 将字节数组反序列化为原对象
	 *
	 * @param byteArray writeToByteArray 方法序列化后的字节数组
	 * @param           <T> 原对象的类型
	 * @return 原对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unSerialize(byte[] byteArray) throws Exception {
		ByteArrayInputStream byteArrayInputStream = null;
		Input input = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(byteArray);
			input = new Input(byteArrayInputStream);
			Kryo kryo = getInstance();
			return (T) kryo.readClassAndObject(input);
		} catch (Exception e) {
			LogUtil.logger.error("###kryo-反序列化失败!" + e, e);
			throw e;
		} finally {
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
				} catch (IOException e) {
				}
			}
			if (input != null) {
				input.close();
			}
		}
	}
}
