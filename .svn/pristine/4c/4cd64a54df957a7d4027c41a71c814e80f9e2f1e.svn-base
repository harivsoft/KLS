package com.vsoftcorp.kls.service.helper;

import com.vsoftcorp.kls.business.entities.Customer;
import com.vsoftcorp.kls.data.CustomerData;
/**
 * This helper class is used to conversion b/w POJO and entity and vice versa
 * @author a1605
 *
 */
public class CustomerHelper {

	public static CustomerData getCustomerData(Customer customer ){
		CustomerData data=new CustomerData();
	
		data.setCbsCustId(customer.getCbsCustId());
		data.setId(customer.getId().toString());
		data.setCustomerId(customer.getCustomerId());
		data.setName(customer.getName());
		data.setMemberSrlNo(customer.getMemberSrlNo());
		data.setMemberType(customer.getMemberType());
		data.setVillageId(customer.getVillageId().toString());
		
		return data;
	}
	
	/*public static CustomerMaster getCustomerMaster(CustomerMasterData data){
		CustomerMaster master=new CustomerMaster();
		return master;
	}*/
	
}