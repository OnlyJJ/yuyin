package com.jiujun.voice.modules.apps.user.useraccount.service;

import java.util.List;

import com.jiujun.voice.modules.apps.user.useraccount.domain.BackpackInfo;
import com.jiujun.voice.modules.apps.user.useraccount.schema.BackPackSchema;

public interface BackPackService {

	/*
	 * 查询背包详情
	 */
	public List<BackPackSchema> getBackpackInfo(String userId);



	/**
	 * 增加背包礼物
	 * 
	 * @param userId
	 * @param correId
	 *            礼物或道具ID
	 * @param type
	 *            1礼物 2道具
	 * @param num
	 *            赠送数量
	 * @param remark
	 *            描述
	 * @return
	 */
	public Long addBackPack(String userId,String fromUserId, String correId, Integer type, Integer num, String remark);

	/**
	 * 扣背包商品
	 * @param userId
	 * @param correId
	 *            礼物或道具ID
	 * @param type
	 *            1礼物 2道具
	 * @param num
	 *            赠送数量
	 * @param remark
	 *            描述
	 * @return  实际扣取数量 
	 */
	public Long subBackPack(String userId,String toUserId, String correId, Integer type, Integer num, String remark);
	
	/**
	 * 加载物品详情
	 * @param userId
	 * @param correId  关联值，如：礼物ID、道具ID
	 * @param type  1礼物 2道具
	 * @return
	 */
	public BackpackInfo getBackPackInfo(String userId, String correId, Integer type);
	
}
