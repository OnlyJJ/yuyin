package com.jiujun.voice.modules.apps.pay.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.pay.domain.PayAppleProduct;
import com.jiujun.voice.modules.apps.pay.domain.PayConfig;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class PayDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 
	 * @param type
	 *            1微信 2支付宝
	 * @return
	 */
	@CacheWrite(time = 60)
	public PayConfig getPayConfig(Integer type) {
		String sql = "select * from t_pay_config where type=? limit 1";
		return jdbcHandle.queryFirst(PayConfig.class, sql, type);
	}

	/**
	 * 添加订单
	 */
	public Long addPayRecord(PayRecord record) {
		return jdbcHandle.insert(record);
	}

	/**
	 * 更新订单状态
	 */
	public Long upPayRecord(Long payId, String tradeNo, Integer gold) {
		String sql = "update t_pay_record set status=1,tradeNo=?,gold=? where id=? and status=0 limit 1";
		return jdbcHandle.update(sql, tradeNo, gold,payId);
	}

	/**
	 * 
	 * @param payId
	 * @return
	 */
	public PayRecord getPayRecord(Long payId) {
		String sql = "select * from t_pay_record where id=? limit 1";
		return jdbcHandle.queryFirst(PayRecord.class, sql, payId);
	}

	/**
	 * 獲取蘋果支付產品列表
	 */
	@CacheWrite(key = CacheConstants.PAY_APPLE_PRODUCTS, time = 60)
	@SuppressWarnings("unchecked")
	public Map<String, PayAppleProduct> getPayAppleProducts() {
		String sql = "select * from t_pay_apple_product";
		List<PayAppleProduct> products = jdbcHandle.query(PayAppleProduct.class, sql);
		if (StringUtil.isNullOrEmpty(products)) {
			return null;
		}
		Map<String, PayAppleProduct> productMap = (Map<String, PayAppleProduct>) PropertUtil.listToMap(products,
				"productId");
		return productMap;
	}

	/**
	 * 获取交易信息
	 */
	public PayRecord getPayRecordByTransNo(String transNo, Integer type) {
		String sql = "select * from  t_pay_record where tradeNo=? and payType=? limit 1";
		return jdbcHandle.queryFirst(PayRecord.class, sql, transNo, type);
	}
	
	/**
	 * 根据用户Id获取充值记录
	 */
	public PayRecord getPayRecordByUserId(String userId){
		String sql = "select * from  t_pay_record where userId=? and status=1 order by  createTime limit 1";
		return jdbcHandle.queryFirst(PayRecord.class, sql, userId);
	}
}
