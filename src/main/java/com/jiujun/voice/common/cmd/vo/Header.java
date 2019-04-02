package com.jiujun.voice.common.cmd.vo;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.CmdModel;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@SuppressWarnings("serial")
public class Header extends CmdModel{
	/**
	 * 命令
	 */
	@ParamCheck
	private String cmd;
	/**
	 * 动作
	 */
	@ParamCheck
	private String action;
	@DocFlag("用户ID")
	private String userId;
	@DocFlag("用户令牌")
	private String token;
	
	private String ipAddress;
	@DocFlag(value="客户端包名",docFieldValue="com.jj.etvoice.cdocer")
	private String packager;
	@DocFlag(value="渠道号",docFieldValue="Cdocer")
	private String channel;
	@DocFlag(value="客户端版本号",docFieldValue="1.0")
	private String version;
	@DocFlag(value="客户端类型,0安卓 1IOS 2WEB",docFieldValue="0")
	private Integer clientType;
	@DocFlag(value="客户端唯一ID",docFieldValue="1B901922E93745528963DA3E553A2B20")
	private String clientId;
	@DocFlag(value="机型",docFieldValue="Cdocer 1.0")
	private String clientModel;
	@DocFlag(value="网络环境",docFieldValue="WLAN")
	private String network;
	@DocFlag(value="系统OS版本",docFieldValue="8.1.0")
	private String osVersion;
	@DocFlag(value="客户端签名",docFieldValue="9989d994006f428d94439239665bc029")
	private String clientSign;
	
	
	
	
	
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	public String getClientModel() {
		return clientModel;
	}
	public void setClientModel(String clientModel) {
		this.clientModel = clientModel;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getClientSign() {
		return clientSign;
	}
	public void setClientSign(String clientSign) {
		this.clientSign = clientSign;
	}
	public String getPackager() {
		return packager;
	}
	public void setPackager(String packager) {
		this.packager = packager;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	
}
