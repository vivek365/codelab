package com.codelab.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codelab.beans.general.AbstractBO;
import com.codelab.beans.general.AbstractDTO;
import com.codelab.beans.general.SearchDTO;
import com.codelab.common.QueryParams;

@Service
public interface GenericService {

	public void save(AbstractBO bo);

	public void update(AbstractBO inputVO);

	public AbstractBO getById(Long id, Class class1);
	
	public boolean hardDeleteById(Long id, Class class1);

	public List<AbstractDTO> find(AbstractDTO searchDTO, Class classMapped);
	
	public List<AbstractBO> getListByCriteria(Class c,String[] columnNames,List<QueryParams> whereClause,SearchDTO abstractDTO);
}
