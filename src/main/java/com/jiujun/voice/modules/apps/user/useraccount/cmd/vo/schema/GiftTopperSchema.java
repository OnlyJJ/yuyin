package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class GiftTopperSchema extends SchemaModel {

	@DocFlag("礼物ID")
	private Integer giftId;
	@DocFlag("礼物名称")
	private String giftName;
	@DocFlag("礼物图标")
	private String giftIcon;
	@DocFlag("累计数量")
	private Integer num;

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftIcon() {
		return giftIcon;
	}

	public void setGiftIcon(String giftIcon) {
		this.giftIcon = giftIcon;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
