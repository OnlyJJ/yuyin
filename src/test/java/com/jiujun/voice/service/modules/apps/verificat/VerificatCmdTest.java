package com.jiujun.voice.service.modules.apps.verificat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.modules.apps.verificat.cmd.vo.SendCodeReqVO;
import com.jiujun.voice.service.modules.apps.BaseCmdTest;

@RunWith(SpringRunner.class)
//@SpringBootTest( classes = VoiceApplication.class)
public class VerificatCmdTest extends BaseCmdTest{

	/**
	 * 发送验证码接口测试
	 */
	@Test
	public void sendCode() {
		
		SendCodeReqVO req =new SendCodeReqVO();
		req.setAccount("644556636@qq.com");
		BaseRespVO resp=execute("verificat.sendCode", req, BaseRespVO.class );
		System.out.println(resp.toJson());
	}
}
