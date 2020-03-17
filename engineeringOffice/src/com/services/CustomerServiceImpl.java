package com.services;

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
public CommonDao getCommonDao() {
	return commonDao;
}

public void setCommonDao(CommonDao commonDao) {
	this.commonDao = commonDao;
}
}
