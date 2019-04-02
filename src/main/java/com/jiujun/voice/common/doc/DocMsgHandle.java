package com.jiujun.voice.common.doc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.doc.entity.ActionDocument;
import com.jiujun.voice.common.doc.entity.ActionMsgDocument;
import com.jiujun.voice.common.doc.entity.CmdDocument;
import com.jiujun.voice.common.model.RootModel;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.common.utils.property.entity.FieldEntity;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.UserInfoCmd;

/**
 * 
 * @author Coody
 *
 */
@Component
public class DocMsgHandle {

	@CacheWrite(key = CacheConstants.DOC_INPUT, time = 7200000, fields = "action.fingerprint")
	public ActionMsgDocument buildMsg(ActionDocument action) {
		try {
			Method method = action.getMethod();
			BaseReqVO req = (BaseReqVO) method.getParameterTypes()[0].newInstance();
			Map<String, Object> inputMap = new HashMap<String, Object>(2);
			inputMap.put("header", initDocValue(new Header()));
			RootModel body=initDocValue(req);
			inputMap.put("body",body );
			String msg = JSON.toJSONString(inputMap, SerializerFeature.PrettyFormat);
			ActionMsgDocument actionMsg = new ActionMsgDocument();
			actionMsg.setFingerprint(action.getFingerprint());
			actionMsg.setMsg(msg);
			return actionMsg;
		} catch (Exception e) {
			PrintException.printException(e);
		}
		return null;
	}

	private RootModel initDocValue(RootModel model) throws InstantiationException, IllegalAccessException {
		if(model==null){
			return null;
		}
		model.initModel();
		List<FieldEntity> fields = PropertUtil.getBeanFields(model);
		for (FieldEntity field : fields) {
			try {
				if (RootModel.class.isAssignableFrom(field.getFieldType())) {
					RootModel fieldValue =field.getFieldValue();
					if(fieldValue==null){
						fieldValue=(RootModel) field.getFieldType().newInstance(); 
					}
					fieldValue = initDocValue(field.getFieldValue());
					PropertUtil.setProperties(model, field.getFieldName(), fieldValue);
					continue;
				}
				DocFlag docFlag = field.getAnnotation(DocFlag.class);
				if (docFlag == null) {
					continue;
				}
				String fieldValue=docFlag.docFieldValue();
				if(StringUtil.isNullOrEmpty(fieldValue)){
					continue;
				}
				PropertUtil.setProperties(model, field.getFieldName(), fieldValue);
			} catch (IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	@CacheWrite(key = CacheConstants.DOC_INPUT, time = 7200000, fields = "msg.fingerprint", read = false)
	public ActionMsgDocument setInputMsg(ActionMsgDocument msg) {
		return msg;
	}

	@CacheWrite(key = CacheConstants.DOC_OUTPUT, time = 7200000, fields = "action.fingerprint")
	public ActionMsgDocument getOutputMsg(ActionDocument action) {
		return null;
	}

	@CacheWrite(key = CacheConstants.DOC_OUTPUT, time = 7200000, fields = "msg.fingerprint", read = false)
	public ActionMsgDocument setOutputMsg(ActionMsgDocument msg) {
		return msg;
	}

	public static void main(String[] args) throws Exception {
		UserInfoCmd cmd = new UserInfoCmd();
		cmd.afterPropertiesSet();
		CmdDocument cmdDocument = new DocHandle().builderCmdDocument(cmd);

		for (ActionDocument action : cmdDocument.getActions()) {
			new DocMsgHandle().buildMsg(action);
		}
	}

}
