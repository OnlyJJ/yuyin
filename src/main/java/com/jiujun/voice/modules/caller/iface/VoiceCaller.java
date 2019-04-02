package com.jiujun.voice.modules.caller.iface;

import com.jiujun.voice.common.model.RootModel;

/**
 * 调度器
 * 
 * @author Coody
 *
 * @param <T>
 */
public interface VoiceCaller<T extends RootModel> {

	public void execute(T entity);
}
