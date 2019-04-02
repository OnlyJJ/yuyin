package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema.UserGeneralSchema;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SearchUserRespVO extends BaseRespVO{

	@DocFlag("用户信息列表")
	private List<UserGeneralSchema> userInfos;

	public List<UserGeneralSchema> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<UserGeneralSchema> userInfos) {
		this.userInfos = userInfos;
	}
}
