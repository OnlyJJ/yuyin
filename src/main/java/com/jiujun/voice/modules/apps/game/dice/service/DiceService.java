package com.jiujun.voice.modules.apps.game.dice.service;

import java.util.List;

import com.jiujun.voice.modules.apps.game.dice.domain.GameDiceQualer;

/**
 * 
 * @author Coody
 *
 */
public interface DiceService {

	
	/**
	 * 查询资质名单
	 * 
	 * @param roomId
	 * @return
	 */
	public List<GameDiceQualer> getQualer(String roomId);

	/**
	 * 取消资质
	 */
	public Long cancelQualer(String roomId,String userId);

	/**
	 * 写入资质名单
	 */
	public void pushQualer(String roomId, List<String> userIds);
	
	
	/**
	 * 开始摇色子
	 */
	public Long doDice(String roomId,String userId);
}
