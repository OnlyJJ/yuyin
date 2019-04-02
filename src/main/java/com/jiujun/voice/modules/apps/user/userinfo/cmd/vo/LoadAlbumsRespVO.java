package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema.UserAlbumSchema;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class LoadAlbumsRespVO extends BaseRespVO{

	@DocFlag("相册列表")
	private List<UserAlbumSchema> albums;

	public List<UserAlbumSchema> getAlbums() {
		return albums;
	}

	public void setAlbums(List<UserAlbumSchema> albums) {
		this.albums = albums;
	}
	
	
	
}
