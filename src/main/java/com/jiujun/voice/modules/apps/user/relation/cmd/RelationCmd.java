package com.jiujun.voice.modules.apps.user.relation.cmd;

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
import com.jiujun.voice.modules.apps.user.relation.cmd.vo.ApplyRecordsRespVO;
import com.jiujun.voice.modules.apps.user.relation.cmd.vo.DoRelationReqVO;
import com.jiujun.voice.modules.apps.user.relation.cmd.vo.FollowReqVO;
import com.jiujun.voice.modules.apps.user.relation.cmd.vo.FriendsRespVO;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationQueue;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationRecord;
import com.jiujun.voice.modules.apps.user.relation.schema.RelationApplySchema;
import com.jiujun.voice.modules.apps.user.relation.service.RelationService;
/**
 * 
 * @author Coody
 *
 */
@CmdOpen("relation")
@DocFlag("用户关系")
public class RelationCmd extends RootCmd {

	@Resource
	RelationService relationService;

	@CmdAction
	@DocFlag("好友添加")
	public BaseRespVO follow(FollowReqVO req) {
		RelationRecord relationRecord = relationService.getRelation(req.getHeader().getUserId(), req.getTargeUserId());
		if (relationRecord != null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1028);
		}
		relationService.addRelation(req.getHeader().getUserId(), req.getTargeUserId());
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("好友删除")
	public BaseRespVO unFollow(FollowReqVO req) {
		RelationRecord relationRecord = relationService.getRelation(req.getHeader().getUserId(), req.getTargeUserId());
		if (relationRecord == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1029);
		}
		Long code = relationService.unFollow(req.getHeader().getUserId(), req.getTargeUserId());
		if (code < 1) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1030);
		}
		return new BaseRespVO();
	}

	//@CmdAction
	//@DocFlag("好友拉黑")
	public BaseRespVO ignoreUser(FollowReqVO req) {
		RelationRecord relationRecord = relationService.getRelation(req.getHeader().getUserId(), req.getTargeUserId());
		if (relationRecord == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1029);
		}
		Long code = relationService.ignoreUser(req.getHeader().getUserId(), req.getTargeUserId());
		if (code < 1) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1030);
		}
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("好友申请处理")
	public BaseRespVO doRelation(DoRelationReqVO req) {
		RelationQueue queue = relationService.getRelationQueue(req.getSender(), req.getHeader().getUserId());
		if (queue == null || queue.getStatus() != 0) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1031);
		}
		relationService.doRelation(req.getSender(), req.getHeader().getUserId(),req.getStatus());
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("加载好友申请列表")
	public ApplyRecordsRespVO applyRecords(BaseReqVO req) {
		List<RelationApplySchema> applys = relationService.getApplyQueues(req.getHeader().getUserId());
		if (StringUtil.isNullOrEmpty(applys)) {
			return new ApplyRecordsRespVO();
		}
		ApplyRecordsRespVO resp = new ApplyRecordsRespVO();
		resp.setApplys(applys);
		return resp;
	}

	@CmdAction
	@DocFlag("我的好友列表")
	public FriendsRespVO friends(BaseReqVO req) {
		List<String> userIds=relationService.getRelations(req.getHeader().getUserId());
		if(StringUtil.isNullOrEmpty(userIds)){
			return new FriendsRespVO();
		}
		FriendsRespVO resp=new FriendsRespVO();
		resp.setUserIds(userIds);
		return resp;
	}

}
