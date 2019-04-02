package com.jiujun.voice.modules.apps.room.cmd;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.room.cmd.schema.MemberInfoSchema;
import com.jiujun.voice.modules.apps.room.cmd.schema.MicInfoSchema;
import com.jiujun.voice.modules.apps.room.cmd.vo.EditThemeReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.MemberInfoReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.MemberInfoRespVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.MicRespVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.ModifyRoomInfoReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.NewRoomReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.OnlineUserReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.OnlineUserRespVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.RoomBaseReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.RoomInfoRespVO;
import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomThemeRecord;
import com.jiujun.voice.modules.apps.room.service.RoomEnjoyTypeService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomThemeRecordService;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationRecord;
import com.jiujun.voice.modules.apps.user.relation.service.RelationService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
/**
 * 
 * @author Coody
 *
 */
@CmdOpen("roomInfo")
@DocFlag("房间信息")
public class RoomInfoCmd extends RootCmd {
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	RoomThemeRecordService roomThemeRecordService;
	@Resource
	RoomMicInfoService roomMicInfoService;
	@Resource
	RoomEnjoyTypeService roomEnjoyTypeService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	RelationService relationService;
	
	
	
	@CmdAction("new")
	@DocFlag("创建房间")
	public BaseRespVO createRoom(NewRoomReqVO vo) {
		RoomInfo info = new RoomInfo();
		BeanUtils.copyProperties(vo, info);
		info.setUserId(vo.getHeader().getUserId());
		roomInfoService.createRoom(info);
		return new BaseRespVO();
	} 
	
	@CmdAction("modify")
	@DocFlag("修改房间信息")
	public BaseRespVO modifyRoomInfo(ModifyRoomInfoReqVO vo) {
		RoomInfo info = new RoomInfo();
		BeanUtils.copyProperties(vo, info);
		info.setUserId(vo.getHeader().getUserId());
		roomInfoService.modifyRoomInfo(info);
		return new BaseRespVO();
	} 
	
	@CmdAction("editTheme")
	@DocFlag("编辑话题")
	public BaseRespVO modifyTheme(EditThemeReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		String theme = vo.getTheme();
		roomThemeRecordService.saveTheme(userId, roomId, theme);
		return new BaseRespVO();
	} 
	
	@LoginIgnore
	@CmdAction("info")
	@DocFlag("获取房间基本信息")
	public RoomInfoRespVO loadRoomInfo(RoomBaseReqVO vo) {
		String roomId = vo.getRoomId();
		// 基本信息
		RoomInfo info = roomInfoService.getRoomInfo(roomId);
		if(info == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		UserSchema user = userInfoService.getUserSchema(info.getUserId());
		if(user == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		RoomInfoRespVO resp= new RoomInfoRespVO();
		BeanUtils.copyProperties(info, resp);
		resp.setIcon(user.getCompleHead());
		
		// 房间话题
		RoomThemeRecord theme = roomThemeRecordService.getValidTheme(roomId);
		if(theme != null) {
			resp.setTheme(theme.getTheme());
		}
		//屏蔽房间密码
		if(!vo.getHeader().getUserId().equals(info.getUserId())){
			resp.setPassword(null);
		}
		// 房间偏好
		String enjoys = info.getEnjoyType();
		if(!StringUtil.isNullOrEmpty(enjoys)) {
			List<RoomEnjoyType> data = roomEnjoyTypeService.listRoomEnjoyType(enjoys);
			if(data != null) {
				resp.setData(data);
			}
		}
		return resp;
	} 
	
	@CmdAction("memberInfo")
	@DocFlag("房间内成员个人信息")
	public MemberInfoRespVO getRoomMemberInfo(MemberInfoReqVO vo) {
		String userId = vo.getTargetUserId();
		String roomId = vo.getRoomId();
		MemberInfoSchema memberDTO = roomInfoService.getMemberInfo(roomId, userId);
		UserSchema user = userInfoService.getUserSchema(userId);
		if(user == null) {
			throw new CmdException(ErrorCode.ERROR_1022);
		}
		MemberInfoRespVO retVO = new MemberInfoRespVO();
		BeanUtils.copyProperties(memberDTO, retVO);
		if (!userId.equals(vo.getHeader().getUserId())) {
			retVO.setRelation(0);
			RelationRecord relation=relationService.getRelation(vo.getHeader().getUserId(), vo.getTargetUserId());
			if(relation!=null){
				retVO.setRelation(1);
			}
		}
		retVO.setRelation(-1);
		retVO.setSex(user.getSex());
		retVO.setAge(user.getAge());
		retVO.setUserId(userId);
		retVO.setRelation(user.getRelation());
		retVO.setSign(user.getSign());
		return retVO;
	}
	
	@CmdAction("members")
	@DocFlag("房间在线成员列表")
	public OnlineUserRespVO getRoomMembers(OnlineUserReqVO vo) {
		String roomId = vo.getRoomId();
		int pageSize = vo.getPageSize();
		int pageNo = vo.getPageNo();
		Pager pager = roomInfoService.listMembers(roomId, pageSize, pageNo);
		return new OnlineUserRespVO().fromPager(pager);
	}
	
	@CmdAction("mic")
	@DocFlag("房间麦位信息")
	public MicRespVO getRoomMics(RoomBaseReqVO vo) {
		String roomId = vo.getRoomId();
		// 基本信息
		RoomInfo info = roomInfoService.getRoomInfo(roomId);
		if(info == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		MicRespVO resp= new MicRespVO();
		List<MicInfoSchema> list = roomMicInfoService.listRoomMicInfo(roomId);
		if(!StringUtil.isAllNull(list)) {
			resp.setData(list);
		}
		return resp;
	}
	
}
