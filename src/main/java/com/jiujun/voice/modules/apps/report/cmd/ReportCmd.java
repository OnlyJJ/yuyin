package com.jiujun.voice.modules.apps.report.cmd;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.modules.apps.report.cmd.vo.ReportReqVO;
import com.jiujun.voice.modules.apps.report.domain.ReportRecord;
import com.jiujun.voice.modules.apps.report.service.ReportService;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("report")
@DocFlag("反馈中心")
public class ReportCmd extends RootCmd{

	
	@Resource
	ReportService reportService;
	
	@CmdAction
	@DocFlag("用户/房间举报")
	public BaseRespVO sendReport(ReportReqVO req){
		ReportRecord report=reportService.getTargeReportRecord(req.getHeader().getUserId(), req.getTargetUserId());
		if(report!=null){
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1041);
		}
		report=reportService.getLastReportRecord(req.getHeader().getUserId());
		if(report!=null){
			if(System.currentTimeMillis()-report.getCreateTime().getTime()<60*1000){
				return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1042);
			}
		}
		//判断是否存在待处理记录
		report=new ReportRecord();
		BeanUtils.copyProperties(req, report);
		report.setReporterUserId(req.getHeader().getUserId());
		report.setStatus(0);
		Long code=reportService.addReport(report);
		if(code>0){
			return new BaseRespVO();
		}
		return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1004);
	}
}
