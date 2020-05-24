package com.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.common.CommonDao;
import com.entities.Customers;


public class CustomerServiceImpl implements CustomerService {
CommonDao commonDao;

@Override
@Transactional
public Integer addCustomer(Customers customer) {
	return commonDao.saveCustomer(customer);
	
}

	@Override
	public List<Customers> getAllCustomers() {
		List list = commonDao.findAll(Customers.class);
		return list;
	}

public CommonDao getCommonDao() {
	return commonDao;
}

public void setCommonDao(CommonDao commonDao) {
	this.commonDao = commonDao;
}

}
