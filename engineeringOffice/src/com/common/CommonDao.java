package com.common;

import java.util.List;

import com.entities.Customers;
import com.entities.Users;

public interface CommonDao {
	 
	public boolean saveObject(Object object);

	List<Object> findAll(Class object);

	Object findEntityById(Class entityClass, Integer EntityId);

	boolean deleteObject(Object object);

	boolean updateObject(Object myObject);
	public Integer saveCustomer(Customers customer);
		

}
