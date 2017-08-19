package com.vsoftcorp.kls.business.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.vsoftcorp.kls.business.entities.Customer;
import com.vsoftcorp.time.Date;
import com.vsoftcorp.time.Duration;
import com.vsoftcorp.time.DurationFactory;
import com.vsoftcorp.time.Sign;
import com.vsoftcorp.time.TimeSpan;
import com.vsoftcorp.time.TimeUnit;

/**
 * @author sponnam
 * 
 */
/**
 * @author a1623
 *
 */
public class DateUtil {

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	private static String dateFormat = "dd/MM/yyyy";

	public final static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

	public static com.vsoftcorp.time.Date getVSoftDateByString(String date) {
		logger.info("in vsoft date");
		return com.vsoftcorp.time.Date.valueOf(getFormattedDate(date));
	}

	public static java.util.Date getFormattedDate(String theDate) {

		java.util.Date d = null;
		String formats[] = { "ddMMyyHHmmss", "dd MMM yyyy", "dd-MMM-yyyy", "d MMM  yyyy",
				"dd/MM/yyyy", "EEE, MMM d,yyyy", "yyyy-MM-dd H:m:s", "dd-MM-yyyy HH:mm:ss",
				"yyyy-MM-dd","MMddyyHHmmss" };
		for (int i = 0; i < formats.length; i++) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(formats[i]);
				d = sdf.parse(theDate);
				break;
			} catch (Exception e) {
				// e.printStackTrace();
				// This part is commented intentionally
			}
		}
		return d;
	}

	public static String getDateString(Date theDate) {
		return sdf.format(theDate.getJavaDate());
	}

	public static Date getFinancialYearBeginDate(Date currentDate) {
		int monthValue = currentDate.getMonthOfYear().getValue();
		int yearValue = currentDate.getYearOfEra().getValue();
		if (monthValue == 1 || monthValue == 2 || monthValue == 3) {
			return getVSoftDateByString("01 Apr " + (yearValue - 1));
		} else {
			return getVSoftDateByString("01 Apr " + (yearValue));
		}
	}

	public static Date getFinancialYearEndDate(Date currentDate) {
		int monthValue = currentDate.getMonthOfYear().getValue();
		int yearValue = currentDate.getYearOfEra().getValue();
		if (monthValue == 1 || monthValue == 2 || monthValue == 3) {
			return getVSoftDateByString("31 Mar " + (yearValue));
		} else {
			return getVSoftDateByString("31 Mar " + (yearValue + 1));
		}
	}

	// 02 Dec 2014 returns 2014-2015
	// 31 Mar 2014 returns 2013-2014
	// 01 Apr 2014 returns 2014-2015
	public static String getFinancialYear(Date currentDate) {
		return DateUtil.getFinancialYearBeginDate(currentDate).getYearOfEra().getValue() + "-"
				+ DateUtil.getFinancialYearEndDate(currentDate).getYearOfEra().getValue();
	}

	public static Date getFinancialYearBeginDate(String financialYear) {
		/*
		 * StringTokenizer strToken = new StringTokenizer(financialYear, "-");
		 */
		String date[] = financialYear.split("-");
		return getVSoftDateByString("01 Apr " + date[0]);
	}

	public static Date getFinancialYearEndDate(String financialYear) {
		String date[] = financialYear.split("-");
		return getVSoftDateByString("31 Mar " + date[1]);
	}

	public static String getDateByAddingNoOfMonths(String theDate, int noOfMonthsToAdd) {

		Date vsoftDate = getVSoftDateByString(theDate);
		vsoftDate = vsoftDate.shift(new Duration(Sign.POSITIVE, TimeSpan.valueOf(

		TimeUnit.MONTH, (noOfMonthsToAdd))));
		vsoftDate = vsoftDate.shift(new Duration(Sign.NEGATIVE, TimeSpan.valueOf(

		TimeUnit.DAY, 1)));
		return sdf.format(vsoftDate.getJavaDate());

	}

	public static String getDateByAddingNoOfDays(String theDate, int noOfDaysToAdd) {

		Date vsoftDate = getVSoftDateByString(theDate);
		vsoftDate = vsoftDate.shift(new Duration(Sign.POSITIVE, TimeSpan.valueOf(

		TimeUnit.DAY, (noOfDaysToAdd))));
		return sdf.format(vsoftDate.getJavaDate());

	}

	public static Integer getDayOfWeek(String theDate) {
		List<String> Days = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(theDate.split("/")[2]),
				Integer.parseInt(theDate.split("/")[1]) - 1,
				Integer.parseInt(theDate.split("/")[0]));
		SimpleDateFormat formatter1 = new SimpleDateFormat("EEE");
		String day = formatter1.format(calendar.getTime());
		return Days.indexOf(day);
	}

	public static String getDateBySubtractingNoOfDays(String theDate, int noOfDaysToSubtract) {

		Date vsoftDate = getVSoftDateByString(theDate);
		vsoftDate = vsoftDate.shift(new Duration(Sign.NEGATIVE, TimeSpan.valueOf(

		TimeUnit.DAY, (noOfDaysToSubtract))));
		return sdf.format(vsoftDate.getJavaDate());

	}

	public static Integer compareDate(String date1, String date2) {
		return getVSoftDateByString(date1).compareTo(getVSoftDateByString(date2));
	}

	public static Date getDateByAddingNoOfMonthsToVsoftDate(Date vsoftDate, int noOfMonthsToAdd) {

		vsoftDate = vsoftDate.shift(new Duration(Sign.POSITIVE, TimeSpan.valueOf(
		TimeUnit.MONTH, (noOfMonthsToAdd))));
		 
		return vsoftDate;
	}
	  /**
	 * @param args
	 */
	public static void main(String[] args) {
		  
		 /* Customer c= new Customer();
			Customer c1 = new Customer();
			ArrayList<Customer> aa= new ArrayList<>();
			aa.add(c);
			aa.add(c1);
			System.out.println("aa=="+aa);*/
		  
	          int[] myIntArray = {3};    
	          
	          for(int t=0;t<myIntArray.length;t++){
	        	  if(myIntArray[t]>3 || myIntArray[t]<=0){
	        		  myIntArray[t] = 3;
	        	  }
	        	  
	          }
	        //  System.out.println("myIntArray=="+myIntArray[0]);
	      //   System.out.println("myIntArray=="+myIntArray[3]);
	          
	          
	          ArrayList<Customer> ll = null;
	          ArrayList<Customer> iterator= new ArrayList<>();
	          Customer c= new Customer();
	      	c.setId(Long.valueOf("1"));
				Customer c1 = new Customer();
				c1.setId(Long.valueOf("2"));
				Customer c2= new Customer();
				c2.setId(Long.valueOf("3"));
				Customer c3 = new Customer();
				c3.setId(Long.valueOf("4"));
				Customer c4= new Customer();
				c4.setId(Long.valueOf("5"));
				Customer c5 = new Customer();
				c5.setId(Long.valueOf("6"));
				Customer c6= new Customer();
				c6.setId(Long.valueOf("7"));
				Customer c7 = new Customer();
				c7.setId(Long.valueOf("8"));
				Customer c8 = new Customer();
				c8.setId(Long.valueOf("9"));
				Customer c9 = new Customer();
				c9.setId(Long.valueOf("10"));
			
	          iterator.add(c);
	          iterator.add(c1);
	          iterator.add(c2);
	          iterator.add(c3);
	          iterator.add(c4);
	          iterator.add(c5);
	          iterator.add(c6);
	          iterator.add(c7);
	          iterator.add(c8);
	          iterator.add(c9);
	         
	          ArrayList<Long> a1 = null;
	         System.out.println("size=="+iterator.size());
	        
	         for(ListIterator it = iterator.listIterator();it.hasNext();){
	             //
	             it.add(it.next());
	                 //System.out.println("it.nextIndex()=="+it);
	                 /*while(it.nextIndex()== myIntArray[i]){
	                
	                     return;
	                 }*/
	           
	             it = iterator.listIterator();
	            
	             it.next();
	                 for(int i = 0;i<iterator.size();i++){
	                     for(int j = 0;j<myIntArray.length;j++){
	                    	 ll = new ArrayList<Customer>();
	                         for(int a=1;a<=myIntArray[j];a++){
	                             if(it.hasNext()){
	                            	
	                            	 ll.add((Customer)it.next());
	                           //  System.out.println("y="+it.next());
	                             }
	                            
	                         }
	                         if(ll.size()>0){
	                       //  System.out.println("ll=="+ll);
	                       
	                        // System.out.println("ll si=="+ll.size());
	                         a1 = new ArrayList<>();
	                         for(int d=0;d<ll.size();d++){
	                        	 a1.add(ll.get(d).getId());
	                        	//System.out.println("in");
	                        	// System.out.println("check=="+a1);
	                         }
	                        System.out.println("check=="+a1);
	                         System.out.println("----");
	                         }
	                        
	                     }
	                    
	                 }
	         }   
}
	 

	public static Date getNextDateByFreequncyPeroid(Date fromDate, Integer freequencyInMonths) {

		Date toDate = null;

		toDate = fromDate.shift(DurationFactory.valueOf("MONTH:" + freequencyInMonths));

		return toDate;

	}
	public static Date getDateByFreequncyPeroid(Date fromDate, Integer freequency) {

		Date toDate = null;

		toDate = fromDate.shift(DurationFactory.valueOf(freequency == 0 ? "MONTH:1" : freequency == 1 ? "MONTH:3" : freequency == 2 ? "MONTH:6" : "YEAR:1"));

		return toDate;

	}
	
	public static String convertDate(String date) throws ParseException {
		 
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = dt.parse(date);
        // *** same for the format String below
        SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy");
        
		return dt1.format(date1);
	}
	
	 public static String getLastFridayOfMonth(String Date) {
		  Calendar cal = Calendar.getInstance();
			cal.setTime(getFormattedDate(Date));
		    int dayofweek;
		    cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) );
		    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		    dayofweek = cal.get(Calendar.DAY_OF_WEEK); 
		    if (dayofweek < Calendar.FRIDAY) 
		      cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 7
		          + Calendar.FRIDAY - dayofweek);
		    else
		      cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)
		          + Calendar.FRIDAY - dayofweek);

	   return sdf.format(cal.getTime());
	}
	
}
