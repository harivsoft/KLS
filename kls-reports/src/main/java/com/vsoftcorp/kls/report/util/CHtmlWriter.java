package com.vsoftcorp.kls.report.util;
import java.io.FileOutputStream;
import java.util.Vector;
public class CHtmlWriter
{
/***********************************************************************************************************
*      					                COOPMAN CONSULTANCY SERVICES PVT. LTD. 
************************************************************************************************************
*  File Name             :  CHtmlWriter.java
*  Project Name          :  eBanking Management System (eBMS) 
*  Author				 :  Prashanth Srinivas 
*  Version & Date        :  ver 1.0 & 10th MAY 2002
*  File Description      :  A Class to generate HTML file given the contents of the file.
*							Modified Version of HtmlWriter by Manish     
*  Modification History  :  
*------------------------------------------------------------------------------------------------------------
*    ID		   Date			Changed By		Description			
*------------------------------------------------------------------------------------------------------------
*
************************************************************************************************************/

	
	
		String strFileName = null;
		FileOutputStream objFOS;
		byte bytFlag = 0;
        	java.util.Calendar custCal = null;
	
		public CHtmlWriter()
		{
            custCal = new CCustomCal();
		}

		public CHtmlWriter(String strFileName)
		{
			try
			{
				//System.out.println("strFileName in CHtmlWriter page....   = "+strFileName);
				this.strFileName = strFileName;
				objFOS = new FileOutputStream(strFileName,true);
				custCal = new CCustomCal();
				//System.out.println("strFileName = "+strFileName);
			}
			catch(Exception objExp)
			{
				bytFlag = -1;
				objExp.printStackTrace();
				
			}
		}
	
		public void fileName(String strName)
		{
			try
			{
				this.strFileName = strName;
				objFOS = new FileOutputStream(strFileName,true);
				//custCal = new CCustomCal();
				
			}
			catch(Exception eName)
			{
				eName.printStackTrace();
				bytFlag = -1;
			}	
		}
		

		public String getTime()
		{
			String strTime = "";
			try
			{
				strTime = ""+custCal.getTime().getHours()+":"+custCal.getTime().getMinutes()+":"+custCal.getTime().getSeconds();
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
			return strTime;
		}

		public void writeReportName(String strTitle,String strFrmDate,String strToDate )
		{
			if(bytFlag == -1)
				return;
			else
			{
				//writeIntoFile("<html><head><title>"+strFileName+"</title><link rel='stylesheet' href='../css/formcss.css'></head><body bgcolor='#FFFFFF'><table border='1' cellspacing='1' cellpadding='1' align='center='><tr align='center'><td class='header'> REPORT : "+strTitle+"  From Date : "+strFrmDate+" TO Date : "+strToDate+"  </td></tr><tr></tr></table>");
				writeIntoFile("<html><head><title>"+strFileName+"</title></head><body bgcolor='#FFFFFF'><br><br><table border='1' cellspacing='1' cellpadding='1' align='center='><tr align='center'><td>  " +strTitle+ "  REPORT,  Date : " +strFrmDate+ "  </td></tr><tr></tr></table>");
			}
		}
		
		public void writeReportHeaders(String[] strHeader)
		{
			if(bytFlag == -1)
				return;
			else
			{
				writeIntoFile("<table border='1' cellspacing='1' cellpadding='1' align='center='><tr>");
				for(int i=0;i<strHeader.length;i++)
				{
					writeIntoFile("<td align='center'> "+ strHeader[i]+"</td>");
				}
				writeIntoFile("</tr>");
			}
		} 
 
		public void writeIntoFile(Vector v1,Vector v2)
		{
			Object Acc_No;
		    	Object strInterest; 
			if(bytFlag == -1)
		    	{
				return;
			}
			else
			{
				try
                		{      
		                 	for(int i=0;i<v1.size();i++)
		            		{
			                        Acc_No = v1.elementAt(i);
                        			strInterest = v2.elementAt(i);
			       		    	//System.out.println("Acc_NO =  "+Acc_No + "\t Interest = "+strInterest);	    		
		        		    	writeIntoFile("<tr><td>"+ Acc_No+ "</td><td>"+ strInterest+ "</td></tr>" );		        
		            		}
		        	}
		                catch(Exception e )
                		{
		                	e.printStackTrace();
		                	
                		}
            		}
        
		}

		public void writeIntoFile(Vector v1,Vector v2,Vector v3,Vector v4,Vector v5)
		{
			Object obj1;
		    	Object obj2; 
			Object obj3;
			Object obj4;
			Object obj5;

			if(bytFlag == -1)
		    	{
				return;
			}
			else
			{
				try
                		{      
		                 	for(int i=0;i<v1.size();i++)
		            		{
			                        obj1 = v1.elementAt(i);
                        			obj2 = v2.elementAt(i);
			                        obj3 = v3.elementAt(i);
                        			obj4 = v4.elementAt(i);
                        			obj5 = v5.elementAt(i);
					
						writeIntoFile("<tr><td>"+ obj1+ "</td><td>"+ obj2+ "</td><td>"+ obj3+"</td><td>"+ obj4 +"</td><td>"+obj5+"</td></tr>" );		        
		            		}
		        	}
		                catch(Exception e )
                		{
		                	e.printStackTrace();
		                	
                		}
            		}
        
		}
		
		public void writeIntoFile(String str1,String str2,String str3,String str4,String str5)
		{
			if(bytFlag == -1)
		    	{
				return;
			}
			else
			{
				try
                		{      
					writeIntoFile("<tr><td>"+ str1+ "</td><td>"+ str2+ "</td><td>"+ str3+"</td><td>"+ str4 +"</td><td>"+str5+"</td></tr>" );		        
		        	}
		                catch(Exception e )
                		{
		                	e.printStackTrace();
		                	endFile();
                		}
            		}
        
		}

		public void writeIntoFile(String strMatter)
		{
			if(bytFlag == -1)
			    return;
			else
			{
				try
				{
					for(int i=0;i<strMatter.length();i++)
					{
					    objFOS.write(strMatter.charAt(i));
					}
				}
				catch(Exception objExp)
				{
					objExp.printStackTrace();
				    return;
				}
			}
		}
		
		public void endFile()
		{
			if(bytFlag == -1)
					return;
			else
			{
				writeIntoFile("</table></body></html>");
//				System.gc();
			}
		}
		
/*		public static void main(String ar[])
		{
	        	CHtmlWriter obj = new CHtmlWriter("pac.html");
		        //obj.writeReportName("SB interest ","1/20/01");
		        String temp[]= { "Acc_No", "Interest" };
	        	obj.writeReportHeaders(temp);
	        
	        	Vector v1=new Vector();
		        Vector v2=new Vector();
	        
		        v1.addElement("1");
	        	v1.addElement("299");
		        v1.addElement("36");
	        
		        v2.addElement("100.09");
	        	v2.addElement("103.09");
		        v2.addElement("120.09");
	        
		        obj.writeIntoFile(v1,v2);
	        	obj.endFile();
		        System.out.println("Compleated");
	        
	   	}
*/		
}