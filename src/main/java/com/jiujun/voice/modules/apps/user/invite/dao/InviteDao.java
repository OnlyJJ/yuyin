package com.jiujun.voice.modules.apps.user.invite.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.jdbc.process.PagerAble;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.user.invite.domain.InvitePrizedRecord;
import com.jiujun.voice.modules.apps.user.invite.domain.InviteRecord;

@Repository
public class InviteDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 根据用户ID查询邀请奖励
	 * 
	 * @param userId
	 * @return
	 */
	@CacheWrite(key = CacheConstants.INVITE_PRIZE_INFO, fields = "userId", time = 600)
	public InvitePrizedRecord getUserInvitePrize(String userId) {
		String sql = "select * from t_invite_prized_record where userId=? limit 1";
		return jdbcHandle.queryFirst(InvitePrizedRecord.class, sql, userId);
	}

	/**
	 * 添加奖励记录
	 * 
	 * @param userId
	 * @param ingot
	 * @param gold
	 * @return
	 */
	@CacheWipe(key = CacheConstants.INVITE_PRIZE_INFO, fields = "userId")
	public Long pushUserInvitePrize(String userId, Integer ingot, Integer gold) {
		if (ingot == null) {
			ingot = 0;
		}
		if (gold == null) {
			gold = 0;
		}
		InvitePrizedRecord record = new InvitePrizedRecord();
		record.setUserId(userId);
		record.setGold(gold);
		record.setIngot(ingot);
		return jdbcHandle.saveOrUpdate(record, "gold", "ingot");
	}

	/**
	 * 加载我的邀请列表
	 */
	@CacheWrite(time = 3)
	public Pager getInvitePager(String userId, Pager pager) {
		String sql = "select * from t_invite_record where inviter=? order by createTime desc ";
		return new PagerAble(InviteRecord.class, sql, pager, userId).invoke();
	}

	/**
	 * 录入邀请人
	 * 
	 * @param userId
	 *            用户ID
	 * @param inviterUserId
	 *            邀请人ID
	 * @param status
	 *            状态 0无效 1有效
	 * @param remark
	 *            描述
	 * @return
	 */
	public Long inputInvite(String userId, String inviterUserId, Integer status, String remark) {
		InviteRecord inviteRecord = new InviteRecord();
		inviteRecord.setRegistranter(userId);
		inviteRecord.setInviter(inviterUserId);
		inviteRecord.setStatus(status);
		inviteRecord.setRemark(remark);
		inviteRecord.setCreateTime(new Date());
		inviteRecord.setDayCode(DateUtils.getDayCode(inviteRecord.getCreateTime()));
		return jdbcHandle.insert(inviteRecord);
	}

	/**
	 * 查询某日邀请人数
	 * 
	 * @param inviter
	 *            邀请者
	 * @param dayCode
	 *            日期 DateUtils.getDayCode()
	 * @return
	 */
	public Integer getInviteNum(String inviter, String dayCode, Integer status) {
		String sql = "select count(id) from t_invite_record where inviter=? and status=? and dayCode=?";
		return jdbcHandle.queryFirst(Integer.class, sql, inviter, status, dayCode);
	}

	/**
	 * 查询用户的伯乐(邀请者)
	 * 
	 * @param registranter
	 *            被邀请者用户ID
	 * @return
	 */
	@CacheWrite(time = 72000)
	public InviteRecord getInviteRecordByUserId(String registranter) {
		String sql = "select * from t_invite_record where registranter=? limit 1";
		return jdbcHandle.queryFirst(InviteRecord.class, sql, registranter);
	}
	
	
	public static void main(String[] args) {
		System.out.println();
	}
}
