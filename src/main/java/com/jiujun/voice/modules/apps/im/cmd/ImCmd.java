package com.jiujun.voice.modules.apps.im.cmd;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.filting.FiltingProcess;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.im.cmd.vo.GetTokenRespVO;
import com.jiujun.voice.modules.apps.im.cmd.vo.SendGeneralMsgReqVO;
import com.jiujun.voice.modules.apps.im.cmd.vo.SendRoomMsgReqVO;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;
import com.jiujun.voice.modules.im.rongcloud.handle.RongCloudHandle;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("im")
@DocFlag("消息中心")
public class ImCmd extends RootCmd {

	@Resource
	RongCloudHandle rongCloudHandle;
	
	@Resource
	IMessageHandle iMessageHandle;

	@Resource
	UserInfoService userInfoService;
	
	@Resource
	RoomMemberBehaviorService roomMemberBehaviorService;

	@CmdAction
	@DocFlag("获取Token")
	public GetTokenRespVO getToken(BaseReqVO req) {
		String userId = req.getHeader().getUserId();
		UserInfo userInfo = userInfoService.getUserInfo(userId);
		String token = rongCloudHandle.getToken(userId, userInfo.getName(), userInfo.getHead());
		GetTokenRespVO resp = new GetTokenRespVO();
		resp.setToken(token);
		resp.setUserId(userId);
		if(StringUtil.isNullOrEmpty(token)) {
			resp.pushErrorCode(ErrorCode.ERROR_1027);
		}
		return resp;
	}
	
	
	@CmdAction
	@DocFlag("发送私聊消息")
	public BaseRespVO sendGeneralMsg(SendGeneralMsgReqVO req){
		
		UserSchema sender=userInfoService.getUserSchema(req.getHeader().getUserId());
		UserSchema receiver=userInfoService.getUserSchema(req.getTargeUserId());
		req.setMsg(FiltingProcess.doFilting(req.getMsg(), "*"));
		iMessageHandle.sendSingleMsg(sender, receiver, req.getMsg(), MsgEnum.PRIVATE_MSG.getType(), true);
		return new BaseRespVO();
	}
	
	
	@CmdAction
	@DocFlag("发送房间消息")
	public BaseRespVO sendRoomMsg(SendRoomMsgReqVO req){
		if(!roomMemberBehaviorService.checkTalk(req.getRoomId(), req.getHeader().getUserId())){
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1045);
		}
		req.setMsg(FiltingProcess.doFilting(req.getMsg(), "*"));
		UserSchema sender=userInfoService.getUserSchema(req.getHeader().getUserId());
		iMessageHandle.sendRoomMsg(sender, req.getRoomId(), req.getMsg(), MsgEnum.USER_ROOM_MSG.getType());
		return new BaseRespVO();
	}
}
