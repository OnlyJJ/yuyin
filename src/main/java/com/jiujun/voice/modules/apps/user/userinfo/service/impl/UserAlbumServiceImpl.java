package com.jiujun.voice.modules.apps.user.userinfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.modules.apps.user.userinfo.dao.UserAlbumDao;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserAlbum;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserAlbumService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class UserAlbumServiceImpl implements UserAlbumService{
	
	
	@Resource
	UserAlbumDao userAlbumDao;

	@Override
	public List<UserAlbum> getUserAlbums(String userId) {
		return userAlbumDao.getUserAlbums(userId);
	}

	@Override
	public Integer commitUserAlbums(String userId, List<String> imgs) {
		List<UserAlbum> userAlbums=getUserAlbums(userId);
		if(userAlbums!=null){
			if(userAlbums.size()+imgs.size()>9){
				throw new CmdException(ErrorCode.ERROR_1043);
			}
		}
		Integer code= userAlbumDao.commitUserAlbums(userId, imgs);
		return code;
	}

	@Override
	public Integer delUserAlbums(String userId, List<String> imgs) {
		return userAlbumDao.delUserAlbums(userId, imgs);
	}

}
