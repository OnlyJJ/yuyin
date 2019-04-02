package com.jiujun.voice.modules.apps.report.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ReportReqVO extends BaseReqVO{

	@DocFlag("举报类型，0个人 1房间")
	public Integer reportType;
	
	@DocFlag("目标用户ID")
	private String targetUserId;
	
	@DocFlag("违规类型,逗号分割")
	private String violations;
	
	@DocFlag("图片列表，逗号分割")
	private String imgs;
	
	@DocFlag("具体描述")
	private String remark;

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getViolations() {
		return violations;
	}

	public void setViolations(String violations) {
		this.violations = violations;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
