package com.jiujun.voice.common.jdbc.process;

import java.util.List;

import com.jiujun.voice.common.container.BeanContainer;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.jdbc.util.JdbcUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public abstract class AbstractPagerAble {

	private JdbcHandle jdbcHandle;

	protected Class<?> clazz;

	protected String sql;

	protected Object[] paras;

	protected Pager pager;

	public AbstractPagerAble(Class<?> clazz, String sql, Pager pager, Object... paras) {
		super();
		this.clazz = clazz;
		this.sql = sql;
		this.paras = paras;
		this.pager = pager;
	}

	public AbstractPagerAble(String sql, Pager pager, Object... paras) {
		super();
		this.sql = sql;
		this.paras = paras;
		this.pager = pager;
	}

	public AbstractPagerAble(String sql, Pager pager) {
		super();
		this.sql = sql;
		this.pager = pager;
	}

	public abstract <T> List<T> query();

	public Pager invoke() {
		this.jdbcHandle = BeanContainer.getBean(JdbcHandle.class);
		Integer count = jdbcHandle.getCount(sql, paras);
		if (count == 0) {
			return new Pager();
		}
		sql += JdbcUtil.buildPagerSQL(pager);
		List<Object> data = query();
		pager.setCount(count);
		pager.setData(data);
		return pager;
	}

	public JdbcHandle getJdbcHandle() {
		return jdbcHandle;
	}

	public void setJdbcHandle(JdbcHandle jdbcHandle) {
		this.jdbcHandle = jdbcHandle;
	}

}
