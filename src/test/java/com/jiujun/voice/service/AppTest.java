package com.jiujun.voice.service;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

	public static void main(String[] args) throws Exception {
	}
	public static class UserInfoTest {

		public Integer state = 0;
		
		private List<Object> list=new ArrayList<Object>(409600);

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		public List<Object> getList() {
			return list;
		}

		public void setList(List<Object> list) {
			this.list = list;
		}

		public void update(int id, String username) {

		}
	}
}