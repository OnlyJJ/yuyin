package com.jiujun.voice.common.cmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.container.BeanContainer;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.model.CmdModel;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.UnsafeUtil;
import com.jiujun.voice.common.verification.VerficationHandle;

/**
 * 后端接口路由最高超类，包括参数解析、接口分发、参数校验等。
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class RootCmd implements InitializingBean {

	private RootCmd instance = null;

	protected static final String ENCODE = "UTF-8";

	/**
	 * 接口容器
	 */
	public final Map<String, Method> ACTION_CONTAINER = new HashMap<String, Method>();

	public Method getAPIMethod(String action) {
		return ACTION_CONTAINER.get(action);
	}

	public Map<String, Method> getAPIMethods() {
		return ACTION_CONTAINER;
	}

	public BaseRespVO execute(Header header, String body) {
		if (header == null || header.getCmd() == null || header.getAction() == null) {
			return ErrorCode.ERROR_1002.builderResponse();
		}
		BaseRespVO result = doAction(header, body);
		return result;
	}

	/**
	 * 过滤特殊字符
	 */
	@SuppressWarnings("serial")
	Map<String, String> filterMap = new HashMap<String, String>() {
		{
			put("<", "＜");
			put(">", "＞");
			put("\\'", "＇");
			put("\\\"", "＂");
		}
	};

	public BaseRespVO doAction(Header header, String json) {
		Method method = instance.getAPIMethod(header.getAction());
		if (method == null) {
			return ErrorCode.ERROR_1002.builderResponse();
		}
		try {
			Class<?>[] clazzs = method.getParameterTypes();
			if (clazzs == null) {
				Object[] paraTemp = {};
				return (BaseRespVO) method.invoke(instance, paraTemp);
			}
			Class<?> paraClazz = clazzs[0];
			if (!CmdModel.class.isAssignableFrom(paraClazz)) {
				// 无装载对象
				Object para = null;
				return (BaseRespVO) method.invoke(instance, para);
			}
			BaseReqVO req = null;
			if (!StringUtil.isNullOrEmpty(json)) {
				// 装载参数
				for (String filterFlag : filterMap.keySet()) {
					if (!json.contains(filterFlag)) {
						continue;
					}
					LogUtil.logger.error(
							"接口存在特殊字符:" + header.getCmd() + "." + header.getAction() + ">>" + filterFlag + ":" + json);
					json = json.replace(filterFlag, filterMap.get(filterFlag));
				}
				req = (BaseReqVO) JSON.parseObject(json, paraClazz);
			}
			if (req == null) {
				// req = (BaseReqVO) paraClazz.newInstance();
				req = UnsafeUtil.createInstance(paraClazz);
			}
			req.setHeader(header);
			Object[] paras = new Object[1];
			VerficationHandle.doVerfication(req);
			paras[0] = req;
			// 方法执行
			return (BaseRespVO) method.invoke(instance, paras);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof CmdException) {
				CmdException cmdException = (CmdException) e.getTargetException();
				if (cmdException.getCode() != ErrorCode.ERROR_1011.getCode()) {
					PrintException.printException(cmdException);
				}
				return new BaseRespVO().pushErrorCode(cmdException.getCode(), cmdException.getMsg(),
						cmdException.getError());
			}
			PrintException.printException(e.getTargetException());
			return ErrorCode.ERROR_1004.builderResponse();
		} catch (CmdException e) {
			if (e.getCode() != ErrorCode.ERROR_1011.getCode()) {
				PrintException.printException(e);
			}
			PrintException.printException(e);
			return new BaseRespVO().pushErrorCode(e.getCode(), e.getMsg(), e.getError());
		} catch (Exception e) {
			PrintException.printException(e);
			return ErrorCode.ERROR_1004.builderResponse();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Method[] methods = this.getClass().getDeclaredMethods();
		CmdOpen cmdOpen = this.getClass().getAnnotation(CmdOpen.class);
		for (Method method : methods) {
			CmdAction cmdAction = method.getAnnotation(CmdAction.class);
			if (StringUtil.isNullOrEmpty(cmdAction)) {
				continue;
			}
			if (method.getParameterTypes().length > 0) {
				if (method.getParameterTypes().length > 1) {
					throw ErrorCode.ERROR_1003.builderException();
				}
				if (!BaseReqVO.class.isAssignableFrom(method.getParameterTypes()[0])) {
					throw ErrorCode.ERROR_1003.builderException();
				}
			}
			String action = cmdAction.value();
			if (StringUtil.isNullOrEmpty(action)) {
				action = method.getName();
			}
			method.setAccessible(true);
			LogUtil.logger.info("初始化接口:" + cmdOpen.value() + "." + action);
			ACTION_CONTAINER.put(action, method);
		}
		this.instance = BeanContainer.getBean(this.getClass());
	}
}
