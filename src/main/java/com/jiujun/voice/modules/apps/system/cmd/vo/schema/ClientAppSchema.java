package com.jiujun.voice.modules.apps.system.cmd.vo.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;

@SuppressWarnings("serial")
public class ClientAppSchema extends SchemaModel{


	@DocFlag("版本号")
	private String version;
	@DocFlag("下载地址")
	private String url;
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
