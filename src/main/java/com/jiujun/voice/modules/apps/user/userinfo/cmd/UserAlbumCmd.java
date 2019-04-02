package com.jiujun.voice.modules.apps.user.userinfo.cmd;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.CommitAlbumsReqVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.DelAlbumsReqVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.LoadAlbumsReqVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.LoadAlbumsRespVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema.UserAlbumSchema;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserAlbum;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserAlbumService;
/**
 * @author Coody
 *
 */
@CmdOpen("userAlbum")
@DocFlag("用户相册中心")
public class UserAlbumCmd extends RootCmd{

	@Resource
	UserAlbumService userAlbumService;
	
	@CmdAction
	@DocFlag("相册列表")
	public LoadAlbumsRespVO loadAlbums(LoadAlbumsReqVO req){
		List<UserAlbum> albums=userAlbumService.getUserAlbums(req.getTargetUserId());
		if(StringUtil.isNullOrEmpty(albums)){
			return new LoadAlbumsRespVO();
		}
		List<UserAlbumSchema> albumSchemas=PropertUtil.getNewList(albums, UserAlbumSchema.class);
		LoadAlbumsRespVO resp=new LoadAlbumsRespVO();
		resp.setAlbums(albumSchemas);
		return resp;
	}
	
	@CmdAction
	@DocFlag("提交相册图片")
	public BaseRespVO commitAlbums(CommitAlbumsReqVO req){
		userAlbumService.commitUserAlbums(req.getHeader().getUserId(), Arrays.asList(new String[]{req.getImg()}));
		return new BaseRespVO();
	}
	
	@CmdAction
	@DocFlag("删除图片")
	public BaseRespVO delAlbums(DelAlbumsReqVO req){
		userAlbumService.delUserAlbums(req.getHeader().getUserId(), req.getImgs());
		return new BaseRespVO();
	}
}
