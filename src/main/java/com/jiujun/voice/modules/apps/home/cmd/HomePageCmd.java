package com.jiujun.voice.modules.apps.home.cmd;

import java.util.List;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.modules.apps.home.cmd.schema.SearchResultSchema;
import com.jiujun.voice.modules.apps.home.cmd.vo.BannerReqVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.BannerRespVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.RoomPageReqVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.RoomPageRespVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.RoomTypeDataReqVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.RoomTypeDataRespVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.RoomTypeRespVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.SearchReqVO;
import com.jiujun.voice.modules.apps.home.cmd.vo.SearchRespVO;
import com.jiujun.voice.modules.apps.home.domain.BannerInfo;
import com.jiujun.voice.modules.apps.home.service.BannerInfoService;
import com.jiujun.voice.modules.apps.home.service.HomePageService;
import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;
import com.jiujun.voice.modules.apps.room.service.RoomEnjoyTypeService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
/**
 * 
 * @author Coody
 *
 */
@CmdOpen("home")
@DocFlag("首页")
public class HomePageCmd extends RootCmd {
	
	@Resource
	BannerInfoService bannerInfoService;
	@Resource
	RoomEnjoyTypeService roomEnjoyTypeService;
	@Resource
	HomePageService homePageService;
	@Resource
	RoomInfoService roomInfoService;
	
	@LoginIgnore
	@CmdAction("banner")
	@DocFlag("banner信息")
	public BannerRespVO getBanner(BannerReqVO vo) {
		int showPlace = vo.getShowPlace();
		List<BannerInfo> data = bannerInfoService.listBannerInfo(showPlace);
		BannerRespVO resp = new BannerRespVO();
		resp.setData(data);
		return resp;
	} 
	
	@LoginIgnore
	@CmdAction
	@DocFlag("搜索")
	public SearchRespVO search(SearchReqVO vo) {
		String condition = vo.getCondition();
		List<SearchResultSchema> data = homePageService.search(condition);
		SearchRespVO resp = new SearchRespVO();
		resp.setData(data);
		return resp;
	} 
	
	@LoginIgnore
	@CmdAction
	@DocFlag("搜索推荐")
	public SearchRespVO searchRecommend(BaseReqVO vo) {
		SearchRespVO resp = new SearchRespVO();
		return resp;
	} 
	
	@LoginIgnore
	@CmdAction()
	@DocFlag("房间类型列表")
	public RoomTypeRespVO listRoomType(BaseReqVO vo) {
		List<RoomEnjoyType> data = roomEnjoyTypeService.listRoomEnjoyConf();
		RoomTypeRespVO resp = new RoomTypeRespVO();
		resp.setData(data);
		return resp;
	} 
	
	@LoginIgnore
	@CmdAction()
	@DocFlag("获取某种房间类型下的所有房间")
	public RoomTypeDataRespVO listRoomTypeData(RoomTypeDataReqVO vo) {
		String type = vo.getType();
		int pageSize = vo.getPageSize();
		int pageNo = vo.getPageNo();
		Pager pager = homePageService.listRoomByType(type, pageSize, pageNo);
		return new RoomTypeDataRespVO().fromPager(pager);
	} 
	
	@LoginIgnore
	@CmdAction()
	@DocFlag("快速加入房间列表")
	public RoomPageRespVO listQuickRoom(RoomPageReqVO vo) {
		int pageSize = vo.getPageSize();
		int pageNo = vo.getPageNo();
		Pager pager = homePageService.listQuickRoom(pageSize, pageNo);
		return new RoomPageRespVO().fromPager(pager);
	} 
	
	
	@LoginIgnore
	@CmdAction()
	@DocFlag("首页推荐")
	public RoomPageRespVO recommend(RoomPageReqVO vo) {
		int pageSize = vo.getPageSize();
		int pageNo = vo.getPageNo();
		Pager pager = homePageService.listRecommend(pageSize, pageNo);
		return new RoomPageRespVO().fromPager(pager);
	} 
	
}
