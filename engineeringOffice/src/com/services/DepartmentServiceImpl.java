package com.services;

import java.util.List;

import com.common.CommonDao;
import com.entities.Departments;
import com.entities.OperationType;
import com.entities.Users;

public class DepartmentServiceImpl implements DepartmentService{
	
   CommonDao commonDao;

public boolean addDepartment(Departments dept) {
	return commonDao.saveObject(dept);
}
	

@Override
public boolean updateDepartment(Departments dept) {
	return commonDao.updateObject(dept);
}

@Override
public boolean deleteDepartment(Departments dept) {
	return commonDao.deleteObject(dept);
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
	
	@Override
	public Departments findDeptById(Integer deptId) {
		return (Departments)commonDao.findEntityById(Departments.class, deptId);

	}

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}
}
