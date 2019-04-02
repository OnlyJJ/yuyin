package com.jiujun.voice.modules.apps.home.service;

import java.util.List;

import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.home.cmd.schema.SearchResultSchema;

/**
 * 
 * @author Coody
 *
 */
public interface HomePageService {
	
	/**
	 * 获取所有有效的快速加入的房间列表
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @param pageSize 单页大小
	 * @param pageNo 页码
	 * @return
	 */
	Pager listQuickRoom(int pageSize, int pageNo);
	
	/**
	 * 获取类型下的所有房间
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @param type
	 * @return
	 */
	Pager listRoomByType(String type, int pageSize, int pageNo);
	
	/**
	 * 获取推荐房间
	 * @author Shao.x
	 * @date 2018年12月21日
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Pager listRecommend(int pageSize, int pageNo);
	
	/**
	 * 搜索
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @param condition 房间/用户id/用户名/房间名
	 * @return
	 */
	List<SearchResultSchema> search(String condition);
	
	
}
