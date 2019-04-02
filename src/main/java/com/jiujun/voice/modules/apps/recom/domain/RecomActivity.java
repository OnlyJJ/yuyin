package com.jiujun.voice.modules.apps.recom.domain;

import java.util.Date;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_recom_activity")
public class RecomActivity extends DBModel{

	@DocFlag("图片地址")
	private String ico;
	@DocFlag("跳转地址")
	private String href;
	@DocFlag("排序")
	private Integer seq;
	@DocFlag("状态")
	private Integer status;
	@DocFlag("描述")
	private String remark;
	@DocFlag("创建时间")
	private Date createTime;
	
	
	
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIco() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) +ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
