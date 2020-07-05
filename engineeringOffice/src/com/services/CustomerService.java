package com.services;

import java.util.List;

import com.entities.Customers;

public interface CustomerService {

	public Integer addCustomer(Customers customer);

	public List<Customers> getAllCustomers();

	public Customers findCustomerById(Integer customerId);

	public List<Customers> getCustomersByDept(Integer deptId);

}
