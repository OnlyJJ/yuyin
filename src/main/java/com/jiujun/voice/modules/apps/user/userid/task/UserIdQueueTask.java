package com.jiujun.voice.modules.apps.user.userid.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.task.BaseTask;
import com.jiujun.voice.common.task.annotation.TaskSingle;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.dic.domain.DicInfo;
import com.jiujun.voice.modules.apps.dic.service.DicInfoService;
import com.jiujun.voice.modules.apps.user.userid.domain.UserIdPretty;
import com.jiujun.voice.modules.apps.user.userid.domain.UserIdQueue;
import com.jiujun.voice.modules.apps.user.userid.handle.UserIdPrettyHandle;
import com.jiujun.voice.modules.apps.user.userid.service.UserIdPrettyService;
import com.jiujun.voice.modules.apps.user.userid.service.UserIdQueueService;

/**
 * 用户ID序列生成器
 * 
 * @author Coody
 *
 */
@Component
public class UserIdQueueTask extends BaseTask {

	@Resource
	UserIdQueueService userIdQueueService;
	@Resource
	DicInfoService dicInfoService;
	@Resource
	UserIdPrettyHandle userIdPrettyHandle;
	@Resource
	UserIdPrettyService userIdPrettyService;

	/**
	 * 当池中ID不足2W时，生成2W条数据 单机带锁执行
	 */
	@Scheduled(cron = "0 0/10 * * * ? ")
	@TaskSingle
	@LogFlag("用户ID生成任务")
	public synchronized void initUserId() {

		Integer count = userIdQueueService.getCount();
		LogUtil.logger.info("用户ID池剩余账号:" + count);
		if (count > 20000) {
			return;
		}
		// 加载起始序列
		DicInfo dicInfo = dicInfoService.getDicInfo(Constants.USER_ID_INDEX);
		if (dicInfo == null) {
			dicInfo = new DicInfo();
			dicInfo.setFieldName(Constants.USER_ID_INDEX);
			dicInfo.setRemark("用户ID序列");
			dicInfo.setFieldValue("99999");
			dicInfoService.saveDicInfo(dicInfo);
		}
		Integer startCode = StringUtil.toInteger(dicInfo.getFieldValue());
		Integer num = 0;
		try {
			List<UserIdPretty> prettys=new ArrayList<UserIdPretty>();
			List<UserIdQueue> userIds=new ArrayList<UserIdQueue>();
			while (num < 20000) {
				TimeUnit.MICROSECONDS.sleep(1);
				startCode++;
				String format = userIdPrettyHandle.getPrettyFormat(startCode);
				if (!StringUtil.isNullOrEmpty(format)) {
					UserIdPretty pretty = new UserIdPretty();
					pretty.setCode(startCode.toString());
					pretty.setFormat(format);
					pretty.setStatus(0);
					prettys.add(pretty);
					continue;
				}
				UserIdQueue userIdQueue = new UserIdQueue();
				userIdQueue.setCode(startCode.toString());
				userIdQueue.setSeq(StringUtil.getRanDom(0, Integer.MAX_VALUE));
				userIds.add(userIdQueue);
				if(num!=0&&num%1000==0){
					dicInfo.setFieldValue(startCode.toString());
					dicInfoService.saveDicInfo(dicInfo);
					if(prettys.size()>0){
						userIdPrettyService.insertUserIdPretty(prettys);
						prettys.clear();
					}
					if(userIds.size()>0){
						userIdQueueService.insertUserId(userIds);
						userIds.clear();
					}
				}
				num++;
			}
			if(prettys.size()>0){
				userIdPrettyService.insertUserIdPretty(prettys);
				prettys.clear();
			}
			if(userIds.size()>0){
				userIdQueueService.insertUserId(userIds);
				userIds.clear();
			}
			dicInfo.setFieldValue(startCode.toString());
			dicInfoService.saveDicInfo(dicInfo);
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		System.out.println(StringUtil.getRanDom(0, Integer.MAX_VALUE));
	}
}
