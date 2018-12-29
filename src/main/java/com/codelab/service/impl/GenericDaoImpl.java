package com.codelab.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.codelab.beans.general.AbstractBO;
import com.codelab.beans.general.AbstractDTO;
import com.codelab.common.CommonImportListener;
import com.codelab.common.QueryParams;
import com.codelab.common.QueryParams.QueryConstants;
import com.codelab.service.GenericDao;

@Repository
public class GenericDaoImpl<T extends Serializable> extends HibernateDaoSupport implements GenericDao<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericDaoImpl.class);

	@PostConstruct
	public void registerListeners() {
		EventListenerRegistry eventListenerRegistry = ((SessionFactoryImplementor) sessionFactory).getServiceRegistry()
				.getService(EventListenerRegistry.class);
		eventListenerRegistry.prependListeners(EventType.PRE_INSERT, CommonImportListener.class);
		eventListenerRegistry.prependListeners(EventType.PRE_UPDATE, CommonImportListener.class);
	}

	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Autowired
	public GenericDaoImpl(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	@Transactional
	public T save(final T o) {
		LOGGER.debug("Execute method : save()");
		sessionFactory.getCurrentSession().saveOrUpdate(o);
		return o;
	}

	@Override
	@Transactional
	public void saveOrUpdateAll(final Collection<T> objects) {
		LOGGER.debug("Execute method : saveOrUpdateAll()");
		for (Object bean : objects) {
			getHibernateTemplate().saveOrUpdate(bean);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public T get(final Class<T> type, final Long id) {
		LOGGER.debug("Execute method : get()");
		Object o = (Object) getHibernateTemplate().load(type, id);
		return (T) ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<T> getAll(final Class<T> type) {
		LOGGER.debug("Execute method : getAll()");
		Criteria crit = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(type);
		return crit.list();
	}

	@Override
	@Transactional
	public boolean delete(final Object object) {
		try{
		LOGGER.debug("Execute method : delete()");
		getHibernateTemplate().delete(object);
		return true;
		}catch(Exception e){
			LOGGER.error("Execute method : delete()", e);
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean hardDeleteById(Long id, Class inputVO) {
		try{
			LOGGER.debug("Execute method : delete()");
			Criteria crit = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(inputVO);
			AbstractBO bo = (AbstractBO) crit.add(Restrictions.eq("id", id)).uniqueResult();
			getHibernateTemplate().delete(bo);
			return true;
		}catch(Exception e){
			LOGGER.error("Execute method : delete()", e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public T updateEntityOnDelete(final T o) {
		LOGGER.debug("Execute method : updateEntityOnDelete()");
		getHibernateTemplate().saveOrUpdate(o);
		return o;
	}

	@Override
	@Transactional
	public T merge(T object) {
		LOGGER.debug("Execute method : merge()");
		return (T) getHibernateTemplate().merge(object);
	}

	@Override
	@Transactional
	public void deleteALL(final Collection<T> objects) {
		LOGGER.debug("Execute method : deleteALL()");
		for (Object bean : objects) {
			getHibernateTemplate().delete(bean);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<T> executeSqlSelect(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeSqlSelect()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(query);
		setQueryParameter(queryObj, column);
		return queryObj.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<T> executeSelect(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeSelect()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
		setQueryParameter(queryObj, column);
		return queryObj.list();
	}

	@Override
	@Transactional
	public Integer executeUpdate(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeUpdate()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
		if (column != null) {
			setQueryUpdateParameter(queryObj, column);
		}

		return queryObj.executeUpdate();
	}

	@Override
	@Transactional
	public Integer executeSqlUpdate(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeSqlUpdate()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(query);
		if (column != null) {
			setQueryUpdateParameter(queryObj, column);
		}

		return queryObj.executeUpdate();
	}

	@Override
	@Transactional
	public int executeCommonSP(String spName, String... param) {
		LOGGER.debug("Execute method : executeCommonSP()");
		int updatedCount;
		StringBuilder queryStr = new StringBuilder("EXEC ");
		queryStr.append(spName + " ");
		StringBuilder paramBuilder = new StringBuilder();
		if (param != null) {
			for (String p : param) {
				if (paramBuilder.length() > 0) {
					paramBuilder.append(", ");
				}
				paramBuilder.append("'" + p + "'");
			}
			queryStr.append(paramBuilder.toString());
		}

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(queryStr.toString());
		updatedCount = query.executeUpdate();
		return updatedCount;
	}

	@Override
	@Transactional
	public void evictObject(final Object object) {
		LOGGER.debug("Execute method : evictObject()");
		getHibernateTemplate().evict(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<T> getListByCriteria(Class<T> c, String[] columnNames, List<QueryParams> queryParamList,
			boolean needToInitilize) {
		
		
		List<T> resultList = null;

		LOGGER.debug("Execute method : getListByCriteria()");

		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(c);

		if (null != queryParamList && !queryParamList.isEmpty()) {

			customRestrictions(queryParamList, criteria);
		}

		if (columnNames != null && columnNames.length > 0) {
			criteria.setProjection(getProjectionList(columnNames));
			criteria.setResultTransformer(Transformers.aliasToBean(c));
		}
		resultList = criteria.list();

		return resultList;
	}

	private ProjectionList getProjectionList(String[] columnNames) {
		ProjectionList projectionList = Projections.projectionList();
		for (String column : columnNames) {
			if(column.contains("countOfColumn:")){
				String col[] = column.split(":");
				projectionList.add(Projections.alias(Projections.count(col[1]), col[2]));
			}else{
				projectionList.add(Projections.property(column), column);
			}
		}
		return projectionList;
	}

	private void customRestrictions(List<QueryParams> queryParamList, Criteria criteria) {
		for (QueryParams queryParam : queryParamList) {
			QueryConstants condition = QueryConstants.valueOf(queryParam.getCondition().toString());
			String property = queryParam.getPropertyName().toString();
			Object value = queryParam.getPropertyValue();

			switch (condition) {
			case EQ:
				criteria.add(Restrictions.eq(property, value));
				break;

			case NE:
				criteria.add(Restrictions.ne(property, value));
				break;

			case STARTS_WITH:
				criteria.add(Restrictions.like(property, String.valueOf(value), MatchMode.START));
				break;

			case LIKE:
				criteria.add(Restrictions.like(property, String.valueOf(value), MatchMode.ANYWHERE));
				break;

			case GT:
				criteria.add(Restrictions.gt(property, value));
				break;

			case LT:
				criteria.add(Restrictions.lt(property, value));
				break;

			case GE:
				criteria.add(Restrictions.ge(property, value));
				break;

			case LE:
				criteria.add(Restrictions.le(property, value));
				break;
			case IS_NULL:
				criteria.add(Restrictions.isNull(property));
				break;
			case IS_NOT_NULL:
				criteria.add(Restrictions.isNotNull(property));
				break;
			case IN:
				criteria.add(Restrictions.in(property, (Object[]) value));
				break;

			case NOT_IN:
				criteria.add(Restrictions.not(Restrictions.in(property, (Object[]) value)));
				break;

			case ORDER_BY:
				orderBy(property, value, criteria);
				break;

			case RESULT_COUNT:
				criteria.setFirstResult(Integer.valueOf(property));
				criteria.setMaxResults((Integer) value);
				break;

			default:
				break;
			}
		}
	}

	private void orderBy(String property, Object orderBy, Criteria criteria) {

		if (QueryConstants.DESC.equals(QueryConstants.valueOf(orderBy.toString()))) {
			criteria.addOrder(Order.desc(property));

		} else {
			criteria.addOrder(Order.asc(property));
		}
	}

	/**
	 * column.put("alias",Payment.class); or column.put("alias","map");
	 *
	 * For in and not in query pass object array of value
	 * map1.put("hospitalUserId", strArray); String str= "90,2"; String
	 * strArray[] = str.split(",");
	 *
	 * if you wish map as return object then pass alias and map, if you want
	 * bean as return then pass class name. if you want limited results then
	 * pass firstIndex and maxResult in this map, both this object must be
	 * Integer map.put("firstIndex", 0); map.put("maxResult", 10);
	 *
	 * @author Vivek Jain
	 * @param query
	 * @param column
	 * @return column.put("alias",TblUser.class); or
	 *
	 */
	@SuppressWarnings("unchecked")
	public void setQueryParameter(Query query, Map<String, Object> column) {
		if (column != null && !column.isEmpty()) {
			if (column.containsKey("alias")) {
				Object alias = column.get("alias");
				if (alias.equals("map")) {
					query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				} else {
					query.setResultTransformer(Transformers.aliasToBean((Class<T>) alias));
				}
				column.remove("alias");
			}
			String firstIndex = "firstIndex";
			if (column.containsKey(firstIndex)) {
				query.setFirstResult((Integer) column.get(firstIndex));
				column.remove(firstIndex);
			}
			if (column.containsKey("uniqueResult")) {
				query.uniqueResult();
				column.remove("uniqueResult");
			}
			String maxResult = "maxResult";
			if (column.containsKey(maxResult)) {
				query.setMaxResults((Integer) column.get(maxResult));
				column.remove(maxResult);
			}
			// for IN and NOT-IN query
			for (Map.Entry<String, Object> entity : column.entrySet()) {
				if (entity.getValue() instanceof Object[]) {
					query.setParameterList(entity.getKey(), (Object[]) entity.getValue());
				} else {
					query.setParameter(entity.getKey(), entity.getValue());
				}
			}
		}
	}

	/**
	 *
	 * @author Vivek Jain
	 * @param query
	 * @param column
	 * @return
	 */
	public void setQueryUpdateParameter(Query query, Map<String, Object> column) {
		for (Map.Entry<String, Object> entity : column.entrySet()) {
			query.setParameter(entity.getKey(), entity.getValue());
		}
	}

	@Override
	@Transactional
	public List<Map<String, Object>> executeHQLSelectMap(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeHQLSelect()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
		if (column == null) {
			column = new HashMap<>();
		}
		column.put("alias", "map");
		setQueryParameter(queryObj, column);
		return queryObj.list();
	}

	@Override
	@Transactional
	public List<T> executeHQLSelect(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeHQLSelect()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(query);
		setQueryParameter(queryObj, column);
		return queryObj.list();
	}

	@Override
	@Transactional
	public List<T> executeSQLSelect(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeSQLSelect()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(query);
		setQueryParameter(queryObj, column);
		return queryObj.list();
	}

	@Override
	@Transactional
	public List<Map<String, Object>> executeSQLSelectMap(String query, Map<String, Object> column) {
		LOGGER.debug("Execute method : executeSQLSelectMap()");
		Query queryObj = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(query);
		if (column == null) {
			column = new HashMap<>();
		}
		column.put("alias", "map");
		setQueryParameter(queryObj, column);
		return queryObj.list();
	}

	@Override
	@Transactional
	public List<AbstractDTO> find(AbstractDTO searchDTO, Class classMapped) {
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(classMapped);
		return criteria.list();
	}
	
}