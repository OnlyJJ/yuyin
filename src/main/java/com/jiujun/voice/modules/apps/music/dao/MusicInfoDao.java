package com.jiujun.voice.modules.apps.music.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.jdbc.process.AbstractPagerAble;
import com.jiujun.voice.common.jdbc.process.PagerAble;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.music.domain.MusicInfo;

@Repository
public class MusicInfoDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 根据签名获取文件列表
	 * 
	 * @param sign
	 * @param size
	 * @return
	 */
	@CacheWrite
	public MusicInfo getMusicBySign(String sign, Long size) {
		String sql = "select * from t_music_info where fileSign=? and size=? limit 1";
		return jdbcHandle.queryFirst(MusicInfo.class, sql, sign, size);
	}

	@CacheWrite
	public MusicInfo getMusicById(Long musicId) {
		String sql = "select * from t_music_info where id=? limit 1";
		return jdbcHandle.queryFirst(MusicInfo.class, sql, musicId);
	}

	/**
	 * 音乐搜索接口
	 * 
	 * @param keyWord
	 *            关键词
	 * @return
	 */
	public Pager searchMusicPager(String keyWord, Pager pager) {

		if (StringUtil.isNullOrEmpty(keyWord)) {
			String sql = "select * from t_music_info order by playNum desc ,downNum desc, createTime desc ";
			return new PagerAble(MusicInfo.class, sql, pager).invoke();
		}
		keyWord = "%" + keyWord + "%";
		StringBuilder sql = new StringBuilder("select * from t_music_info where 1=1 ");
		sql.append(" and (title like ? or author like ?)");
		sql.append(" order by playNum desc ,downNum desc, createTime desc ");
		return new AbstractPagerAble(MusicInfo.class, sql.toString(), pager, keyWord, keyWord) {
			@Override
			public <T> List<T> query() {
				return jdbcHandle.query(clazz, sql, paras);
			}
		}.invoke();
	}

	/**
	 * 分页加载热门音乐列表
	 * 
	 * @param pager
	 * @return
	 */
	public Pager searchMusicPager(Pager pager) {

		String sql = "select * from t_music_info order by playNum desc ,downNum desc, createTime desc ";
		return new PagerAble(MusicInfo.class, sql, pager).invoke();
	}

	public void pushDownNum(Long musicId) {
		String sql = "update t_music_info set downNum = downNum + 1 where id=? limit 1";
		jdbcHandle.update(sql, musicId);
	}

	public Long addMusic(MusicInfo music) {
		return jdbcHandle.insert(music);
	}

	public Long delMusic(Long musicId) {
		String sql = "delete from t_music_info where id=? limit 1";
		return jdbcHandle.update(sql, musicId);
	}

	/**
	 * 分页查询用户上传的音乐列表
	 */
	public Pager getUserMusicPager(String userId, Pager pager) {
		String sql = "select * from t_music_info where userId=?  order by playNum desc ,downNum desc, createTime desc  ";
		return new PagerAble(MusicInfo.class, sql, pager, userId).invoke();
	}
}
