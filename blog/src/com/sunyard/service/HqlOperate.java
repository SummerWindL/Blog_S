package com.sunyard.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface HqlOperate {
	public Integer getCountByHql(String hql);
	/**
	 * 通过hql查找数据
	 * @param hql
	 * @return
	 */
	public List getByHql(String hql);
	public <T> List<T> getByHql(String hql,Class<T> clazz);
	/**
	 *  通过hql查找数据
	 * @param hql		hql语句
	 * @param params	占位参数
	 * @return
	 */
	public List getByHql(String hql,Object...params);
	public <T> List<T> getByHql(String hql,Class<T> clazz,Object...params);
	/**
	 * 查找第一条数据
	 * @param hql
	 * @return
	 */
	public Object getByHqlFirst(String hql);
	public <T> T getByHqlFirst(String hql,Class<T> clazz);
	/**
	 * 查找第一条数据
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object getByHqlFirst(String hql,Object...params);
	public <T> T getByHqlFirst(String hql,Class<T> clazz,Object...params);
	
	/**
	 * 查找前top条数据
	 * @param hql
	 * @param top
	 * @return
	 */
//	public List getByHql(final String hql,final int top);
	/**
	 * 通过hql更新数据库
	 * @param hqls
	 */
	public void updateByHql(final String...hqls);
	/**
	 * 保存对象
	 * @param objs
	 */
	public void save(Object...objs);
	/**
	 * 更新对象
	 * @param objs
	 */
	public void merge(Object... objs);
	/**
	 * 删除对象
	 * @param objs
	 */
	public void delete(Object... objs);
	/**
	 * 保存list列表
	 * @param l
	 */
	public void saveList(List l);
	/**
	 * 通过ID获取对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T getById(Class<T> clazz,Serializable id);
	/**
	 * 通过sql更新数据集
	 * @param sqls
	 */
	public void updateBySql(final String...sqls);
	/**
	 *  通过hql查找数据
	 * @param hql		hql语句
	 * @param params	占位参数
	 * @return
	 */
	public List getByHqlParam(String hql,Object...params);
	/**
	 * 查找前top条数据
	 * @param hql
	 * @param top
	 * @return
	 */
	public List getByHqlTop(final String hql,final int top);
	/**
	 * 查找前top条数据
	 * @param hql			查询语句
	 * @param params		参数
	 * @param top			纪录数
	 * @return
	 */
	public List getByHqlParamTop(final String hql,final Object[] params,final int top);
	/**
	 * 更新数据库hql
	 * @param sql		hql语句
	 * @param params	参数
	 */
	public void updateByHqlParams(String hql,Object...params);
	/**
	 * 
	 * @param objs
	 */
	public void mergeList(List objs);
	/**               
	 * 删除数组
	 * @param list
	 */
	public void deleteList(List list);
	/**
	 * 更新列表
	 * @param l
	 */
	public void updateList(List l);
	/**
	 * 通过对象查询
	 * @param instance
	 * @return
	 */
	public List getByObj(Object instance);
	/**
	 * 通过对象查询第一条纪录
	 * @param instance
	 * @return
	 */
	public Object getByObjFirst(Object instance);
	
	/**
	 * 通过sql进行查询
	 * @param sql		sql语句
	 * @param args		占位参数
	 * @return
	 */
	public List getBySql(String sql,Object...args);
	
	public List getPagedByHql(final String hql,final int start,final int top);
	/**
	 * 通过sql进行查询	此方式对带下划线的字段做了特殊处理( aa_bb -> aaBb )
	 * @param <T>
	 * @param sql		sql语句
	 * @param clazz		返回类型
	 * @param args		占位参数
	 * @return
	 */
//	public <T extends Object> List<T> getBySqlEx(String sql,final Class<T> clazz,Object...args);
	/**
	 * 通过sql进行查询
	 * @param <T>
	 * @param sql		sql语句
	 * @param clazz		返回类型
	 * @param convert	要转化的字段		key: 数据库字段名（小写）　value:类字段名
	 * @param args		占位参数
	 * @return
	 */
//	public <T extends Object> List<T> getBySqlExSpec(String sql,final Class<T> clazz,final Map<String, String> convert,Object...args);
	/**
	 * 通过sql进行查询			此方式对带下划线的字段做了特殊处理( aa_bb -> aaBb )
	 * @param <T>
	 * @param sql		sql语句
	 * @param clazz		返回类型
	 * @param args		占位参数
	 * @return
	 */
//	public <T extends Object> T getBySqlFirst(String sql,final Class<T> clazz,Object...args);
	/**
	 * 通过sql进行查询
	 * @param <T>
	 * @param sql		sql语句
	 * @param clazz		返回类型
	 * @param convert	要转化的字段		key: 数据库字段名（小写）　value:类字段名
	 * @param args		占位参数
	 * @return
	 */
//	public <T extends Object> T getBySqlFirstSpec(String sql,final Class<T> clazz,final Map<String, String> convert,Object...args);
	
//	public <T extends Object> T queryForObject(String sql,final Class<T> clazz,Object...args);
	
//	public Map<String,Object> queryForMap(String sql,Object...args);
	
	public JdbcTemplate getJdbcTemplate();
	
	public HibernateTemplate getHibernateTemplate();
	
	public List getPageBySql(String sql,int start,int limit);
	
	public Integer getBySqlTotalCount(String sql);
	
	/**
	 * 返回List<String[]>
	 * 
	 * @param sql
	 * @return
	 */
	public List<String[]> getBySql2(String sql);
	
	/**
	 * 根据属性查询对象列表
	 * @param <T> 泛类型
	 * @param clazz 实体对象class
	 * @param property 属性名称
	 * @param value 属性值
	 * @return
	 */
	public <T> List<T> findByProperty(Class<T> clazz,String property,Object value);
	
	/**
	 * 根据属性查询对象列表（带排序）
	 * @param <T> 泛类型
	 * @param clazz 实体对象class
	 * @param property 属性名称
	 * @param value 属性值
	 * @param order 排序字符串
	 * @return
	 */
	public <T> List<T> findByProperty(Class<T> clazz,String property,Object value,String order);
	
	/**
	 * 
	 * @param <T>
	 * @param entity
	 * @throws Exception
	 */
	public <T> void update(T entity);
	
	/**
	 * 查询全部对象
	 * @param <T> 泛类型
	 * @param clazz 实体对象class
	 * @return
	 */
	public <T> List<T> findAll(Class<T> clazz);
}
