package com.jiujun.voice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.exception.VoiceException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;


/**
 * JDBC测试类
 * @author Coody
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class JdbcTest {

	/**
	 * JDBC核心说明，
	 * 1、不论执行什么语句，最终以查询结果为准。
	 * 2、当查询一个对象时，凡是对象字段，跟查询结果返回的字段名一致的，均会自动装载
	 * 3、当查询一个数值时，凡是响应值能归属该类型的，均会自动转换
	 * 4、当数据库字段与对象字段不一致时，使用DBColumn注解在对象的字段上标记该字段对应的实际列名，将自动装载。
	 * 5、当存在复杂的跨表查询，方式一：建立查询结果列名对应的复合VO、方式二：使用List<Map<String,Object>>
	 * 6、特别注意：JDBC操作的一切对象、VO等，均归属BaseModel分支下，需继承或间接继承BaseModel
	 */
	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 单表测试
	 */
	@Test
	public void sigleTest() {
		//查询一个列表
		String sql="select * from t_user_info ";
		List<UserInfo> userInfos=jdbcHandle.query(UserInfo.class, sql);
		System.out.println("查询结果:"+JSON.toJSONString(userInfos));
		//带参查询一个列表
		sql="select * from t_user_info where id>?";
		userInfos=jdbcHandle.query(UserInfo.class, sql,0);
		System.out.println("查询结果:"+JSON.toJSONString(userInfos));
		//查询一条数据
		sql="select * from t_user_info limit 1";
		UserInfo userInfo=jdbcHandle.queryFirst(UserInfo.class, sql);
		System.out.println("查询结果:"+JSON.toJSONString(userInfo));
		//带参查询一条数据
		sql="select * from t_user_info where user=?";
		userInfo=jdbcHandle.queryFirst(UserInfo.class, sql,"admin");
		System.out.println("查询结果:"+JSON.toJSONString(userInfo));
	}
	/**
	 * 统计、平均数等测试
	 */
	@Test
	public void statiscTest() {
		//查询一个列表
		String sql="select count(*) from t_user_info ";
		Integer count=jdbcHandle.queryFirst(Integer.class, sql);
		System.out.println("查询结果:"+count);
		sql="select avg(id) from t_user_info ";
		Double avg=jdbcHandle.queryFirst(Double.class, sql);
		System.out.println("查询结果:"+avg);
	}
	/**
	 * 跨表查询
	 */
	@Test
	public void multipleTest() {
		//查询一个列表
//		String sql="select u.*,a.* from t_user_info u,t_user_account a where a.userId=u.id";
//		List<UserAndAccountVO> userAndAccountVOs=jdbcHandle.query(UserAndAccountVO.class, sql);
//		System.out.println("查询结果:"+JSON.toJSONString(userAndAccountVOs));
//		//查询一条数据,带参
//		sql="select u.*,a.* from t_user_info u,t_user_account a where a.userId=u.id and u.user=?";
//		UserAndAccountVO userAndAccountVO=jdbcHandle.queryFirst(UserAndAccountVO.class, sql,"admin");
//		System.out.println(JSON.toJSONString(userAndAccountVO));
	}
	
	/**
	 * 更新操作
	 */
	public void updateTest() {
		String sql="update t_user_info set pwd=? where id=?";
		Long code=jdbcHandle.update(sql,"000000",1);
		System.out.println("影响行数:"+code);
	}
	
	/**
	 * 注，insert操作，不抛出异常，默认返回自增主键，请根据返回值进行判断。如果小于0，则视为失败，需抛出异常或处理异常。需在UserInfo对象上使用@DBTable("t_user_info")指定表名
	 */
	@Test
	public void insertTest() {
		UserInfo userInfo=new UserInfo();
		userInfo.setName("test"+StringUtil.getRanDom(1, 9999999));
		userInfo.setAge(18);
		Long userId=jdbcHandle.insert(userInfo);
		if(userId<1) {
			throw new VoiceException("插入用户失败");
		}
		System.out.println("插入用户成功，用户ID为："+userId);
	}
	
	/**
	 * 保存或更新，可用于update操作，也可用于insert操作,需在UserInfo对象上使用@DBTable("t_user_info")指定表名
	 */
	@Test
	public void saveTest() {
		//插入用户数据
		UserInfo userInfo=new UserInfo();
		userInfo.setName("test"+StringUtil.getRanDom(1, 9999999));
		userInfo.setAge(18);
		//返回影响行数或自增主键，当userInfo对象没有为主键(或唯一索引)赋值,则插入数据
		Long userId=jdbcHandle.saveOrUpdate(userInfo);
		if(userId<1) {
			throw new VoiceException("插入用户失败");
		}
		System.out.println("插入用户成功，用户ID为："+userId);
		
		//userInfo.setId(userId);
		//更新用户数据，根据userId进行update操作
		userInfo.setAge(19);
		Long code=jdbcHandle.saveOrUpdate(userInfo);
		if(code<1) {
			throw new VoiceException("更新用户失败");
		}
		System.out.println("更新用户成功，影响行数："+code);
		
		//在未知情况下，我们经常需要初始化XX表数据，而此时，该数据可能已经存在，故此，我们需要通过分布式锁确保数据安全，然而。saveOrUpdate可以规避这个锁
		UserAccount account=new UserAccount();
		//用户充值十元，但是此时，我们不确定用户账务数据是否已经初始化，同时受到影响的数据还包括积分：score
//		account.setUserId(userId);
//		account.setAccount(10);
//		account.setScore(0);
		//我们可以将account score设置为递增字段
		code=jdbcHandle.saveOrUpdate(account,"account","score");
		//以上语句执行，表示当存在数据时，account=account+传入的account，score=score+传入的score，根据account对象的赋值，account+10,score+0。当不存在数据时，则插入
		System.out.println("更新用户账务信息成功，影响行数："+code);
		
	}
	
	
	/**
	 * 删除操作
	 */
	public void deleteTest() {
		String sql="delete from t_user_info where id=?";
		Long code=jdbcHandle.update(sql,1);
		System.out.println("影响行数:"+code);
	}
	
	
	public static void main(String[] args) {
		List<Object> list =new ArrayList<Object>();
		while(true){
			list.add(new ArrayList<Object>(64*64));
		}
	}
}
