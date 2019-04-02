package com.jiujun.voice.modules.apps.report.domain;

import java.util.Date;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_report_record")
public class ReportRecord extends DBModel{

	
	@DocFlag("举报类型，0个人 1房间")
	public Integer reportType;
	
	@DocFlag("目标用户ID")
	private String targetUserId;
	
	@DocFlag("举报者用户ID")
	private String reporterUserId;
	
	@DocFlag("违规类型,逗号分割")
	private String violations;
	
	@DocFlag("图片列表，逗号分割")
	private String imgs;
	
	@DocFlag("具体描述")
	private String remark;
	
	@DocFlag("举报时间")
	private Date createTime;
	
	@DocFlag("处理状态，0待处理")
	public Integer status;

	
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	public String getReporterUserId() {
		return reporterUserId;
	}

	public void setReporterUserId(String reporterUserId) {
		this.reporterUserId = reporterUserId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
