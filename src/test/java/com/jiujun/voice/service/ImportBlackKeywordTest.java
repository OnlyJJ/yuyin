package com.jiujun.voice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.model.DBModel;
import com.jiujun.voice.common.utils.FileUtils;
import com.jiujun.voice.common.utils.StringUtil;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class ImportBlackKeywordTest {

	
	@Resource
	JdbcHandle jdbcHandle;
	
	@Test
	public void importKeyWord(){
		String context=FileUtils.readFile("e:/data.txt");
		String[] keywords=context.split("\r\n");
		
		List<BlackKeyword> list=new ArrayList<ImportBlackKeywordTest.BlackKeyword>();
		for(String keyword:keywords){
			
			BlackKeyword line=new BlackKeyword();
			line.setKeyword(keyword);
			list.add(line);
			if(list.size()>500){
				jdbcHandle.batchSaveOrUpdate(list);
				list.clear();
			}
		}
		if(!StringUtil.isNullOrEmpty(list)){
			jdbcHandle.batchSaveOrUpdate(list);
			list.clear();
		}
		
	}
	
	
	@SuppressWarnings("serial")
	@DBTable("t_black_keyword")
	public static class BlackKeyword extends DBModel{
		
		private String keyword;

		public String getKeyword() {
			return keyword;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
		
		

	}
}
