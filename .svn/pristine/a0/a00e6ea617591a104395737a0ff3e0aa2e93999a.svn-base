package com.vsoftcorp.kls.report.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/***********************************************************************************************************
*      					                COOPMAN CONSULTANCY SERVICES PVT. LTD. 
************************************************************************************************************
*  File Name             :  CCustomCal.java
*  Project Name          :  eBanking Management System (eBMS) 
*  Author	         	 : Girish and Prashanth
*  Version & Date        :  ver 1.0 & 13th August 2001
*  File Description      :  Custom calendar that extends GregorianCalendar class and adds functionalities.     
*  Classes used	         : 	java.util.Calendar,java.util.GregorianCalendar and java.sql.Date and java.Lang.*. 
*  Modification History  :  
*------------------------------------------------------------------------------------------------------------
*    ID		   Date			Changed By		Description			
*------------------------------------------------------------------------------------------------------------
*
************************************************************************************************************/

    
public class CCustomCal extends GregorianCalendar
{
		/***********************************************************************************************************
			Description/Purpose : A Class defining a Custom Calendar having features needed for easy manipulation of
						Time.
				Usage Instructions  : Create an Instance of this Class and call any of its methods.

		************************************************************************************************************/
		
		
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% CONSTRUCTORS %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		
    public CCustomCal()
	{
				/************************************************************************************************************
				 *   Identity : DEFAULT CONSTRUCTOR
				 *   Function : Calls the default Constructor of Gregorian Calendar class and deducts 1900 Years from the 
				 *              Gregorian Calendar Initialized. .
				 *	@param   : None
				 *************************************************************************************************************/

				super();
				this.add(Calendar.YEAR,-1900);
	}
				
    public CCustomCal(int intYear,int intMonth,int intDate)
    {
				/************************************************************************************************************
				 *   Identity : OVER LOADED CONSTRUCTOR FOR INTEGER FIELDS
				 *   Function : Takes integer arguments for Date,Month and Year and Constructs a Custom Calendar Object from them.
				 *	 @param   : integer year, integer month, integer date in the same order.
				 *************************************************************************************************************/
        
        super((intYear-1900),(intMonth-1),intDate);
    }
        
    public CCustomCal(String date)
    {
            CCustomCal intcal=null;
        	try
        	{   
//        		java.util.Date utildate = new java.util.Date(date);
//         	    java.sql.Date sqldate   = new java.sql.Date((utildate.getYear()),(utildate.getMonth()),utildate.getDate());
//         	    intcal = new CCustomCal((sqldate.getYear()+1900),(sqldate.getMonth()+1),sqldate.getDate());
         	}
         	catch(Exception e)
         	{
         	    System.out.println("exception in CCustomCal. Constructor.."+ e);
         	}
    }

	public CCustomCal(java.util.Date dtUtilDateArg) 
    {
				/************************************************************************************************************
				 *   Identity : OVERLOADED CONSTRUCTOR FOR SQL DATE OBJECT
				 *   Function : Takes a java.sql.Date object as argument and constructs a Custom Calendar object from it. 
				 *	 @param   : java.sql.Date
				 *************************************************************************************************************/
     		
     		//super(date.getYear()-1900,date.getMonth()-1,date.getDate());
     		super(dtUtilDateArg.getYear(),dtUtilDateArg.getMonth(),dtUtilDateArg.getDate());
    }
        

        
        
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% METHODS %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
        
     public CCustomCal getCustomCalendar(String strDate)
     {
				/************************************************************************************************************
				*   Description : Function that takes in a java.lang.String representation of a Date in MM/DD/YYYY Format 
				*                 and Initializes a CCustomCal from it.
				*   @param 		: String strDate in MM/DD/YYYY Format. 
				*   @return  		: CCustomCal object.
				*************************************************************************************************************/
        	
        	// String date to be in MM/DD/YYYY Format.
        	
        	java.util.Date dtUtil = new java.util.Date(strDate);
         	java.sql.Date dtSql = new java.sql.Date((dtUtil.getYear()),(dtUtil.getMonth()),dtUtil.getDate());
         	CCustomCal dtCustCal	= new CCustomCal((dtSql.getYear()+1900),(dtSql.getMonth()+1),dtSql.getDate());
         	return dtCustCal;
    }
        
        
    public CCustomCal klone() 
 	{
				/************************************************************************************************************
				*   Description : Function that clones the given CCustomCal object using the clone method of GregorianCalendar class. 
				*   @param 			: None. 
				*   @return  		: CCustomCal object.
				*************************************************************************************************************/
 			
		  	return (CCustomCal)this.clone();
	}
				
  
  	public String toString()
    {
				/************************************************************************************************************
				*   Description : Function toString of Gregorian Calendar class is over ridden here.
				*   @param 			: None
				*   @return  		: java.lang.String object.
				*************************************************************************************************************/
    
    		String strTemp1 = null;
        strTemp1 = (this.get(Calendar.YEAR)+1900)+"-"+(this.get(Calendar.MONTH)+1)+"-"+(this.get(Calendar.DATE))+" "+this.get(Calendar.HOUR_OF_DAY)+":"+this.get(Calendar.MINUTE)+":"+this.get(Calendar.SECOND);
        return strTemp1;
    }
        
    public String toString(boolean boolFlag)
    {
				/************************************************************************************************************
				*   Description : Function toString over loaded to obtain String representation of Date with/without Time.
				*   @param 			: boolean boolFlag
				*   @return  		: java.lang.String object.
				*************************************************************************************************************/
        
        String strTemp2 = null;
        if(boolFlag) strTemp2 = (this.get(Calendar.YEAR)+1900)+"-"+(this.get(Calendar.MONTH)+1)+"-"+(this.get(Calendar.DATE));
        else strTemp2 = (this.get(Calendar.YEAR)+1900)+"-"+(this.get(Calendar.MONTH)+1)+"-"+(this.get(Calendar.DATE))+"("+this.get(Calendar.HOUR_OF_DAY)+"-"+this.get(Calendar.MINUTE)+"-"+this.get(Calendar.SECOND)+")";
        return strTemp2;
    }
      
    
    public void addPeriod(String strTempArg) 
    {
				/************************************************************************************************************
				*   Description : Function that adds Date/Month/Year to the CCustomCal object.
				*   @param 			: java.lang.String strTemp
				*   @return  		: void.
				*************************************************************************************************************/
        
        int intPeriod=Integer.parseInt(strTempArg.substring(1));
        char charTemp = strTempArg.charAt(0);
        switch(charTemp)
        {
        		// Case Date to be Added
            case 'D':
            case 'd':
                     this.add(Calendar.DATE,(intPeriod));
                     break;
        		// Case Month to be Added
            case 'M':
            case 'm':
            	     
                     this.add(Calendar.MONTH,intPeriod);
                     break;
        		// Case Year to be Added
            case 'Y':
            case 'y':
                     
                     
                     this.add(Calendar.YEAR,intPeriod);
                     break;
        }
    }
       
        
    public void addPeriod(String strTempArg,int day_val) //ADDED BY VENKAT FOR VAISH BANK REQ
    {
				/************************************************************************************************************
				*   Description : Function that adds Date/Month/Year to the CCustomCal object.
				*   @param 			: java.lang.String strTemp
				*   @return  		: void.
				*************************************************************************************************************/
        
        int intPeriod=Integer.parseInt(strTempArg.substring(1));
        char charTemp = strTempArg.charAt(0);
        switch(charTemp)
        {
        		// Case Date to be Added
            case 'D':
            case 'd':
                     this.add(Calendar.DATE,(intPeriod+day_val));
                     break;
        		// Case Month to be Added
            case 'M':
            case 'm':
            	     this.add(Calendar.DATE,day_val );//ADDED BY VENKAT
                     this.add(Calendar.MONTH,intPeriod);
                     break;
        		// Case Year to be Added
            case 'Y':
            case 'y':
                     
                     this.add(Calendar.DATE,day_val);//ADDED BY VENKAT
                     this.add(Calendar.YEAR,intPeriod);
                     break;
        }
    }
        
        
	public int getPeriod(CCustomCal dtCustCalen)
	{
				/************************************************************************************************************
				*   Description : Function that Calaculates the Period between the invoking CCustomCal object and the
				*									CCustomCal Object passed as argument.
				*   @param 			: CCustomCal
				*   @return  		: integer.
				*************************************************************************************************************/
				
				CCustomCal dtCustCalenClone = dtCustCalen.klone();
	
				/* Declaring & Initializing Variables */
				int intPer = 0;
				try
				{
						intPer = (this.dayDiff(dtCustCalenClone))+1;
				}
				catch(Exception objExp)
				{
						return 0;
				}
	 	 		return intPer;
	}
				
        
	public int dayDiff(CCustomCal dtCustCalend)throws Exception
	{
				/************************************************************************************************************
				*   Description : Function that Calculates the Difference in days between the Invoking Object and the CCustomCal
				*								  object passed as argument.
				*   @param 			: packagebp.CCustomCal
				*   @return  		: integer.
				*************************************************************************************************************/
				
				int intDiff = 0;
				//try
				{
						intDiff = Math.abs(Math.round((this.getTimeInMillis() - dtCustCalend.getTimeInMillis())/(1000*60*60*24)));
						
				}
				/*catch(Exception objExp)
				{
						intDiff = 0;
				}*/
				return intDiff;
	}
		
		
	public int dayDifference(CCustomCal dtCustCalend)throws Exception
	{
				/************************************************************************************************************
				*   Description : Function that Calculates the Difference in days between the Invoking Object and the CCustomCal
				*								  object passed as argument.
				*   @param 			: packagebp.CCustomCal
				*   @return  		: integer.
				*************************************************************************************************************/
				
				int intDiff = 0;
				//try
				{
						intDiff = Math.round((this.getTimeInMillis() - dtCustCalend.getTimeInMillis())/(1000*60*60*24));
						
				}
				/*catch(Exception objExp)
				{
						intDiff = 0;
				}*/
				return intDiff;
	}	
			
				
	public java.util.Date toUtilDate()
	{
				/************************************************************************************************************
				*   Description : Function that constructs a java.sql.Date of the invoking CCustomCal object.
				*   @param 			: None
				*   @return  		: java.sql.Date.
				*************************************************************************************************************/
				
				java.util.Date dtUtilDate1 = new java.util.Date(this.get(Calendar.YEAR),this.get(Calendar.MONTH),this.get(Calendar.DATE));
				return dtUtilDate1;
	}
				
	public java.sql.Date toSQLDate()
	{
				/************************************************************************************************************
				*   Description : Function that constructs a java.sql.Date of the invoking CCustomCal object.
				*   @param 			: None
				*   @return  		: java.sql.Date.
				*************************************************************************************************************/
				
				java.sql.Date dtSqlDate1 = new java.sql.Date(this.get(Calendar.YEAR),this.get(Calendar.MONTH),this.get(Calendar.DATE));
				return dtSqlDate1;
	}
				
	public boolean between(CCustomCal dtDate1,CCustomCal dtDate2)
	{
				/************************************************************************************************************
				*   Description : Function that checks whether the invoking CCustomCal object is in between the two Dates,
				*									passed as arguments.
				*   @param 			: packagebp.CCustomCal and packagebp.CCustomCal.
				*   @return  		: boolean.
				*************************************************************************************************************/
        
				CCustomCal dtDate1Clone = dtDate1.klone();
				CCustomCal dtDate2Clone = dtDate2.klone();
				if(dtDate1.after(dtDate2))
				{
						CCustomCal dtTemp = dtDate2.klone();
						dtDate2Clone = dtDate1.klone();
						dtDate1Clone = dtTemp;
				}
				if((!this.before(dtDate1Clone)) && (!this.after(dtDate2Clone))) return true;
				else return false;
	}
		

	void show(String str)
	{
		System.out.println(str);
	}
				
	public String getFormattedDate(String strFormat)
	{
				// FUNCTION TO OBTAIN FORMATTED DATE
		SimpleDateFormat simpleDateFormat = null;
		try
		{
			simpleDateFormat = new SimpleDateFormat(strFormat);
			
			return simpleDateFormat.format(this.toUtilDate());
		}
		catch(Exception err)
		{
			System.out.println("Error in getting formatted date: "+err);
			return null;
		}
		finally
		{
			simpleDateFormat = null;
		}
	}
	
	public String getFormattedDate(Date date)
	{
				// FUNCTION TO OBTAIN FORMATTED DATE
		SimpleDateFormat simpleDateFormat = null;
		try
		{
			simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
			System.out.println("*****************"+simpleDateFormat.format(date));
			
			return simpleDateFormat.format(date);
		}
		catch(Exception err)
		{
			System.out.println("Error in getting formatted date: "+err);
			return null;
		}
		finally
		{
			simpleDateFormat = null;
		}
	}

/*	public static void main(String ar[])
	{
				    CCustomCal obj=new CCustomCal();
				  //  CCustomCal obj2=new CCustomCal("9/26/2008");
				    //CCustomCal obj2=obj.getInterestCalendar("9/26/2008");
				    //System.out.println(obj2.getPeriod(obj));
				    System.out.println("obj: " +obj);
				    obj.add(obj.MONTH,1);
				    System.out.println("MONTH: " +obj.get(Calendar.MONTH ));
                    System.out.println("WEEK_OF_YEAR: " + obj.get(Calendar.WEEK_OF_YEAR));
                    System.out.println("WEEK_OF_MONTH: " + obj.get(Calendar.WEEK_OF_MONTH));
                    System.out.println("DATE: " + obj.get(Calendar.DATE));      
                    System.out.println("DAY_OF_MONTH: " + obj.get(Calendar.DAY_OF_MONTH));
                    System.out.println("DAY_OF_YEAR: " + obj.get(Calendar.DAY_OF_YEAR)); 
                    System.out.println("DAY_OF_WEEK: " + obj.getTime().getHours()+" : "+obj.getTime().getMinutes()+" : "+obj.getTime().getSeconds());
                    System.out.println("date="+ obj);
			 System.out.println("first day of the week : " + obj.getFirstDayOfWeek()); 
			 System.out.println("first day of the week : " + obj.getFormattedDate("dd MMM yyyy")); 
		

    }*/
}               // End of Class CCustomCal        