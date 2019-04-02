package com.jiujun.voice.modules.apps.system.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ClientUpdateRespVO extends BaseRespVO{

	
	@DocFlag("版本号")
	private String version;
	@DocFlag("下载地址")
	private String url;
	@DocFlag("是否强制更新，0非强制 1强制")
	private Integer musted =0;
	@DocFlag("包名")
	private String packager;
	@DocFlag("更新描述")
	private String remark;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getMusted() {
		return musted;
	}
	public void setMusted(Integer musted) {
		this.musted = musted;
	}
	public String getPackager() {
		return packager;
	}
	public void setPackager(String packager) {
		this.packager = packager;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
