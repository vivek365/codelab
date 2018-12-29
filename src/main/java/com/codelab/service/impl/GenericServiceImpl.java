package com.codelab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codelab.beans.general.AbstractBO;
import com.codelab.beans.general.AbstractDTO;
import com.codelab.beans.general.SearchDTO;
import com.codelab.common.QueryParams;
import com.codelab.service.GenericDao;
import com.codelab.service.GenericService;

@Service
public class GenericServiceImpl implements GenericService {

	@Autowired
	private GenericDao<AbstractBO> genericDao;

	@Override
	@Transactional
	public void save(AbstractBO bo) {
		genericDao.save(bo);
	}

	@Override
	@Transactional
	public void update(AbstractBO bo) {
		genericDao.save(bo);
	}

	@Override
	public AbstractBO getById(Long id, Class inputVO) {
		return genericDao.get(inputVO, id);
	}
	
	@Override
	public boolean hardDeleteById(Long id, Class inputVO) {
		return genericDao.hardDeleteById(id,inputVO);
	}

	@Override
	public List<AbstractDTO> find(AbstractDTO searchDTO, Class classMapped) {
		// TODO Auto-generated method stub
		return genericDao.find(searchDTO,classMapped);
	}

	@Override
	public List<AbstractBO> getListByCriteria(Class c,String[] columnNames,List<QueryParams> whereClause,SearchDTO abstractDTO) {
		if(abstractDTO.getWantPagination()){
			whereClause.add(new QueryParams(abstractDTO.getOffset(), "RESULT_COUNT", abstractDTO.getLimit()));
		}
		return genericDao.getListByCriteria(c, columnNames, whereClause,false);
	}


}
