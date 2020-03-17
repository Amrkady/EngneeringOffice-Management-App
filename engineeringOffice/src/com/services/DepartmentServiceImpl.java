package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Departments;
import com.entities.OperationType;

public class DepartmentServiceImpl implements DepartmentService{
	
   CommonDao commonDao;

public boolean addDepartment(Departments dept) {
	return commonDao.saveObject(dept);
}
	
	public List<Departments> loadDepartments(){
		 List depts=commonDao.findAll(Departments.class);
		 return depts;
	}
	public boolean addOperationType(OperationType operation) {
		return commonDao.saveObject(operation);
	}
	public List<OperationType> loadOperation(){
		List operations=commonDao.findAll(OperationType.class);
		 return operations;
	}

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}
}
