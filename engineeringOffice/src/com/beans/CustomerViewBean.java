package com.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entities.Customers;
import com.services.CustomerService;

@ManagedBean
@ViewScoped
public class CustomerViewBean {
	@ManagedProperty(value = "#{customerServiceImpl}")
	private CustomerService customerServiceImpl;
	private List<Customers> customers = new ArrayList<Customers>();

	@PostConstruct
	public void init() {
		customers = customerServiceImpl.getAllCustomers();
	}

	public CustomerService getCustomerServiceImpl() {
		return customerServiceImpl;
	}

	public void setCustomerServiceImpl(CustomerService customerServiceImpl) {
		this.customerServiceImpl = customerServiceImpl;
	}

	public List<Customers> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customers> customers) {
		this.customers = customers;
	}

}