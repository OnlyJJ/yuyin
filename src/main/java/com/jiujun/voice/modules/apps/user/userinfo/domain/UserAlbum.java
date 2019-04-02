package com.jiujun.voice.modules.apps.user.userinfo.domain;

import java.util.Date;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_user_album")
public class UserAlbum extends DBModel{

	@DocFlag("用户ID")
	private String userId;
	@DocFlag("图片地址,相对路径")
	private String img;
	@DocFlag("创建时间")
	private Date createTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
