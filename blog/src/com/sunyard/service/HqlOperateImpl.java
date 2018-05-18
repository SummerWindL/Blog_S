package com.sunyard.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sunyard.consts.Consts;


@Component("HqlOperate")
@Transactional
public class HqlOperateImpl implements HqlOperate {
	
	private static final Log log = LogFactory.getLog(HqlOperateImpl.class);
	private HibernateTemplate hibernateTemplate;
	private JdbcTemplate jdbcTemplate;
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public  List getByHql(String hql){
		return hibernateTemplate.find(hql);
	}
	public <T> List<T> getByHql(String hql,Class<T> clazz){
		return hibernateTemplate.find(hql);
	}
	public List getByHql(String hql,Object...params){
		return hibernateTemplate.find(hql, params);
	}
	public <T> List<T> getByHql(String hql,Class<T> clazz,Object...params){
		return hibernateTemplate.find(hql, params);
	}
	public Object getByHqlFirst(String hql,Object...params){
		List list = hibernateTemplate.find(hql, params);
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	public Integer getCountByHql(String hql){
		int _start = hql.indexOf("from");
		int _end = hql.indexOf("order by");
		_end = _end==-1 ? hql.length() : _end;
		String _hql = "select count(*) "+hql.substring(_start,_end);
		return (Integer)getByHqlFirst(_hql);
	}
	public <T> T getByHqlFirst(String hql,Class<T> clazz,Object...params){
		List<T> list = hibernateTemplate.find(hql, params);
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	/**
	 * 得到第一条纪录
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public Object getByHqlFirst(String hql){
		List list = hibernateTemplate.find(hql);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	public <T> T getByHqlFirst(String hql,Class<T> clazz){
		List<T> list = hibernateTemplate.find(hql);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
//	public List getByHql(final String hql,final int top){
//		List l = (ArrayList)hibernateTemplate.execute(new HibernateCallback(){
//			public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
//				Query query = session.createQuery(hql);				
//				query.setFirstResult(0);
//				query.setMaxResults(top);
//				List _data = query.list();
//				return _data;
//			}			
//		});
//		return l;
//	}
	public void updateByHql(final String...hqls){
		hibernateTemplate.execute(new HibernateCallback(){
		public Object doInHibernate(Session session) throws HibernateException,SQLException {
			for(String hql : hqls){
				session.createQuery(hql).executeUpdate();
			}
			return null;
			}
		});
	}

	public void merge(Object... objs){
		for(Object obj : objs){
			hibernateTemplate.merge(obj);
		}
	}
	public void delete(Object... objs){
		for(Object obj : objs){
			hibernateTemplate.delete(obj);
		}		
	}
	public void save(Object...objs){
		for(Object obj : objs){
			hibernateTemplate.save(obj);
		}
	}
	public void saveList(List l){
		for(Object o : l ){
			hibernateTemplate.save(o);
		}
	}
	public <T> T getById(Class<T> clazz,Serializable id){
		try {
			return (T)hibernateTemplate.get(clazz, id);
		} catch (DataAccessException e) {
			throw e;
		}
	}
	public void updateBySql(final String...sqls){
		hibernateTemplate.execute(new HibernateCallback(){
		public Object doInHibernate(Session session) throws HibernateException,SQLException {
			for(String sql : sqls){
				session.createSQLQuery(sql).executeUpdate();
			}
			return null;
			}
		});
	}

	public List getBySql(String sql,Object...args){
		return jdbcTemplate.queryForList(sql, args);
	}
	
	public List<String[]> getBySql2(String sql){
		return jdbcTemplate.query(sql, new RowMapper<String[]>(){
			public String[] mapRow(ResultSet rs, int index) throws SQLException {
				String[] array = new String[rs.getMetaData().getColumnCount()];
				for(int i = 0; i < array.length; i++) {
					array[i] = rs.getString(i+1);
				}
				return array;
			}
		});
	}
	
	public List<String[]> getPageBySql(String sql,int start,int limit) {
		StringBuffer pageSql= new StringBuffer();
		List<String[]> list = null;
		if(Consts.DBTYPE.equals("1")) {
			pageSql.append("select b.* from (select rownum as linenum,a.* from (").append(sql)
					.append(") a where rownum <= ").append(start+limit).append(") b where linenum > ").append(start);
			list = jdbcTemplate.query(pageSql.toString(), new RowMapper<String[]>(){
				public String[] mapRow(ResultSet rs, int index) throws SQLException {
					String[] array = new String[rs.getMetaData().getColumnCount()-1];
					// 去掉第一列linenum  
					for(int i = 0; i < array.length; i++) {
						array[i] = rs.getString(i+2);
					}
					return array;
				}
			});
		} else if(Consts.DBTYPE.equals("0")) {
			pageSql.append("select * from (select row_number() over() as rownum, a.* from (").append(sql).append(") a) b ")
					.append("where rownum > ").append(start).append(" and rownum <= ").append(start+limit);
			list = jdbcTemplate.query(pageSql.toString(), new RowMapper<String[]>(){
				public String[] mapRow(ResultSet rs, int index) throws SQLException {
					String[] array = new String[rs.getMetaData().getColumnCount()-1];
					// i从2开始取值，去掉第一列rownum  
					for(int i = 0; i < array.length; i++) {
						array[i] = rs.getString(i+2);
					}
					return array;
				}
			});
		} else if ("3".equals(Consts.DBTYPE)) {
			//??informix
			pageSql.append("select skip ").append(start).append(" first ").append(limit).append(" * from (")
			.append(sql).append(")");
			list = jdbcTemplate.query(pageSql.toString(), new RowMapper<String[]>(){
				public String[] mapRow(ResultSet rs, int index) throws SQLException {
					String[] array = new String[rs.getMetaData().getColumnCount()];
					for(int i = 0; i < array.length; i++) {
						array[i] = rs.getString(i+1);
					}
					return array;
				}
			});
		} 
		return list;
	}
	
	public Integer getBySqlTotalCount(String sql) {
		return jdbcTemplate.queryForInt(sql);
	}
	
//	public <T extends Object> List<T> getBySqlEx(String sql,final Class<T> clazz,Object...args){
//		return jdbcTemplate.query(sql, args, new RowMapper<T>(){
//			@Override
//			public T mapRow(ResultSet rs, int index) throws SQLException {
//				return SqlUtil.converRs2Bean(rs, clazz);
//			}
//		});
//	}
//	
//	public <T extends Object> List<T> getBySqlExSpec(String sql,final Class<T> clazz,final Map<String, String> convert,Object...args){
//		return jdbcTemplate.query(sql, args, new RowMapper<T>(){
//			@Override
//			public T mapRow(ResultSet rs, int index) throws SQLException {
//				return SqlUtil.converRs2Bean(rs, clazz,convert);
//			}
//		});
//	}
//	
//	public <T extends Object> T getBySqlFirst(String sql,final Class<T> clazz,Object...args){
//		try {
//			return jdbcTemplate.queryForObject(sql, new RowMapper<T>(){
//				@Override
//				public T mapRow(ResultSet rs, int index) throws SQLException {
//					return SqlUtil.converRs2Bean(rs, clazz);
//				}}, args);
//		} catch (DataAccessException e) {
//			return null;
//		}
//	}
//	
//	public <T extends Object> T getBySqlFirstSpec(String sql,final Class<T> clazz,final Map<String, String> convert,Object...args){
//		try {
//			return jdbcTemplate.queryForObject(sql, new RowMapper<T>(){
//				@Override
//				public T mapRow(ResultSet rs, int index) throws SQLException {
//					return SqlUtil.converRs2Bean(rs, clazz,convert);
//				}}, args);
//		} catch (DataAccessException e) {
//			return null;
//		}
//	}
//	
//	public <T extends Object> T queryForObject(String sql,final Class<T> clazz,Object...args){
//		try {
//			return jdbcTemplate.queryForObject(sql,clazz,args);
//		} catch (DataAccessException e) {
////			log.error(e);
//			return null;
//		}
//	}
	
	public Map<String,Object> queryForMap(String sql,Object...args){
		try {
			return jdbcTemplate.queryForMap(sql, args);
		} catch (DataAccessException e) {
			log.error(e);
			return null;
		}
	}
	

	public List getByHqlParam(String hql,Object...params){
		return hibernateTemplate.find(hql, params);
	}
	public List getByHqlParamTop(final String hql,final Object[] params,final int top){	
		List l = (ArrayList)hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for(int i = 0 ; i < params.length ; i ++){
					query.setParameter(i, params[i]);
				}
				query.setFirstResult(0);
				query.setMaxResults(top);
				List _data = query.list();
				return _data;
			}			
		});
		return l;
	}
	public List getByHqlTop(final String hql,final int top){		
		List l = (ArrayList)hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(top);
				List _data = query.list();
				return _data;
			}			
		});
		return l;
	}
	public List getPagedByHql(final String hql,final int start,final int top){		
		List l = (ArrayList)hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(top);
				List _data = query.list();
				return _data;
			}			
		});
		return l;
	}
	public void mergeList(List objs){
		for(Object obj : objs){
			hibernateTemplate.merge(obj);
		}
	}
	public void deleteList(List list){
		for(Object obj : list){
			hibernateTemplate.delete(obj);
		}
	}
	public void updateList(List l){
		for(Object o : l ){
			hibernateTemplate.merge(o);
		}
	}
	public List getByObj(Object instance){
		List results = new ArrayList();
		try {
			results = hibernateTemplate.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
		}
		return results;
	}
	public Object getByObjFirst(Object instance){
		List results = getByObj(instance);
		if(results.isEmpty()){
			return null;
		}else{
			return results.get(0);
		}
	}
	public void updateByHqlParams(String sql,Object...params){
		hibernateTemplate.bulkUpdate(sql, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByProperty(Class<T> clazz,String property,Object value){
		String queryString = "from " + clazz.getName() + " where "+ property + "= ?";
		List<T> list=(List<T>)hibernateTemplate.find(queryString, value);
		if(null!=list && list.size()!=0)
			return list;
		return new ArrayList<T>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByProperty(Class<T> clazz,String property,Object value,String order){
		String queryString = "from " + clazz.getName() + " where "+ property + "= ?";
		if(null!=order && order.trim().length()!=0)
			queryString+=" order by "+order;
		List<T> list=(List<T>)hibernateTemplate.find(queryString, value);
		if(null!=list && list.size()!=0)
			return list;
		return new ArrayList<T>();
	}
	
	@Override
	public <T> void update(T entity)  {
		hibernateTemplate.update(entity);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz){
		return hibernateTemplate.find("from "+clazz.getName());
	}
}
