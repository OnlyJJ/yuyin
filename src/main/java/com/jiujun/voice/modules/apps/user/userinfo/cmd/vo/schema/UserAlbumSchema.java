package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserAlbum;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UserAlbumSchema extends UserAlbum{

	@DocFlag("图片地址,全路径")
	private String compleImg;
	
	
	
	public String getCompleImg() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) + super.getImg();
	}



	public void setCompleImg(String compleImg) {
		this.compleImg = compleImg;
	}


	
}
