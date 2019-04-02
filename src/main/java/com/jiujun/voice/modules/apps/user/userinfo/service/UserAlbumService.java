package com.jiujun.voice.modules.apps.user.userinfo.service;

import java.util.List;

import com.jiujun.voice.modules.apps.user.userinfo.domain.UserAlbum;

/**
 * 
 * @author Coody
 *
 */
public interface UserAlbumService {

	/**
	 * 查询用户相册列表
	 * @return
	 */
	public List<UserAlbum> getUserAlbums(String userId);
	
	/**
	 * 保存相册图片
	 */
	public Integer commitUserAlbums(String userId,List<String> imgs);
	
	/**
	 * 删除图片
	 */
	public Integer delUserAlbums(String userId,List<String> imgs);
}
