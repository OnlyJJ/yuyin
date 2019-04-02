package com.jiujun.voice.common.constants;

/**
 * 缓存KEY常量 ，C1代表版本前缀，必须要有
 * 
 * @author Coody
 *
 */
public class CacheConstants {
	
	
	/**
	 * 用户ID和手机临时关系
	 */
	public static final String USER_MOBILE_MAPPER="V2:USER:MOBILE_MAPPER";
	/**
	 * 用户相册缓存
	 */
	public static final String USER_ALBUM_LIST = "V2:USER:ALBUM_LIST";
	/**
	 * 用户登录记录缓存
	 */
	public static final String USER_LOGIN_RECORD = "V2:USER:LOGIN_RECORD";
	/**
	 * 好友申请消息
	 */
	public static final String RELATION_QUEUE = "V2:RELATION:QUEUE";
	/**
	 * 好友列表
	 */
	public static final String RELATION_LIST = "V2:RELATION:LIST";
	/**
	 * 好友关系
	 */
	public static final String RELATION_INFO = "V2:RELATION:INFO";
	/**
	 * 融云消息队列
	 */
	public static final String RONG_CLOUD_MESSAGE_QUEUE = "V2:RONG_CLOUD:MESSAGE_QUEUE";
	/**
	 * 用户AUTH授权Key
	 */
	public static final String USER_VERCODE_AUTHTOKEN = "V2:USER:VERCODE:AUTHTOKEN";
	/**
	 * 礼物列表
	 */
	public static final String GIFT_LIST = "V2:GIFT:LIST";
	/**
	 * 礼物详情
	 */
	public static final String GIFT_INFO = "V2:GIFT:INFO";
	/**
	 * 赠送礼物队列
	 */
	public static final String CALLER_SENDGIFT_QUEUE = "V2:CALLER:SENDGIFT_QUEUE";
	
	/**
	 * 充值队列
	 */
	public static final String CALLER_PAY_QUEUE = "V2:CALLER:PAY_QUEUE";
	/**
	 * 字典信息
	 */
	public static final String DIC_INFO = "V2:DIC:INFO";
	/**
	 * 文档出参
	 */
	public static final String DOC_OUTPUT = "V2:DOC:OUTPUT";
	/**
	 * 文档入参
	 */
	public static final String DOC_INPUT = "V2:DOC:INPUT";
	/**
	 * 文档信息
	 */
	public static final String DOC_INFO = "V2:DOC:INFO";
	/**
	 * 用户基础信息
	 */
	public static final String USER_INFO = "V2:USER:INFO";
	/**
	 * 用户账务信息
	 */
	public static final String USER_ACCOUNT = "V2:USER:ACCOUNT";
	/**
	 * 用户登录Token
	 */
	public static final String USER_TOKEN = "V2:USER:TOKEN";
	/**
	 * 定时任务控制器
	 */
	public static final String TASK_MAP = "V2:TASK_MAP";
	/**
	 * 验证码
	 */
	public static final String USER_VERCODE = "V2:USER:VERCODE";
	/**
	 * 验证码次数
	 */
	public static final String USER_VERCODE_CHECK = "V2:USER:VERCODE:CHECK";
	/**
	 * 房间基本信息
	 */
	public static final String ROOM_INFO = "V2:ROOM:INFO";
	/**
	 * 个人房间
	 */
	public static final String PERSON_ROOM = "V2:PERSON:ROOM";
	/**
	 * 房间个人信息
	 */
	public static final String MEMBER_INFO = "V2:MEMBER:INFO";
	/**
	 * 房间成员有序列表缓存
	 */
	public static final String MEMBER_SORT_DATA = "V2:MEMBER:SORT:DATA:";
	/**
	 * 房间管理员缓存
	 */
	public static final String ROOM_MANAGER = "V2:ROOM:MANAGER";
	/**
	 * 用户房间行为记录缓存（禁言，禁麦，踢出房间）
	 */
	public static final String ROOM_USER_BEHAVIOR = "V2:ROOM:BEHAVIOR";
	/**
	 * 房间麦克风列表缓存
	 */
	public static final String ROOM_MIC_LIST = "V2:ROOM:MIC:LIST";
	/**
	 * 房间具体麦位信息
	 */
	public static final String ROOM_MIC_SEAT = "V2:ROOM:MIC:SEAT";
	/**
	 * 用户所在房间
	 */
	public static final String USER_ONLINE_ROOM = "V2:USER:ONLINE:ROOM:";
	/**
	 * 用户上麦信息
	 */
	public static final String USER_MIC_INFO = "V2:USER:MIC:INFO";
	/**
	 * 有序的可显示和搜索的房间列表（z-set，member为roomId）
	 */
	public static final String ACTIVE_ROOM_SORT = "V2:ACTIVE:ROOM:SORT";
	/**
	 * 激活房间信息缓存
	 */
	public static final String ACTIVE_ROOM_INFO = "V2:ACTIVE:ROOM:INFO";
	/**
	 * 所有的激活房间
	 */
	public static final String ACTIVE_ROOM_ALL = "V2:ACTIVE:ROOM:ALL";
	/**
	 * 财富等级配置缓存
	 */
	public static final String RICH_LEVEL_ALL = "V2:RICH:LEVEL:ALL";
	/**
	 * 魅力等级配置缓存
	 */
	public static final String CHARM_LEVEL_ALL = "V2:CHARM:LEGEL:ALL";
	/**
	 * 房间送礼记录信息缓存
	 */
	public static final String ROOM_GIFT_RECORD = "V2:R:GIFT:RECORD:";
	/**
	 * 房间送礼记录-日记录
	 */
	public static final String ROOM_GIFT_RECORD_DAY = "V2:R:GIFT:DAY:";
	/**
	 * 房间送礼记录-周记录
	 */
	public static final String ROOM_GIFT_RECORD_WEEK = "V2:R:GIFT:WEEK:";
	/**
	 * 麦位状态缓存
	 */
	public static final String ROOM_MIC_STATUS = "V2:ROOM:MIC:STATUS";
	/**
	 * 用户个人所有收礼记录
	 */
	public static final String USER_RECIVEGIFT_RECORD = "V2:GIFT:USER:RECIVE:";

	/**
	 * 摇骰子资格用户列表
	 */
	public static final String DICE_QUALER_LIST = "V2:DICE:QUALER:LIST";

	/**
	 * 邀请奖励缓存
	 */
	public static final String INVITE_PRIZE_INFO="V2:INVITE:PRIZE_INFO";
	
	/**
	 * 用户背包
	 */
	public static final String BACKPACK_INFO="V2:BACKPACK:INFO";
	
	
	/**
	 * 房间音乐表
	 */
	public static final String ROOM_MUSICS="V2:ROOM:MUSICS";
	
	/**
	 * 苹果产品支付列表
	 */
	public static final String PAY_APPLE_PRODUCTS="PAY:APPLE_PRODUCTS";
	
	/**
	 * REDIS与后台的交互渠道
	 */
	public static final String VOICE_PUBSUB="VOICE:PUBSUB";
}
