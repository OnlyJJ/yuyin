package com.jiujun.voice.modules.apps.topper.service;

import java.util.List;

import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserCharmSchema;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserExpSchema;
import com.jiujun.voice.modules.apps.topper.domain.UserSendTopper;
/**
 * @author Coody
 *
 */
public interface UserSendTopperService {

	/**
	 * 保存榜单信息
	 * 
	 * @param toppers
	 * @return
	 */
	public Integer saveUserSendToppers(List<UserSendTopper> toppers);


	/**
	 * 查看用户礼物接受排行
	 * 
	 * @param type 0总榜，1日榜 2周榜 3月榜
	 * @param num  条数，默认10条
	 */
	public List<UserSendTopper> getUserReceiveGiftTopper(String toUserId);
	
	/**
	 * 分页查询财富榜
	 * 
	 * @param type
	 *            0总榜，1日榜 2周榜 3月榜
	 * @param pager
	 * @return
	 */
	public Pager getExpTopperPager(Integer type, Pager pager);
	
	/**
	 * 获取我的财富榜排名
	 * 
	* @param userId
	 *            
	 * @param type
	 *            0总榜，1日榜 2周榜 3月榜
	 * @return
	 */
	public UserExpSchema getExpRank(String userId,Integer type);
	
	/**
	 * 分页查询魅力榜
	 * 
	 * @param type
	 *            0总榜，1日榜 2周榜 3月榜
	 * @param pager
	 * @return
	 */
	public Pager getCharmTopperPager(Integer type, Pager pager);

	/**
	 * 获取我的魅力榜排名
	 * 
	* @param userId
	 *            
	 * @param type
	 *            0总榜，1日榜 2周榜 3月榜
	 * @return
	 */
	public UserCharmSchema getCharmRank(String userId, Integer type);
	
}
