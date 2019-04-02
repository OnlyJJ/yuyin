package com.jiujun.voice.modules.apps.report.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.report.domain.ReportRecord;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class ReportDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public Long addReport(ReportRecord report){
		return jdbcHandle.insert(report);
	}
	
	public ReportRecord getTargeReportRecord(String reporterUserId, String targetUserId) {
		String sql="select * from t_report_record where reporterUserId=? and targetUserId=? and status=0 limit 1";
		return jdbcHandle.queryFirst(ReportRecord.class, sql,reporterUserId,targetUserId);
	}
	
	public ReportRecord getLastReportRecord(String reporterUserId){
		String sql="select * from t_report_record where reporterUserId=? order by id desc limit 1";
		return jdbcHandle.queryFirst(ReportRecord.class, sql,reporterUserId);
	}
}
