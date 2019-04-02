package com.jiujun.voice.modules.apps.user.relation.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class FriendsRespVO extends BaseRespVO{

	
	@DocFlag("好友列表")
	private List<String> userIds;

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
	
	
}
