package com.jiujun.voice.modules.apps.game.dice.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SetDiceQualersReqVO extends BaseReqVO{

	
	@DocFlag("用户ID列表")
	private List<String> users;

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
	
	
	
	
}
