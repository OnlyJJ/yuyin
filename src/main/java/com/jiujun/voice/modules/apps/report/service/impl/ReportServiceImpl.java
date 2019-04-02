package com.jiujun.voice.modules.apps.report.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.report.dao.ReportDao;
import com.jiujun.voice.modules.apps.report.domain.ReportRecord;
import com.jiujun.voice.modules.apps.report.service.ReportService;

/**
 *
 * @author Coody
 *
 */
@Service
public class ReportServiceImpl implements ReportService{

	@Resource
	ReportDao reportDao;

	@Override
	public Long addReport(ReportRecord report) {
		return reportDao.addReport(report);
	}

	@Override
	public ReportRecord getTargeReportRecord(String reporterUserId, String targetUserId) {
		return reportDao.getTargeReportRecord(reporterUserId, targetUserId);
	}
	@Override
	public ReportRecord getLastReportRecord(String reporterUserId){
		return reportDao.getLastReportRecord(reporterUserId);
	}
}
