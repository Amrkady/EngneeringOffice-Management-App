package com.services;

import java.util.List;

import com.entities.Departments;
import com.entities.OperationType;

public interface DepartmentService {

	public boolean addDepartment(Departments dept);
	
	public boolean addOperationType(OperationType operation);
	
	public List<Departments> loadDepartments();
	
	public List<OperationType> loadOperation();
		
	
}
