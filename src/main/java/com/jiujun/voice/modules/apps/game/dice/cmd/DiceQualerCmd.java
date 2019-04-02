package com.jiujun.voice.modules.apps.game.dice.cmd;

import java.util.List;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.game.dice.cmd.vo.DiceQualersReqVO;
import com.jiujun.voice.modules.apps.game.dice.cmd.vo.DiceQualersRespVO;
import com.jiujun.voice.modules.apps.game.dice.cmd.vo.SetDiceQualersReqVO;
import com.jiujun.voice.modules.apps.game.dice.domain.GameDiceQualer;
import com.jiujun.voice.modules.apps.game.dice.service.DiceService;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("dice")
@DocFlag("摇骰子")
public class DiceQualerCmd extends RootCmd {

	@Resource
	DiceService diceService;
	@Resource
	RoomInfoService roomInfoService;

	@CmdAction
	@DocFlag("设置资格用户")
	public BaseRespVO setDiceQualers(SetDiceQualersReqVO req) {
		diceService.pushQualer(req.getHeader().getUserId(), req.getUsers());
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("摇骰子")
	public BaseRespVO doDice(BaseReqVO req) {
		RoomInfo roomInfo = roomInfoService.getUserInRoom(req.getHeader().getUserId());
		if (roomInfo == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_2019);
		}
		List<GameDiceQualer> qualers = diceService.getQualer(roomInfo.getRoomId());
		if (StringUtil.isNullOrEmpty(qualers)) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_503);
		}
		List<String> users=PropertUtil.getFieldValues(qualers, "userId");
		if(!users.contains(req.getHeader().getUserId())){
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_503);
		}
		Long code = diceService.doDice(roomInfo.getRoomId(), req.getHeader().getUserId());
		if(code<1){
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1004);
		}
		return new BaseRespVO();
	}
	
	@CmdAction
	@DocFlag("下麦取消资格")
	public BaseRespVO cancelDice(BaseReqVO req) {
		RoomInfo roomInfo = roomInfoService.getUserInRoom(req.getHeader().getUserId());
		if (roomInfo == null) {
			return new BaseRespVO();
		}
		diceService.cancelQualer(roomInfo.getRoomId(), req.getHeader().getUserId());
		return new BaseRespVO();
	}
	
	@CmdAction
	@DocFlag("摇骰子资格列表")
	public DiceQualersRespVO diceQualers(DiceQualersReqVO req) {
		List<GameDiceQualer> qualers=diceService.getQualer(req.getRoomId());
		DiceQualersRespVO resp=new DiceQualersRespVO();
		resp.setQualers(qualers);
		return resp;
	}
}
