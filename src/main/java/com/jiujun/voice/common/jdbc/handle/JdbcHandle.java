package com.jiujun.voice.common.jdbc.handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.filting.FiltingProcess;
import com.jiujun.voice.common.jdbc.entity.BeanDataBiller;
import com.jiujun.voice.common.jdbc.exception.JdbcException;
import com.jiujun.voice.common.jdbc.exception.TableNotFoundException;
import com.jiujun.voice.common.jdbc.factory.BeanDataBillerFactory;
import com.jiujun.voice.common.jdbc.factory.BeanDataBillerFactory.DataFieldEntity;
import com.jiujun.voice.common.jdbc.source.DynamicDataSource;
import com.jiujun.voice.common.jdbc.util.JdbcUtil;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.model.DBModel;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.common.utils.property.UnsafeUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Repository
@SuppressWarnings("unchecked")
public class JdbcHandle {

	@Resource
	public JdbcTemplate jdbcTemplate;

	/**
	 * 执行SQL语句获得任意类型结果
	 * 
	 * @param clazz
	 *            返回类型
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数列表
	 * @return
	 */
	public <T> List<T> query(Class<?> clazz, String sql, Object... paras) {
		List<Map<String, Object>> records = query(sql, paras);
		if (StringUtil.isNullOrEmpty(records)) {
			return null;
		}
		if (DBModel.class.isAssignableFrom(clazz)) {
			return JdbcUtil.buildModels((Class<? extends DBModel>) clazz, records);
		}
		List<Object> list = new ArrayList<Object>();
		for (Map<String, Object> line : records) {
			if (StringUtil.isNullOrEmpty(line)) {
				continue;
			}
			Map.Entry<String, Object> entry = line.entrySet().iterator().next();
			Object value = entry.getValue();
			if (!StringUtil.isNullOrEmpty(value)) {
				list.add(PropertUtil.parseValue(value, clazz));
				continue;
			}
			String fieldName = entry.getKey().toLowerCase();
			if (fieldName.startsWith("count(") || fieldName.startsWith("sum(") || fieldName.startsWith("avg(")
					|| fieldName.startsWith("abs(") || fieldName.startsWith("max(") || fieldName.startsWith("min(")) {
				list.add(PropertUtil.parseValue(0, clazz));
				continue;
			}
			continue;
		}
		return (List<T>) list;
	}

	public <T> List<T> query(Class<?> clazz, String sql) {
		return query(clazz, sql, (Object[]) null);
	}

	/**
	 * 重载几个方法，用于编译器避免警告
	 * 
	 * @param clazz
	 * @param sql
	 * @param paras
	 * @return
	 */
	public <T> List<T> query(Class<?> clazz, String sql, Integer... paras) {
		return query(clazz, sql, (Object[]) paras);
	}

	/**
	 * 重载几个方法，用于编译器避免警告
	 * 
	 * @param clazz
	 * @param sql
	 * @param paras
	 * @return
	 */
	public <T> List<T> query(Class<?> clazz, String sql, String... paras) {
		return query(clazz, sql, (Object[]) paras);
	}

	/**
	 * 重载几个方法，用于编译器避免警告
	 * 
	 * @param clazz
	 * @param sql
	 * @param paras
	 * @return
	 */
	public <T> List<T> query(Class<?> clazz, String sql, Long... paras) {
		return query(clazz, sql, (Object[]) paras);
	}

	/**
	 * 执行SQL语句获得任意类型结果
	 * 
	 * @param clazz
	 *            返回类型
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数列表
	 * @return
	 */
	public <T> List<T> query(Class<?> clazz, String sql, List<?> paras) {
		return query(clazz, sql, paras.toArray());
	}

	/**
	 * 查询功能区 -start
	 */
	/**
	 * 执行SQL语句获得任意类型结果
	 * 
	 * @param clazz
	 *            返回类型
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数列表
	 * @return
	 */
	public <T> T queryFirst(Class<?> clazz, String sql, Object... paras) {
		List<T> list = query(clazz, sql, paras);
		if (StringUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	public <T> T queryFirst(Class<?> clazz, String sql) {
		return queryFirst(clazz, sql, (Object[]) null);
	}

	/**
	 * 重载几个方法，用于编译器避免警告
	 * 
	 * @param clazz
	 *            返回类型
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数列表
	 * @return
	 */
	public <T> T queryFirst(Class<?> clazz, String sql, Integer... paras) {
		return queryFirst(clazz, sql, (Object[]) paras);
	}

	/**
	 * 重载几个方法，用于编译器避免警告
	 * 
	 * @param clazz
	 *            返回类型
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数列表
	 * @return
	 */
	public <T> T queryFirst(Class<?> clazz, String sql, Long... paras) {
		return queryFirst(clazz, sql, (Object[]) paras);
	}

	/**
	 * 重载几个方法，用于编译器避免警告
	 * 
	 * @param clazz
	 *            返回类型
	 * @param sql
	 *            sql语句
	 * @param paras
	 *            参数列表
	 * @return
	 */
	public <T> T queryFirst(Class<?> clazz, String sql, String... paras) {
		return queryFirst(clazz, sql, (Object[]) paras);
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> query(String sql) {
		return baseQuery(sql, new Object[] {});
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @param paraMap
	 *            参数map容器
	 * @return 结果集
	 */
	public List<Map<String, Object>> query(String sql, Object... paras) {
		return baseQuery(sql, paras);
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	public Map<String, Object> queryFirst(String sql) {
		return queryFirst(sql, new Object[] {});
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	public Map<String, Object> queryFirst(String sql, Object... paras) {
		if (!sql.toLowerCase().contains("limit")) {
			sql = sql + " limit 1";
		}
		List<Map<String, Object>> list = query(sql, paras);
		if (StringUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 根据语句和条件查询总记录数
	 * 
	 * @param sql
	 *            语句
	 * @param map
	 *            条件容器
	 * @return
	 */
	public Integer getCount(String sql, Object... params) {
		sql = formatCountSql(sql);
		Integer count = queryFirst(Integer.class, sql, params);
		return count;
	}

	/**
	 * 批量保存或更新
	 * 
	 * @param obj
	 *            欲保存的对象
	 * @param addFields
	 *            当数据存在时累加的字段
	 * @return 成功数
	 */
	public Integer batchSaveOrUpdate(List<? extends DBModel> objs) {
		return batchSaveOrUpdate(objs, new String[] {});
	}

	public Integer batchSaveOrUpdate(List<? extends DBModel> models, String... addFields) {

		/**
		 * Bean归类
		 */
		Map<Class<? extends DBModel>, List<DBModel>> modelClazzGroups = new HashMap<Class<? extends DBModel>, List<DBModel>>();
		for (DBModel model : models) {
			if (StringUtil.isNullOrEmpty(model)) {
				continue;
			}
			if (!modelClazzGroups.containsKey(model.getClass())) {
				modelClazzGroups.put(model.getClass(), new ArrayList<DBModel>());
			}
			modelClazzGroups.get(model.getClass()).add(model);
		}
		Map<String, List<Object[]>> batchMap = new HashMap<String, List<Object[]>>(16);
		for (Class<?> clazz : modelClazzGroups.keySet()) {
			List<DBModel> modelGroup = modelClazzGroups.get(clazz);
			BeanDataBiller beanDataBiller = BeanDataBillerFactory.getBiller(clazz);
			if (StringUtil.isNullOrEmpty(beanDataBiller.getFields())) {
				return -1;
			}
			if (!beanDataBiller.getOnekeyWrite()) {
				throw new JdbcException(clazz.getSimpleName() + "所在表不允许直接保存");
			}
			for (DBModel model : modelGroup) {
				// 拼接SQL语句
				StringBuilder sqlBuilder = new StringBuilder(
						MessageFormat.format("insert into {0} set ", beanDataBiller.getTable()));
				List<Object> paras = new ArrayList<Object>();
				String diySql = JdbcUtil.buildBeanSetSql(beanDataBiller, model, paras);
				if (StringUtil.isNullOrEmpty(diySql)) {
					continue;
				}
				sqlBuilder.append(diySql);
				sqlBuilder.append(" ON DUPLICATE KEY UPDATE ");
				diySql = JdbcUtil.buildBeanSetSql(beanDataBiller, model, paras, addFields);
				sqlBuilder.append(diySql);
				if (!batchMap.containsKey(sqlBuilder.toString())) {
					batchMap.put(sqlBuilder.toString(), new ArrayList<Object[]>());
				}
				batchMap.get(sqlBuilder.toString()).add(paras.toArray());
			}
		}
		if (StringUtil.isNullOrEmpty(batchMap)) {
			return 0;
		}
		// 指定主库
		DynamicDataSource.setDataSourceFlag(DynamicDataSource.MASTER_FLAG);
		Integer result = 0;
		for (String sql : batchMap.keySet()) {
			int[] codes = jdbcTemplate.batchUpdate(sql, batchMap.get(sql));
			for (int code : codes) {
				if (code < 0) {
					continue;
				}
				result++;
			}
		}
		return result;
	}

	/**
	 * 根据对象条件进行插入
	 * 
	 * @param obj
	 * @return
	 */

	public Long insert(DBModel model) {
		try {
			if (StringUtil.isNullOrEmpty(model)) {
				return -1L;
			}
			BeanDataBiller beanDataBiller = BeanDataBillerFactory.getBiller(model.getClass());
			// 获取属性列表
			if (StringUtil.isNullOrEmpty(beanDataBiller.getFields())) {
				return -1L;
			}
			// 拼接SQL语句
			StringBuilder sqlBuilder = new StringBuilder(
					MessageFormat.format("insert into {0} set ", beanDataBiller.getTable()));
			Map<Integer, Object> params = new TreeMap<Integer, Object>();
			for (DataFieldEntity field : beanDataBiller.getFields().keySet()) {
				String columnName = beanDataBiller.getFields().get(field);
				if (StringUtil.isNullOrEmpty(columnName)) {
					continue;
				}
				Object fieldValue = UnsafeUtil.getFieldValue(model, field);
				// Object fieldValue =field.getSourceField().get(model);
				if (fieldValue == null) {
					continue;
				}
				if (String.class.isAssignableFrom(field.getFieldType())) {
					if (field.getFilting() != null) {
						if (FiltingProcess.getFilting((String) fieldValue) != null) {
							throw new CmdException(ErrorCode.ERROR_1056.getCode(), field.getFiltingError());
						}
					}
				}
				sqlBuilder.append("`").append(columnName).append("`").append("=?");
				params.put(params.size() + 1, fieldValue);
				sqlBuilder.append(",");
			}
			sqlBuilder = new StringBuilder(sqlBuilder.toString().substring(0, sqlBuilder.toString().length() - 1));
			return baseUpdate(sqlBuilder.toString(), params.values().toArray());

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			PrintException.printException(e);
		}
		return -1L;
	}

	/**
	 * 根据对象进行插入或更新
	 * 
	 * @param obj
	 * @param priKeyName
	 * @return
	 */
	public Long saveOrUpdate(DBModel model) {
		return saveOrUpdate(model, new String[] {});
	}

	/**
	 * 保存或更新
	 * 
	 * @param obj
	 *            欲保存的对象
	 * @param addFields
	 *            当数据存在时累加的字段
	 * @return
	 */
	public Long saveOrUpdate(DBModel model, String... addFields) {
		if (model == null) {
			return -1L;
		}
		BeanDataBiller beanDataBiller = BeanDataBillerFactory.getBiller(model.getClass());
		if (!beanDataBiller.getOnekeyWrite()) {
			throw new JdbcException(model.getClass().getSimpleName() + "所在表不允许直接保存");
		}
		// 拼接SQL语句
		StringBuilder sqlBuilder = new StringBuilder(
				MessageFormat.format("insert into {0} set ", beanDataBiller.getTable()));
		List<Object> paras = new ArrayList<Object>();
		String diySql = JdbcUtil.buildBeanSetSql(beanDataBiller, model, paras);
		if (StringUtil.isNullOrEmpty(diySql)) {
			return -1L;
		}
		sqlBuilder.append(diySql);
		sqlBuilder.append(" ON DUPLICATE KEY UPDATE ");
		diySql = JdbcUtil.buildBeanSetSql(beanDataBiller, model, paras, addFields);
		sqlBuilder.append(diySql);
		return baseUpdate(sqlBuilder.toString(), paras.toArray());
	}

	/**
	 * 更新操作
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	public Long update(String sql, Object... objs) {
		Map<Integer, Object> map = new HashMap<Integer, Object>(16);
		for (Object obj : objs) {
			map.put(map.size() + 1, obj);
		}
		return baseUpdate(sql, map.values().toArray());
	}

	/**
	 * 更新操作
	 * 
	 * @param sql
	 * @return
	 */
	public Long update(String sql) {
		return baseUpdate(sql, new Object[] {});
	}

	/**
	 * 执行SQL查询语句
	 * 
	 * @param sql
	 * @param paraMap
	 *            参数map容器
	 * @return 结果集
	 */
	private List<Map<String, Object>> baseQuery(String sql, Object... paras) {
		try {
			if (!LogUtil.isProd()) {
				LogUtil.logger.debug("[执行语句:" + formatSql(sql, paras) + "]");
			}
			List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, paras);
			return result;
		} catch (Exception e) {
			PrintException.printException(e);
			throw new JdbcException("语句执行出错", e);
		}
	}

	/**
	 * 执行SQL更新语句
	 * 
	 * @param sql
	 *            语句
	 * @param paraMap
	 *            参数
	 * @return
	 */
	private Long baseUpdate(final String sql, final Object... paras) {
		try {
			if (!LogUtil.isProd()) {
				LogUtil.logger.debug("[执行语句:" + formatSql(sql, paras) + "]");
			}
			if (!sql.toLowerCase().trim().startsWith("insert")) {
				return Long.valueOf(jdbcTemplate.update(sql, paras));
			}
			// 指定主库
			DynamicDataSource.setDataSourceFlag(DynamicDataSource.MASTER_FLAG);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			Integer code = jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
					for (int i = 0; i < paras.length; i++) {
						ps.setObject(i + 1, paras[i]);
					}
					return ps;
				}
			}, keyHolder);
			List<Map<String, Object>> list = keyHolder.getKeyList();
			if (StringUtil.isNullOrEmpty(list) || list.size() > 1) {
				return Long.valueOf(code);
			}
			if (StringUtil.isNullOrEmpty(list.get(0)) || list.get(0).size() > 1) {
				return Long.valueOf(code);
			}
			try {
				Long key = keyHolder.getKey().longValue();
				return key;
			} catch (Exception e) {
				PrintException.printException(e);
				return Long.valueOf(code);
			}
		} catch (Exception e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				return -1L;
			}
			if (e instanceof DuplicateKeyException) {
				return -1L;
			}
			String error = PrintException.getErrorStack(e);
			String errorLine = StringUtil.doMatcherFirst(error, TableNotFoundException.MATCHER);
			if (errorLine != null) {
				throw new TableNotFoundException(errorLine);
			}
			LogUtil.logger.error(error);
			return -1L;
		}
	}

	private String formatCountSql(String sql) {
		while (sql.indexOf("  ") > -1) {
			sql = sql.replace("  ", " ");
		}
		Integer formIndex = sql.toLowerCase().indexOf("from");
		if (formIndex > -1) {
			sql = sql.substring(formIndex, sql.length());
		}
		Integer orderIndex = sql.toLowerCase().indexOf("order by");
		if (orderIndex > -1) {
			sql = sql.substring(0, orderIndex);
		}
		Integer limitIndex = sql.toLowerCase().indexOf("limit");
		while (limitIndex > -1) {
			String firstSql = sql.substring(0, limitIndex);
			String lastSql = sql.substring(limitIndex);
			if (lastSql.indexOf(")") > -1) {
				lastSql = lastSql.substring(lastSql.indexOf(")"));
				firstSql = firstSql + lastSql;
			}
			sql = firstSql;
			limitIndex = sql.toLowerCase().indexOf("limit");
		}
		if (orderIndex > -1) {
			sql = sql.substring(0, orderIndex);
		}
		sql = "select count(*) " + sql;
		return sql;
	}

	private static String formatSql(String sql, Object... params) {
		sql += " ";
		String[] sqlRanks = sql.split("\\?");
		if (sqlRanks.length == 1) {
			return sql;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sqlRanks.length; i++) {
			sb.append(sqlRanks[i]);
			if (i != sqlRanks.length - 1) {
				try {
					Object value = params[i];
					if (!StringUtil.isNullOrEmpty(value)) {
						if (Date.class.isAssignableFrom(value.getClass())) {
							value = DateUtils.toString((Date) value, "yyyy-MM-dd HH:mm:ss");
						}
						if (String.class.isAssignableFrom(value.getClass())) {
							value = "'" + value + "'";
						}
					}
					sb.append(value);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return sb.toString();
	}
}
