package com.jiujun.voice.modules.apps.room.cmd;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.modules.apps.room.cmd.schema.MemberInfoSchema;
import com.jiujun.voice.modules.apps.room.cmd.vo.BehaviorBaseReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.CheckRespVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.FairyInfoRespVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.InRoomReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.ManagerReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.MemberInfoRespVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.MicManageReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.MicReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.RoomBaseReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.SendGiftReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.TalkReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.UserStayRoomRespVO;
import com.jiujun.voice.modules.apps.room.domain.FairyInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomMicInfo;
import com.jiujun.voice.modules.apps.room.service.FairyInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMicManageService;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.apps.room.service.SendGiftRecordService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.SendFromBackPackReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.useraccount.service.BackPackService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

/**
 * 房间动作，此服务提供的api大多需要权限校验
 * 
 * @author Shao.x
 * @date 2018年12月4日
 */
@CmdOpen("roomBehavior")
@DocFlag("房间行为中心")
public class RoomBehaviorCmd extends RootCmd {

	@Resource
	RoomRoleService roomRoleService;
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	RoomMemberBehaviorService roomMemberBehaviorService;
	@Resource
	RoomMicInfoService roomMicInfoService;
	@Resource
	SendGiftRecordService sendGiftRecordService;
	@Resource
	RoomMicManageService roomMicManageService;
	@Resource
	BackPackService backPackService;
	@Resource
	FairyInfoService fairyInfoService;

	@CmdAction()
	@DocFlag("进入房间前的校验")
	public CheckRespVO checkStatus(RoomBaseReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		int lockFlag = roomMemberBehaviorService.checkHasPwd(roomId);
		boolean isForce = roomMemberBehaviorService.checkInRoom(roomId, userId);
		int isForceOut = 0;
		if (!isForce) {
			isForceOut = 1;
		}
		int full = 0;
		boolean isFull = roomMemberBehaviorService.checkIsFull(roomId, userId);
		if (isFull) {
			full = 1;
		}
		CheckRespVO resp = new CheckRespVO();
		resp.setLockFlag(lockFlag);
		resp.setIsForceOut(isForceOut);
		resp.setIsFull(full);
		return resp;
	}

	@CmdAction()
	@DocFlag("校验房间密码")
	public BaseRespVO checkPwd(InRoomReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		String password = vo.getPassword();
		roomMemberBehaviorService.checkRoomPwd(roomId, userId, password);
		return new BaseRespVO();
	}

	@CmdAction()
	@DocFlag("获取用户所在房间")
	public UserStayRoomRespVO getUserInRoom(BaseReqVO vo) {
		String userId = vo.getHeader().getUserId();
		RoomInfo room = roomInfoService.getUserInRoom(userId);
		UserStayRoomRespVO resp = new UserStayRoomRespVO();
		if (room != null) {
			BeanUtils.copyProperties(room, resp);
			UserSchema user = userInfoService.getUserSchema(room.getUserId());
			if (user != null) {
				resp.setIcon(user.getCompleHead());
				resp.setName(user.getName());
			}
			// 是否上麦
			int micFlag = 0;
			int micStatus = 0;
			RoomMicInfo mic = roomMicInfoService.getRoomMicInfoByUser(userId);
			if (mic != null) {
				micFlag = 1;
				Integer status = roomMicManageService.getMicStatus(mic.getRoomId(), mic.getSeat());
				if (status != null) {
					micStatus = status;
				}
			}
			resp.setMicStatus(micStatus);
			resp.setMicFlag(micFlag);
		}
		return resp;
	}

	// 进入房间（权限校验）
	@CmdAction("inRoom")
	@DocFlag("进入房间")
	public MemberInfoRespVO inRoom(InRoomReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		String password = vo.getPassword();
		MemberInfoSchema memberDTO = roomMemberBehaviorService.inRoom(roomId, userId, password);
		MemberInfoRespVO retVO = new MemberInfoRespVO();
		BeanUtils.copyProperties(memberDTO, retVO);
		return retVO;
	}

	// 退出房间
	@CmdAction("outRoom")
	@DocFlag(" 退出房间")
	public BaseRespVO outRoom(RoomBaseReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		roomMemberBehaviorService.outRoom(roomId, userId);
		return new BaseRespVO();
	}

	// 上or下管理（房主设置管理）
	@CmdAction("manage")
	@DocFlag("上下管理员")
	public MemberInfoRespVO upAndDownManage(ManagerReqVO vo) {
		// 上下管理，都返回房间个人信息，以免刷新不及时
		String userId = vo.getHeader().getUserId();
		String toUserId = vo.getToUserId();
		String roomId = vo.getRoomId();
		int operate = vo.getOperate();
		roomRoleService.handleManage(userId, toUserId, roomId, operate);
		return new MemberInfoRespVO();
	}

	@CmdAction("talk")
	@DocFlag("禁言解禁")
	public MemberInfoRespVO manageTalk(TalkReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String toUserId = vo.getToUserId();
		String roomId = vo.getRoomId();
		int operate = vo.getOperate();
		roomMemberBehaviorService.manageTalk(roomId, userId, toUserId, operate);
		return new MemberInfoRespVO();
	}

	// 踢出房间，踢出房间后，1小时内不能再次进入
	@CmdAction()
	@DocFlag("踢出房间")
	public BaseRespVO forceOut(BehaviorBaseReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String toUserId = vo.getToUserId();
		String roomId = vo.getRoomId();
		roomMemberBehaviorService.forceOut(roomId, userId, toUserId);
		return new BaseRespVO();
	}

	// 上or下麦（用户主动上、下麦）
	@CmdAction("mic")
	@DocFlag("上下麦")
	public BaseRespVO upAndDownMic(MicReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		int seat = vo.getSeat();
		int seatType = vo.getSeatType();
		int opearte = vo.getOpearte();
		roomMicInfoService.upAndDownMic(roomId, userId, seat, seatType, opearte);
		return new BaseRespVO();
	}

	@CmdAction()
	@DocFlag("切换麦位")
	public BaseRespVO changeMic(MicReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		int seat = vo.getSeat();
		int seatType = vo.getSeatType();
		roomMicInfoService.changeMic(roomId, userId, seat, seatType);
		return new BaseRespVO();
	}

	// 抱上or下麦（房主或管理员可对未上麦用户直接抱上麦，对已上麦用户抱下麦）
	@CmdAction("manageMic")
	@DocFlag("上下麦管理")
	public BaseRespVO manageMic(MicReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		String targetUserId = vo.getTargetUserId();
		int seat = vo.getSeat();
		int seatType = vo.getSeatType();
		int opearte = vo.getOpearte();
		roomMicInfoService.manageMic(roomId, userId, targetUserId, seat, seatType, opearte);
		return new BaseRespVO();
	}

	@CmdAction()
	@DocFlag("麦位管理")
	public BaseRespVO ruleMic(MicManageReqVO vo) {
		String userId = vo.getHeader().getUserId();
		String roomId = vo.getRoomId();
		int seat = vo.getSeat();
		int seatType = vo.getSeatType();
		int opearte = vo.getOpearte();
		roomMicInfoService.operateMic(roomId, userId, seat, seatType, opearte);
		return new BaseRespVO();
	}

	// 送礼
	@CmdAction()
	@DocFlag("送礼")
	public BaseRespVO sendGift(SendGiftReqVO vo) {
		String fromUserId = vo.getHeader().getUserId();
		String toUserId = vo.getToUserId();
		String roomId = vo.getRoomId();
		int giftId = vo.getGiftId();
		int num = vo.getNum();
		if (num < 1 || num > 1000000) {
			throw new CmdException(ErrorCode.ERROR_501);
		}
		sendGiftRecordService.sendGift(roomId, fromUserId, toUserId, giftId, num, true);
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("赠送背包物品")
	public BaseRespVO sendFromBackPack(SendFromBackPackReqVO req) {
		sendGiftRecordService.sendGiftFromBackpack(req.getHeader().getUserId(), req.getToUserId(), req.getRoomId(),
				req.getCorreId(), req.getType(), req.getNum());
		return new BaseRespVO();

	}

	@CmdAction
	@DocFlag("获取精灵表数据")
	public FairyInfoRespVO getFairyInfo(BaseReqVO req) {
		FairyInfo fairyInfo = fairyInfoService.getCurrentFairy();
		if (fairyInfo == null) {
			return new FairyInfoRespVO();
		}
		FairyInfoRespVO resp = new FairyInfoRespVO();
		resp.setActivityName(fairyInfo.getActivityName());
		List<String> imgs = JSON.parseObject(fairyInfo.getShowImg(), new TypeReference<List<String>>() {
		});
		resp.setShowImgs(imgs);
		return resp;
	}

}
