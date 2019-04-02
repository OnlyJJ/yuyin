package com.jiujun.voice.modules.apps.file.cmd.vo;

import java.util.Base64;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UploadReqVO extends BaseReqVO{
	
	@ParamCheck
	@DocFlag("文件类型,头像:head，相册:album 举报:report 常规:general 音频：audio")
	private String busType;

	@DocFlag("Base64数据")
	@ParamCheck
	private String img;
	
	@ParamCheck
	@DocFlag("文件格式，如：jpg、mp3")
	private String fileType;

	
	
	
	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getImg() {
		try {
			return Base64.getDecoder().decode(img.trim());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public void setImg(String img) {
		this.img = img;
	}

	
	
}
