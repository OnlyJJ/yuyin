package com.jiujun.voice.common.dao;


/**
 * dao基类
 * @author Shao.x
 * @date 2018年11月16日
 * @param <R>
 */
public interface BaseMapper<R> {

	R getById(Object id);

	int insert(R vo);

	int update(R vo);

	void removeById(Object id);

}
