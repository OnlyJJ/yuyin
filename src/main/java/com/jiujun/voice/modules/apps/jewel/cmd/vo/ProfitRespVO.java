package com.jiujun.voice.modules.apps.jewel.cmd.vo;

import java.util.Date;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 我的收益
 * @author Shao.x
 * @date 2019年1月9日
 */
@SuppressWarnings("serial")
public class ProfitRespVO extends BaseRespVO{

	@DocFlag("总收益")
	private long totalJewel;
	
	@DocFlag("今日钻石")
	private long dayJewel;
	
	@DocFlag("可提现钻石")
	private long drawJewel;
	
	@DocFlag("正在审核中的钻石")
	private long frozenJewel;
	
	@DocFlag("提款开始日期")
	private Date drawStartTime;
	
	@DocFlag("提款截至日期")
	private Date drawEndTime;
	
	

	public Date getDrawStartTime() {
		return drawStartTime;
	}

	public void setDrawStartTime(Date drawStartTime) {
		this.drawStartTime = drawStartTime;
	}


	public Date getDrawEndTime() {
		return drawEndTime;
	}

	public void setDrawEndTime(Date drawEndTime) {
		this.drawEndTime = drawEndTime;
	}

	public long getTotalJewel() {
		return totalJewel;
	}

	public void setTotalJewel(long totalJewel) {
		this.totalJewel = totalJewel;
	}

	public long getDayJewel() {
		return dayJewel;
	}

	public void setDayJewel(long dayJewel) {
		this.dayJewel = dayJewel;
	}

	public long getDrawJewel() {
		return drawJewel;
	}

	public void setDrawJewel(long drawJewel) {
		this.drawJewel = drawJewel;
	}

	public long getFrozenJewel() {
		return frozenJewel;
	}

	public void setFrozenJewel(long frozenJewel) {
		this.frozenJewel = frozenJewel;
	}
	
	
	
}
