package com.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;

import com.entities.Customers;
import com.entities.Departments;
import com.entities.OperationType;
import com.models.CustomerModel;
import com.services.CustomerService;
import com.services.DepartmentService;
import com.services.DepartmentServiceImpl;


@ManagedBean
@ViewScoped
public class CustomerBean {
	@ManagedProperty(value = "#{customerServiceImpl}")
	private CustomerService customerServiceImpl;
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	
	private List<Departments> depts;
	private List<OperationType> operations;
	private Customers customers=new Customers();
	private Integer departmentId;
	private Integer operationId;
	private Integer step=1;
	private Integer customerId;
	private CustomerModel customerModel=new CustomerModel();
	
	
	
	@PostConstruct
	public void init() {
		depts=departmentServiceImpl.loadDepartments();
		operations=departmentServiceImpl.loadOperation();
	}
	
	public void check() {
		if(step==1) {
		System.out.println("4444444444444444444444444444444444444444444444444");
		if(customers.getNatNo().toString().length()!=10 ||customers.getPhone().length()!=10) {
		if(customers.getNatNo().toString().length()!=10)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "خطأ", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		if(customers.getPhone().length()!=10)
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "خطأ", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);}
	}else step=2;
		}else if(step==2) {
			if(departmentId!=null)step=3;
		}
	}


	public String save() {
		try {
		customerId=customerServiceImpl.addCustomer(customers);
		customerModel.setCustomerId(customerId);
		customerModel.setAddress(customers.getAddress());		
		customerModel.setDeptId(departmentId);
		customerModel.setDeptName(depts.stream()
			     .filter(item -> item.getDeptId().equals(departmentId))
			     .findFirst().get().getDeptName());
//		System.out.println(depts.stream() .filter(item -> item.getDeptId().equals(departmentId)).findFirst().get().getDeptName());
		customerModel.setNatNo(customers.getNatNo());
		customerModel.setPhone(customers.getPhone());
		customerModel.setCustomerName(customers.getCustomerName());
//		System.out.println(customers.getCustomerName());
		HttpServletRequest httprequest=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession httpSession=httprequest.getSession(false);
		httpSession.setAttribute("CustomerObject", customerModel);
		validate();
		System.out.println(operationId);
		if(operationId==1)
		return "bills";
		else if(operationId==2) return "contract";
		
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public void validate() {
		HttpServletRequest httprequest=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession httpSession=httprequest.getSession(false);
		CustomerModel cm=(CustomerModel)httpSession.getAttribute("CustomerObject");
		System.out.println(cm.getCustomerName());
	}
	public CustomerService getCustomerServiceImpl() {
		return customerServiceImpl;
	}




	public void setCustomerServiceImpl(CustomerService customerServiceImpl) {
		this.customerServiceImpl = customerServiceImpl;
	}




	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}




	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}




	public List<Departments> getDepts() {
		return depts;
	}




	public void setDepts(List<Departments> depts) {
		this.depts = depts;
	}




	public List<OperationType> getOperations() {
		return operations;
	}




	public void setOperations(List<OperationType> operations) {
		this.operations = operations;
	}




	public Customers getCustomers() {
		return customers;
	}




	public void setCustomers(Customers customers) {
		this.customers = customers;
	}




	public Integer getDepartmentId() {
		return departmentId;
	}




	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}




	public Integer getOperationId() {
		return operationId;
	}




	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

}
