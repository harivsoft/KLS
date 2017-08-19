package com.vsoftcorp.kls.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Customer;
import com.vsoftcorp.kls.data.CustomerData;
import com.vsoftcorp.kls.dataaccess.ICustomerDAO;
import com.vsoftcorp.kls.dataaccess.factory.KLSDataAccessFactory;
import com.vsoftcorp.kls.service.ICustomerService;
import com.vsoftcorp.kls.service.exception.KlsRuntimeException;
import com.vsoftcorp.kls.service.helper.CustomerHelper;

/**
 * @author a1605
 */
public class CustomerService implements ICustomerService {

	private static final Logger logger = Logger
			.getLogger(CustomerService.class);

	@Override
	public List<CustomerData> getAllCustomersByName(String customerName) {
		logger.info("Start : Calling CustomerDAO in getAllCustomersByName() method.");		
		ICustomerDAO dao=KLSDataAccessFactory.getCustomerDAO();
		List<CustomerData> customerDataList=new ArrayList<CustomerData>();
		CustomerData customerData=new CustomerData();
		try {
			List<Customer> customerList = dao.getAllCustomersByName(customerName);
			for (Customer customer : customerList) {
				customerData = CustomerHelper.getCustomerData(customer);
				customerDataList.add(customerData);
			}
		} catch (Exception excp) {
			logger.error("Error in retrieving all the customer  records");
			throw new KlsRuntimeException(
					"Error in retrieving all the customer  records",
					excp.getCause());
		}
		logger.info("End : Calling CustomerDAO in getAllCustomersByName() method");
		return customerDataList;
	}
	
}