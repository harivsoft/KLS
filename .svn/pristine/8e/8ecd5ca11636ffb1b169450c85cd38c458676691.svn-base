package com.vsoftcorp.kls.service.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * 
 * @author a1565
 *
 */
public class BusinessKeyGenerator {
	
	
	public static String generateProductCode() {
		long currentDateTime = System.currentTimeMillis();
		System.out.println("currentDateTime: "+currentDateTime);
		SimpleDateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentDateTime);
//		return df.format(cal.getTime());
		return String.valueOf(currentDateTime);
	}
	
	public static void main(String args[]) {
		String productCode = "PROD_"+generateProductCode();
		System.out.println("Product Code is: "+productCode);
				
	}
		

}