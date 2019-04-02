package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class DelAlbumsReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("图片地址(相对路径、多张)")
	private List<String> imgs;

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
	
	
}
