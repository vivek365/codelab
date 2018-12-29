package com.codelab.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.codelab.beans.general.AbstractDTO;
import com.codelab.common.QueryParams;

@Repository
public interface GenericDao<T extends Serializable> {
	public T save(final T o);

	public T get(final Class<T> type, final Long id);

	public List<T> getAll(final Class<T> type);

	public boolean delete(final Object object);
	
	public boolean hardDeleteById(Long id, Class classType);

	public T updateEntityOnDelete(final T o);

	public void saveOrUpdateAll(final Collection<T> objects);

	public T merge(T object);

	public void deleteALL(final Collection<T> objects);

	/**
	 * 
	 * 
	 * @param sqlQuery
	 * @param column
	 * @return column.put("alias",Payment.class); or column.put("alias","map");
	 */
	public List<T> executeSqlSelect(String query, Map<String, Object> column);

	/**
	 * 
	 * 
	 * @param sqlQuery
	 * @param column
	 * @return column.put("alias",Payment.class); or column.put("alias","map");
	 * 
	 */
	public List<T> executeSelect(String query, Map<String, Object> column);

	public List<Map<String, Object>> executeHQLSelectMap(String query, Map<String, Object> column);

	public List<Map<String, Object>> executeSQLSelectMap(String query, Map<String, Object> column);

	public List<T> executeHQLSelect(String query, Map<String, Object> column);

	public List<T> executeSQLSelect(String query, Map<String, Object> column);

	/**
	 * 
	 * 
	 * @param query
	 * @param column
	 * @return column.put("alias",Payment.class); or column.put("alias","map");
	 * 
	 */
	public Integer executeUpdate(String query, Map<String, Object> column);

	/**
	 * 
	 * 
	 * @param query
	 * @param column
	 * @return column.put("alias",TblListingFlexibleWorkFlow.class); or
	 *         column.put("alias","map");
	 * 
	 */
	public Integer executeSqlUpdate(String query, Map<String, Object> column);

	/**
	 * 
	 * @param spName
	 * @param param
	 * @return
	 */
	public int executeCommonSP(String spName, String... param);

	/**
	 * Clear object from session cache.
	 * 
	 * 
	 * @param object
	 */
	public void evictObject(final Object object);


	// public List<T> getListByRistrictions(Class<T> c, Map<String, Object> map,
	// String[] columnNames,
	// Map<String, Object> others);

	/**
	 * @param class
	 *            -> Ex : TblUser.class
	 * @param required
	 *            columns -> Ex : String[] columnNames = {"userId"}
	 * @param List<QueryParams>
	 *            queryParamList Ex : sequence : propertyName = "userName",
	 *            condition = QueryConstants.LIKE, propertyValue = "test" Query
	 *            Ex: List<QueryParams> queryParamList = new ArrayList<>();
	 *            queryParamList.add(new QueryParams(IS_DELETED,
	 *            QueryConstants.EQ, 0)); queryParamList.add(new
	 *            QueryParams(USERNAME, QueryConstants.STARTS_WITH,
	 *            searchParam)); queryParamList.add(new QueryParams(0,
	 *            QueryConstants.RESULT_COUNT, 20)); return
	 *            customDrugDao.getListByCriteria(TblUser.class, null,
	 *            queryParamList);
	 * 
	 * @return list
	 */
	public List<T> getListByCriteria(Class<T> c, String[] columnNames, List<QueryParams> queryParamList,boolean needToInitilize);

	public List<AbstractDTO> find(AbstractDTO searchDTO, Class classMapped);
}
