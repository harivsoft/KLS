package com.vsoftcorp.kls.dataaccess;

import java.util.List;

import com.vsoftcorp.kls.business.entities.Customer;

public interface ICustomerDAO {

	public List<Customer> getAllCustomersByName(String customerName);
	
	public Customer getCustomer(Long customerId);
	
	public void saveCustomer(Customer customer);
	
	public void updateCustomer(Customer customer);

}