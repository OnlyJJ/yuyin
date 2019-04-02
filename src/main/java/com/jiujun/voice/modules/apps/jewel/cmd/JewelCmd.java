package com.jiujun.voice.modules.apps.jewel.cmd;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.BindCardReqVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.CardInfoRespVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.CheckMobileReqVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.DrawBackReqVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.DrawJewelPageReqVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.DrawPageRespVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.DrawReqVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.ProfitRespVO;
import com.jiujun.voice.modules.apps.jewel.cmd.vo.ReceiveGiftRespVO;
import com.jiujun.voice.modules.apps.jewel.domain.CardInfo;
import com.jiujun.voice.modules.apps.jewel.domain.DrawJewel;
import com.jiujun.voice.modules.apps.jewel.domain.JewelCollect;
import com.jiujun.voice.modules.apps.jewel.service.CardInfoService;
import com.jiujun.voice.modules.apps.jewel.service.DrawJewelRecordService;
import com.jiujun.voice.modules.apps.jewel.service.DrawJewelService;
import com.jiujun.voice.modules.apps.jewel.service.JewelCollectService;
import com.jiujun.voice.modules.apps.room.service.SendGiftRecordService;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.verificat.service.VerificatService;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("jewel")
@DocFlag("我的收益")
public class JewelCmd extends RootCmd {

	@Resource
	DrawJewelRecordService drawJewelRecordService;
	@Resource
	DrawJewelService drawJewelService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	SendGiftRecordService sendGiftRecordService;
	@Resource
	VerificatService verificatService;
	@Resource
	CardInfoService cardInfoService;
	@Resource
	JewelCollectService jewelCollectService;

	private Date getDrawStartTime() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		return DateUtils.toDate(year+"-"+month+"-20");
	}
	private Date getDrawEndTime() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		return DateUtils.toDate(year+"-"+month+"-30"+" 23:59:59");
	}
	@CmdAction
	@DocFlag("我的收益")
	public ProfitRespVO myProfit(BaseReqVO req) {
		String userId = req.getHeader().getUserId();
		ProfitRespVO resp = new ProfitRespVO();
		resp.setDrawStartTime(getDrawStartTime());
		resp.setDrawEndTime(getDrawEndTime());
		long totalJewel = 0L;
		long canDrawJewel = 0L;
		long frozenJewel = 0L;
		long dayJewel = 0L;
		DrawJewel drawJewel = drawJewelService.getUserDrawJewel(userId);
		if (drawJewel != null) {
			totalJewel = drawJewel.getTotalJewel(); // 总提现钻石
		}
		resp.setTotalJewel(totalJewel);
		// 当前可提钻石
		UserAccount account = userAccountService.getUserAccountByUserId(userId);
		if (account != null) {
			canDrawJewel = account.getJewel();
		}
		resp.setDrawJewel(canDrawJewel);
		// 当前正在审核中的提现
		Long frozen = drawJewelRecordService.getFrozenJewel(userId);
		if (frozen != null) {
			frozenJewel = frozen;
		}
		resp.setFrozenJewel(frozenJewel);
		// 今日收礼总数
		JewelCollect jc = jewelCollectService.getJewelCollect(userId, DateUtils.getDateString(DateUtils.YYYYMMDD));
		if (jc != null) {
			dayJewel = jc.getJewel();
		}
		resp.setDayJewel(dayJewel);
		return resp;
	}

	@CmdAction
	@DocFlag("绑卡信息")
	public CardInfoRespVO cardInfo(BaseReqVO req) {
		String userId = req.getHeader().getUserId();
		UserAccount userAccount = userAccountService.getUserAccountByUserId(userId);
		if (userAccount == null) {
			throw new CmdException(ErrorCode.ERROR_1009);
		}
		CardInfoRespVO resp = new CardInfoRespVO();
		String mobile = userAccount.getMobile();
		resp.setMobile(mobile);
		CardInfo card = cardInfoService.getCardInfo(userId);
		if (card != null) {
			BeanUtils.copyProperties(card, resp);
		}
		return resp;
	}

	@CmdAction
	@DocFlag("校验 验证码")
	public BaseRespVO checkCode(CheckMobileReqVO req) {
		String mobile = req.getMobile();
		Integer code = req.getCode();
		BaseRespVO resp = new BaseRespVO();

		UserAccount accountDB = userAccountService.getUserAccount(req.getHeader().getUserId());
		if (accountDB != null && !StringUtil.isNullOrEmpty(accountDB)
				&& !accountDB.getMobile().equals(req.getMobile())) {
			return resp.pushErrorCode(ErrorCode.ERROR_1050);
		}
		boolean check = verificatService.doVerification(mobile, code);
		if (!check) {
			resp.pushErrorCode(ErrorCode.ERROR_1017);
		}
		return resp;
	}

	@CmdAction
	@DocFlag("绑定手机")
	public BaseRespVO bindMobile(CheckMobileReqVO req) {
		String userId = req.getHeader().getUserId();
		String mobile = req.getMobile();
		Integer code = req.getCode();
		// 校验验证码
		CardInfoRespVO resp = new CardInfoRespVO();
		boolean check = verificatService.doVerification(mobile, code);
		if (!check) {
			return resp.pushErrorCode(ErrorCode.ERROR_1015);
		}

		// 绑定手机到账户
		UserAccount accountDB = userAccountService.getUserAccount(userId);
		if (accountDB != null && !StringUtil.isNullOrEmpty(accountDB.getMobile())
				&& accountDB.getMobile().equals(mobile)) {
			return resp.pushErrorCode(ErrorCode.ERROR_1015);
		}

		UserAccount accountDBFromMobile = userAccountService.getUserAccount(req.getMobile());
		if (accountDBFromMobile != null) {
			return resp.pushErrorCode(ErrorCode.ERROR_1015);
		}
		UserAccount account = new UserAccount();
		account.setUserId(userId);
		account.setMobile(mobile);
		userAccountService.updateUserAccount(account, "mobile");
		return resp;
	}

	@CmdAction
	@DocFlag("绑定银行卡")
	public BaseRespVO bindCard(BindCardReqVO req) {
		String userId = req.getHeader().getUserId();
		String cardNO = req.getCardNo();
		String owner = req.getOwner();
		UserAccount userAccount = userAccountService.getUserAccountByUserId(userId);
		if (userAccount == null) {
			throw new CmdException(ErrorCode.ERROR_1009);
		}
		String mobile = userAccount.getMobile();
		if (StringUtil.isNullOrEmpty(mobile)) {
			throw new CmdException(ErrorCode.ERROR_1044);
		}
		String bank = req.getBank();
		String subbranch = req.getSubbranch();
		String address = req.getAddress();
		cardInfoService.save(userId, cardNO, owner, bank, subbranch, address);
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("提现")
	public BaseRespVO draw(DrawReqVO req) {
		
		Date now=new Date();
		if(now.getTime()<getDrawStartTime().getTime()||now.getTime()>getDrawEndTime().getTime()){
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_3005);
		}
		
		String userId = req.getHeader().getUserId();
		Integer jewel = req.getJewel();
		drawJewelRecordService.draw(userId, jewel);
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("提现审核后回调")
	public BaseRespVO drawBack(DrawBackReqVO req) {
		String userId = req.getHeader().getUserId();
		Integer status = req.getStatus();
		Integer recordId = req.getRecordId();
		drawJewelRecordService.drawResultBack(userId, recordId, status);
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("提现记录")
	public DrawPageRespVO drawRecord(DrawJewelPageReqVO req) {
		String userId = req.getHeader().getUserId();
		int pageSize = req.getPageSize();
		int pageNo = req.getPageNo();
		Pager page = drawJewelRecordService.getDrawRecord(userId, pageSize, pageNo);
		return new DrawPageRespVO().fromPager(page);
	}

	@CmdAction
	@DocFlag("收礼记录")
	public ReceiveGiftRespVO reciveGiftRecord(DrawJewelPageReqVO req) {
		String userId = req.getHeader().getUserId();
		int pageSize = req.getPageSize();
		int pageNo = req.getPageNo();
		Pager page = sendGiftRecordService.getUserGiftRecord(userId, pageSize, pageNo);
		return new ReceiveGiftRespVO().fromPager(page);
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(DateUtils.toString(new JewelCmd().getDrawStartTime(), DateUtils.DATETIME_PATTERN));
		System.out.println(DateUtils.toString(new JewelCmd().getDrawEndTime(), DateUtils.DATETIME_PATTERN));
	}
}
