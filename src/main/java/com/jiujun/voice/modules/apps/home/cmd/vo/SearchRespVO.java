package com.jiujun.voice.modules.apps.home.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.modules.apps.home.cmd.schema.SearchResultSchema;

/**
 *  搜索响应实体
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SearchRespVO extends BaseRespVO {
	
	private List<SearchResultSchema> data;

	public List<SearchResultSchema> getData() {
		return data;
	}

	public void setData(List<SearchResultSchema> data) {
		this.data = data;
	}

}
