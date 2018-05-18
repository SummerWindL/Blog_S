package com.sunyard.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface HqlOperate {
	public Integer getCountByHql(String hql);
	/**
	 * ͨ��hql��������
	 * @param hql
	 * @return
	 */
	public List getByHql(String hql);
	public <T> List<T> getByHql(String hql,Class<T> clazz);
	/**
	 *  ͨ��hql��������
	 * @param hql		hql���
	 * @param params	ռλ����
	 * @return
	 */
	public List getByHql(String hql,Object...params);
	public <T> List<T> getByHql(String hql,Class<T> clazz,Object...params);
	/**
	 * ���ҵ�һ������
	 * @param hql
	 * @return
	 */
	public Object getByHqlFirst(String hql);
	public <T> T getByHqlFirst(String hql,Class<T> clazz);
	/**
	 * ���ҵ�һ������
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object getByHqlFirst(String hql,Object...params);
	public <T> T getByHqlFirst(String hql,Class<T> clazz,Object...params);
	
	/**
	 * ����ǰtop������
	 * @param hql
	 * @param top
	 * @return
	 */
//	public List getByHql(final String hql,final int top);
	/**
	 * ͨ��hql�������ݿ�
	 * @param hqls
	 */
	public void updateByHql(final String...hqls);
	/**
	 * �������
	 * @param objs
	 */
	public void save(Object...objs);
	/**
	 * ���¶���
	 * @param objs
	 */
	public void merge(Object... objs);
	/**
	 * ɾ������
	 * @param objs
	 */
	public void delete(Object... objs);
	/**
	 * ����list�б�
	 * @param l
	 */
	public void saveList(List l);
	/**
	 * ͨ��ID��ȡ����
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T getById(Class<T> clazz,Serializable id);
	/**
	 * ͨ��sql�������ݼ�
	 * @param sqls
	 */
	public void updateBySql(final String...sqls);
	/**
	 *  ͨ��hql��������
	 * @param hql		hql���
	 * @param params	ռλ����
	 * @return
	 */
	public List getByHqlParam(String hql,Object...params);
	/**
	 * ����ǰtop������
	 * @param hql
	 * @param top
	 * @return
	 */
	public List getByHqlTop(final String hql,final int top);
	/**
	 * ����ǰtop������
	 * @param hql			��ѯ���
	 * @param params		����
	 * @param top			��¼��
	 * @return
	 */
	public List getByHqlParamTop(final String hql,final Object[] params,final int top);
	/**
	 * �������ݿ�hql
	 * @param sql		hql���
	 * @param params	����
	 */
	public void updateByHqlParams(String hql,Object...params);
	/**
	 * 
	 * @param objs
	 */
	public void mergeList(List objs);
	/**               
	 * ɾ������
	 * @param list
	 */
	public void deleteList(List list);
	/**
	 * �����б�
	 * @param l
	 */
	public void updateList(List l);
	/**
	 * ͨ�������ѯ
	 * @param instance
	 * @return
	 */
	public List getByObj(Object instance);
	/**
	 * ͨ�������ѯ��һ����¼
	 * @param instance
	 * @return
	 */
	public Object getByObjFirst(Object instance);
	
	/**
	 * ͨ��sql���в�ѯ
	 * @param sql		sql���
	 * @param args		ռλ����
	 * @return
	 */
	public List getBySql(String sql,Object...args);
	
	public List getPagedByHql(final String hql,final int start,final int top);
	/**
	 * ͨ��sql���в�ѯ	�˷�ʽ�Դ��»��ߵ��ֶ��������⴦��( aa_bb -> aaBb )
	 * @param <T>
	 * @param sql		sql���
	 * @param clazz		��������
	 * @param args		ռλ����
	 * @return
	 */
//	public <T extends Object> List<T> getBySqlEx(String sql,final Class<T> clazz,Object...args);
	/**
	 * ͨ��sql���в�ѯ
	 * @param <T>
	 * @param sql		sql���
	 * @param clazz		��������
	 * @param convert	Ҫת�����ֶ�		key: ���ݿ��ֶ�����Сд����value:���ֶ���
	 * @param args		ռλ����
	 * @return
	 */
//	public <T extends Object> List<T> getBySqlExSpec(String sql,final Class<T> clazz,final Map<String, String> convert,Object...args);
	/**
	 * ͨ��sql���в�ѯ			�˷�ʽ�Դ��»��ߵ��ֶ��������⴦��( aa_bb -> aaBb )
	 * @param <T>
	 * @param sql		sql���
	 * @param clazz		��������
	 * @param args		ռλ����
	 * @return
	 */
//	public <T extends Object> T getBySqlFirst(String sql,final Class<T> clazz,Object...args);
	/**
	 * ͨ��sql���в�ѯ
	 * @param <T>
	 * @param sql		sql���
	 * @param clazz		��������
	 * @param convert	Ҫת�����ֶ�		key: ���ݿ��ֶ�����Сд����value:���ֶ���
	 * @param args		ռλ����
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
	 * ����List<String[]>
	 * 
	 * @param sql
	 * @return
	 */
	public List<String[]> getBySql2(String sql);
	
	/**
	 * �������Բ�ѯ�����б�
	 * @param <T> ������
	 * @param clazz ʵ�����class
	 * @param property ��������
	 * @param value ����ֵ
	 * @return
	 */
	public <T> List<T> findByProperty(Class<T> clazz,String property,Object value);
	
	/**
	 * �������Բ�ѯ�����б�������
	 * @param <T> ������
	 * @param clazz ʵ�����class
	 * @param property ��������
	 * @param value ����ֵ
	 * @param order �����ַ���
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
	 * ��ѯȫ������
	 * @param <T> ������
	 * @param clazz ʵ�����class
	 * @return
	 */
	public <T> List<T> findAll(Class<T> clazz);
}
