package com.jiujun.voice.modules.apps.game.dice.cmd.vo;

import java.util.Date;
import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.game.dice.domain.GameDiceQualer;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class DiceQualersRespVO extends BaseRespVO{

	
	@DocFlag("资格用户列表")
	private List<GameDiceQualer> qualers;

	public List<GameDiceQualer> getQualers() {
		return qualers;
	}

	public void setQualers(List<GameDiceQualer> qualers) {
		this.qualers = qualers;
	}



	
	
	public static void main(String[] args) {
		String star="2014-03-10";
		
		Date date=new Date();
		
		int i=0;
		while(!DateUtils.toString(date,DateUtils.DATA_PATTERN).equals(star)){
			i++;
			date=DateUtils.changeDay(date, -1);
		}
		System.out.println(i);
	}
}
