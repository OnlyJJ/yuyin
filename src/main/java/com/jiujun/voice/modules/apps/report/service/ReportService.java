package com.jiujun.voice.modules.apps.report.service;

import com.jiujun.voice.modules.apps.report.domain.ReportRecord;
/**
 * 
 * @author Coody
 *
 */
public interface ReportService {

	
	/**
	 * 记录举报内容
	 * @param report
	 * @return
	 */
	public Long addReport(ReportRecord report);
	/**
	 * 查询举报内容
	 * @param reporterUserId  举报人 
	 * @param targetUserId 被举报人
	 * @return
	 */
	public ReportRecord getTargeReportRecord(String reporterUserId,String targetUserId);
	
	/**
	 * 查询最近一次举报
	 */
	public ReportRecord getLastReportRecord(String reporterUserId);
}
