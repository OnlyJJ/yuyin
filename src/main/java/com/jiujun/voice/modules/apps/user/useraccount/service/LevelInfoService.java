package com.jiujun.voice.modules.apps.user.useraccount.service;

import com.jiujun.voice.modules.apps.user.useraccount.domain.LevelInfo;
/**
 * 
 * @author Coody
 *
 */
public interface LevelInfoService {
	
	/**
	 * 获取当前经验对应的等级
	 * @author Shao.x
	 * @date 2018年12月17日
	 * @param type 类型，1-财富，2-魅力
	 * @param exp 当前经验
	 * @return
	 */
	LevelInfo getLevel(int type, long exp);
	
	/**
	 * 根据当前等级，获取下一财富等级对应的经验值
	 * @author Shao.x
	 * @date 2018年12月17日
	 * @param level
	 * @return 返回-1，表示已经是最大等级
	 */
	long getNextRichExp(int level);
	
	/**
	 * 根据当前等级，获取下一魅力等级对应的经验值
	 * @author Shao.x
	 * @date 2018年12月17日
	 * @param level
	 * @return 返回-1，表示已经是最大等级
	 */
	long getNextCharmExp(int level);
	
}
