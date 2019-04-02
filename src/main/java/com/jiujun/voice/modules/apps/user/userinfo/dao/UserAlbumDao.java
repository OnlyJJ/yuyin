package com.jiujun.voice.modules.apps.user.userinfo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserAlbum;
/**
 * @author Coody
 *
 */
@Repository
public class UserAlbumDao {

	@Resource
	JdbcHandle jdbcHandle;

	@CacheWrite(key = CacheConstants.USER_ALBUM_LIST, time = 7200, fields = "userId")
	public List<UserAlbum> getUserAlbums(String userId) {
		String sql = "select * from t_user_album where userId=? order by id desc ";
		return jdbcHandle.query(UserAlbum.class, sql, userId);
	}
	
	@CacheWipe(key = CacheConstants.USER_ALBUM_LIST, fields = "userId")
	public Integer commitUserAlbums(String userId, List<String> imgs) {
		List<UserAlbum> albums=new ArrayList<UserAlbum>();
		for(String img:imgs){
			UserAlbum album=new UserAlbum();
			album.setImg(img);
			album.setUserId(userId);
			albums.add(album);
		}
		return jdbcHandle.batchSaveOrUpdate(albums);
	}
	
	@CacheWipe(key = CacheConstants.USER_ALBUM_LIST,  fields = "userId")
	public Integer delUserAlbums(String userId,List<String> imgs){
		for(String img:imgs){
			String sql="delete from t_user_album where userId=? and img=? limit 1";
			jdbcHandle.update(sql,userId,img);
		}
		return imgs.size();
	}
}
