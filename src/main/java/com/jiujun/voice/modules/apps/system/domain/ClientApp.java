package com.jiujun.voice.modules.apps.system.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * @author Coody
 *
 */
@DBTable("t_client_app")
@SuppressWarnings("serial")
public class ClientApp extends DBModel{

	
	private Integer type;
	private String version;
	private String url;
	private Integer musted = 0;
	private String packager;
	private String remark;
	private String sign;
	private Integer status;
	private Date createTime;
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
