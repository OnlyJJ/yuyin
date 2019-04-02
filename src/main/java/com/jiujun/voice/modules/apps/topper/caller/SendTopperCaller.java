package com.jiujun.voice.modules.apps.topper.caller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;
import com.jiujun.voice.modules.apps.topper.domain.UserSendTopper;
import com.jiujun.voice.modules.apps.topper.service.UserSendTopperService;
import com.jiujun.voice.modules.caller.entity.SendGiftEntity;
import com.jiujun.voice.modules.caller.iface.AbstractSendGiftCaller;
/**
 * @author Coody
 *
 */
@Component
public class SendTopperCaller extends AbstractSendGiftCaller {

	@Resource
	UserSendTopperService userSendTopperService;
	@Resource
	GiftInfoService giftInfoService;

	/**
	 * 赠送礼物榜单统计器
	 */
	@Override
	public void execute(SendGiftEntity entity) {

		List<UserSendTopper> toppers = new ArrayList<UserSendTopper>();

		GiftInfo gift = giftInfoService.getGiftInfo(entity.getGiftId());
		Integer[] types = new Integer[] { 0, 1, 2, 3 };
		String[] fields = new String[] { "fromUserId", "giftId", "toUserId", "roomId" };
		// 统计用户送日周月总榜
		for (Integer type : types) {
			try {
				UserSendTopper topper = new UserSendTopper();
				topper.setDayCode(getDayCode(type));
				topper.setExp(gift.getPrice().longValue());
				topper.setFromUserId(entity.getFromUserId());
				topper.setGiftId(entity.getGiftId());
				topper.setNum(entity.getNum());
				topper.setRoomId(entity.getRoomId());
				topper.setToUserId(entity.getToUserId());
				// 0总榜，1日榜 2周榜 3月榜
				topper.setType(type);
				toppers.add(topper);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (Integer type : types) {
			List<String> express = getExpression(fields.length, fields.length);
			for (String expres : express) {
				try {
					String[] element = StringUtil.splitString(expres);
					UserSendTopper topper = new UserSendTopper();
					topper.setDayCode(getDayCode(type));
					topper.setExp(gift.getPrice().longValue());
					topper.setFromUserId(entity.getFromUserId());
					topper.setGiftId(entity.getGiftId());
					topper.setNum(entity.getNum());
					topper.setRoomId(entity.getRoomId());
					topper.setToUserId(entity.getToUserId());
					// 0总榜，1日榜 2周榜 3月榜
					topper.setType(type);
					for (int i = 0; i < element.length; i++) {
						if (!"0".equals(element[i])) {
							continue;
						}
						String fieldName = fields[i];
						PropertUtil.setFieldValue(topper, fieldName, "0");
					}
					toppers.add(topper);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		userSendTopperService.saveUserSendToppers(toppers);
	}

	/**
	 * 
	 * @param type
	 *            0总榜，1日榜 2周榜 3月榜
	 * @return
	 */
	private static String getDayCode(Integer type) {
		if (type == 0) {
			return "0";
		}
		if (type == 1) {
			return DateUtils.getDayCode();
		}
		if (type == 2) {
			return DateUtils.getWeekCode(0);
		}
		if (type == 3) {
			return DateUtils.getMonthCode(0);
		}
		return "0";
	}

	public static void main(String[] args) {
		System.out.println(getExpression(4, 4).size());
	}

	private static List<String> getExpression(Integer membership, Integer length) {
		String value = formatLength("", length);
		Integer num = Integer.parseInt(value, 2);
		String end = StringUtil.getByMosaicChr("1", "", length);
		List<String> express = new ArrayList<String>();
		while (!value.equals(end)) {
			value = Integer.toString(num, 2);
			value = formatLength(value, 4);
			express.add(value);
			num++;

		}
		return express;
	}

	private static String formatLength(String v, Integer length) {
		while (v.length() < length) {
			v = "0" + v;
		}
		return v;
	}
}
