package com.vsoftcorp.kls.GepRep.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vsoftcorp.kls.GepRep.data.ReportInputData;
import com.vsoftcorp.kls.GepRep.service.IGenRepService;
import com.vsoftcorp.kls.GepRep.util.GenRepUtil;

public class GenRepService implements IGenRepService {

	public File gerateReportInPDF(ReportInputData data) {
		PdfWriter writer =null;
		OutputStream out = null;
		int ColumnCtr = 0, RowCtr = 0, TotalCount = 0;
		int MaxPage = 0;
		String PageHdr = "";
		String TabHdr = "";
		String RepFtr = "";
		String PageFtr = "";
		String TabFtr = "";
		String tempforfastprint = "";
		String ColSpace = data.getColSpace();
		String RemoteAddr = "";// to get the ipconfig of the client system
								// accessing the application
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		ResultSet rsBankDets = null;
		File file = new File("report.pdf");
		try {
			
			 out = new FileOutputStream(file);
			int rsColCount;
			int StartPos, EndPos, NoRecords, rsRowCount, noOfPages, no, mod;
			int pageNo = 0, totalPages = 0;
			/* for decimal formatting */
			DecimalFormat form = null;
			String strFormat = "###########0.00";
			/* for decimal formatting */

			String strDate = data.getSysDate();
			// String strBnkClckTime = TimerBean.getTimeString();
			/*
			 * String sqlBankDets =
			 * "select  bankname,branchname,address1 from sbbranchdefinition where branchid ='"
			 * +session.getValue("strBranchid")+"' "; rsBankDets =
			 * db.executeSelectQuery(sqlBankDets);
			 */
			String strBankname = "", strBranchname = "", strAddress1 = "", strPgHdr = "";
			strBankname = data.getStrBankname();
			strBranchname = data.getStrBranchname();
			strAddress1 = data.getStrAddress();
//			rsBankDets.close();
			String strRepHdrFtr = data.getStrRepHdrFtr() == null ? "" : data
					.getStrRepHdrFtr();
			String strPgHdrFtr = data.getStrPgHdrFtr() == null ? "" : data
					.getStrPgHdrFtr();
			String strTblHdrFtr = data.getStrTblHdrFtr() == null ? "" : data
					.getTempforfastprint();
			String strQry = data.getStrQry() == null ? "" : data.getStrQry();
			String strColTitles = data.getStrColTitles() == null ? "" : data
					.getStrColTitles();
			String strColAlign = data.getStrColAlign() == null ? "" : data
					.getStrColAlign();
			String strColStyle = data.getStrColStyle() == null ? "" : data
					.getStrColStyle();
			String strColTitleBrk = data.getStrColTitleBrk() == null ? ""
					: data.getStrColTitleBrk();
			String strSkipCol = data.getStrSkipCol() == null ? "" : data
					.getStrSkipCol();
			String strGroup = data.getGroupParam() == null ? "" : data.getGroupParam();
			String strLink = data.getStrLink() == null ? "" : data.getStrLink();
			String strTotal = data.getStrTotal() == null ? "" : data
					.getStrTotal();
			String strErrMsg = data.getStrErrMsg() == null ? "" : data
					.getStrErrMsg();
			String strSlNoFlg = data.getStrSlNoFlg() == null ? "" : data
					.getStrSlNoFlg();
			tempforfastprint = data.getTempforfastprint() == null ? "" : data
					.getTempforfastprint();
			String orderFalg = data.getOrderFalg() == null ? "N" : data
					.getOrderFalg();
			String romanLettersReuire = data.getRomanLettersReuire() == null ? "N"
					: data.getRomanLettersReuire();
			//RemoteAddr=data.getParameter("RemoteAddr")==null?"":data.getParameter("RemoteAddr");
			System.out.println("tempforfastprint "+tempforfastprint);
			System.out.println("All Parameters read into Variables.");
			
			/*RemoteAddr =data.getRemoteAddr() ;
			System.out.println("RemoteAddr"+RemoteAddr);*/
			try {
				StartPos = ( data.getStart()!=null && data.getStart()>0 ) ? data.getStart() : 0 ;
			} catch(NumberFormatException numErr) {
				System.out.println("Start Position Parameter value not in valid format. Setting it to ZERO.... ");
				StartPos = 0;
			}

			try {
				NoRecords = ( data.getNoRecords()!=null && data.getNoRecords()>0 ) ? data.getNoRecords() : (Integer.MAX_VALUE) ;
			} catch(NumberFormatException numErr) {
				System.out.println("No. of Records per Page Parameter value not in valid format. Setting it to MAXIMUM VALUE OF int DATA TYPE.... ");
				NoRecords = Integer.MAX_VALUE;
			}
			
			
		
				////////BREAKING GROUP COLUMNS & OBJECT DECLARATIONS USED FOR GROUPING 
			String strGrpCols="", strGrpAggs="", strGrpMsgs="";
			if( strGroup.indexOf('~')!=(-1) )
			{
				strGrpCols = strGroup.substring(0,strGroup.indexOf('~'));
				if(strGroup.indexOf('~')!=strGroup.lastIndexOf('~'))
				{
					strGrpAggs = strGroup.substring( strGroup.indexOf('~')+1, strGroup.lastIndexOf('~') );
					strGrpMsgs = strGroup.substring( strGroup.lastIndexOf('~')+1 );
				}
				else
				{
					strGrpAggs = strGroup.substring( strGroup.lastIndexOf('~')+1 );
				}
				if(strGrpAggs.equals(""))
					throw new Exception("Argument passed through GroupParam Parameter is not in proper format. \nTrying to group without specifying the aggregate function.");
			}

			String[] arrGroupCols = splitString( strGrpCols, "," );
			String[] arrGroupAggs = splitString( strGrpAggs, "," );
			String[] arrGrpMsgs = splitString( strGrpMsgs, "," );

			Hashtable HTBrkVals = new Hashtable();
			 
		    double fltAggFnVal[] = new double[arrGroupAggs.length];
			String strGrpColVal[] = new String[arrGroupCols.length];
			int[] brkRowCnt = new int[arrGroupAggs.length];

			String[] arrGrpAggFnCols = new String[arrGroupAggs.length];
			String[] arrGrpAggFns = new String[arrGroupAggs.length];
			for(int i=0; i<arrGroupAggs.length; i++)
			{
				arrGrpAggFnCols[i] = arrGroupAggs[i].substring(arrGroupAggs[i].indexOf('(')+1,arrGroupAggs[i].indexOf(')')).trim();
				arrGrpAggFns[i] = arrGroupAggs[i].substring(0,arrGroupAggs[i].indexOf('(')).trim();
			}

				System.out.println("//////// GETTING THE RESULT SET INFO. ........."+strQry+"................"+arrGroupCols.length); 
		  	rsColCount=0;
		  	//changes by Ashraf
			if( strQry!=null && !strQry.equals("") )
			{
				System.out.println("...............in if. .........");
			
				if (strQry.trim().equals("null"))
				{
					// html code replaced
					
					return file; //stops the execution of the jsp...
					//return; //stops the execution of the jsp...
				}
//////			This block modified on 22 Mar '03 by Santosh for handling Grouping alongwith Ordering the cols.
				if (arrGroupCols.length>0)
				{
					String strQry1 = "", strQry2 = "";
					//out.println("<BR>qry Before --"+strQry);
					if(strQry.toUpperCase().indexOf("ORDER BY")!=(-1))
					{
						strQry1 = strQry.substring(0,strQry.toUpperCase().indexOf("ORDER BY")+8) + " ";
						System.out.print("<BR>qry1 --"+strQry1);
						strQry2 = strQry.substring(strQry.toUpperCase().indexOf("ORDER BY")+9);
						System.out.print("<BR>qry2 --"+strQry2);
						if ("N".equalsIgnoreCase(orderFalg)) {
						for(int i=0; i<arrGroupCols.length; i++)	
							strQry1 += (Integer.parseInt(arrGroupCols[i])+1) + ",";
						}
						strQry = strQry1 + strQry2;
					}
					else
					{
						strQry += " order by ";
						for(int i=0; i<arrGroupCols.length; i++) strQry += (Integer.parseInt(arrGroupCols[i])+1) + ",";
						strQry = strQry.substring(0,strQry.length()-1);
					}
					System.out.println("Final qry --"+strQry);
				}
				System.out.println("Final Query is :"+strQry);

				rs = GenRepUtil.getResultSet(strQry); 
				
				/*if(!repRS.isRSLoaded())
				{
					System.out.println("Fetching ResultSet ...");
					rs = db.getScrollableRS(strQry);
					repRS.setReportRS(rs);
				}
				else
				{
					rs = repRS.getReportRS();
					System.out.println("ResultSet already exists....");
				}*/
				//rs=repRS.getReportRS(db,strQry);
					///// added by HK Santosh on 29 Jan 2003 (For Scrollable ResultSet implementation)

				rsmd = rs.getMetaData();
				rsColCount = rsmd.getColumnCount();
				
				System.out.println("ResultSet Obtained. Col Count is:-- "+rsColCount);
			} else if (!strQry.trim().equals("null")) throw new Exception("QUERY NOT SUPPLIED TO REPORT GENERATOR PAGE. \nPLEASE CHECK THE PARAMETER PASSING PAGE.");

		// COLLECTING RESULTSET VALUES IN AN ARRAY OF VECTORS(One Vec for each of the Cols)
		// FOR DISPLAY PURPOSE
			Vector vecValues[] = new Vector[rsColCount];
			rsRowCount = 0;
			
			//by Ashraf: retrieve some params for...
			String grpCol = ""; //col on which the group has o be made
			String qryParam = data.getQryParam();
			String grpParam = data.getGroupParam();

			
			int idxGrpCol = 0;
			int idxTotCols[] = new int[1];
			double subTots[] = new double[1];
			
			//before entering the while rs.next
			if (grpParam!=null && !grpParam.trim().equals("") && !grpParam.trim().equals("null")) {
				
				//Retrieve datas from grpParam
				int datasStartAt = 0; //datas appear from the 1st elt
				String grpParams[] = grpParam.split(",");
				int szGrpParams = grpParams.length;
				//array containing the elts of the 1st part of the grpParam string (before the 1st ,)
				String grpParams1stElts[] = grpParams[0].split("~");
				//if (grpParams1stElts.length==0) {
				if (String.valueOf(grpParams[0]).length()<3) { //in case the fist part of the string doesn't contain ~
					grpParams1stElts = grpParams[1].split("~"); 
					datasStartAt = 1; //datas appear from the 2nd elt
				}
				//index of the grouping criteria (column)
				idxGrpCol = Integer.parseInt(grpParams1stElts[0]);
				//array that will contain the indexes of subtotals columns
				idxTotCols = new int[szGrpParams-datasStartAt];
				//array that will contain the totals for each col
				subTots = new double[szGrpParams-datasStartAt];
				//let's fill...
				idxTotCols[0] = Integer.parseInt(grpParams1stElts[1].substring(grpParams1stElts[1].indexOf("(")+1,grpParams1stElts[1].indexOf(")"))); //1st elts
				//now the others: the starting idx is datasStartAt+1 till < szGrpParams
				int stIdx = 1;
				for (int i=datasStartAt+1; i<szGrpParams; i++,stIdx++) 
					idxTotCols[stIdx] = Integer.parseInt(grpParams[i].substring(grpParams[i].indexOf("(")+1,grpParams[i].indexOf(")")));
			}	
	        int norecCount=0;
			while(rs.next())
			{

				//-------------Added by Ashraf------------
				
				if (grpParam!=null && !grpParam.trim().equals("") && !grpParam.trim().equals("null")) {
				//Here is the critera on which we got to make the subtots
					String tmpValGrpCol = rs.getString(idxGrpCol+1); //should be +1 coz of the getString function
					if ( grpCol.length()!=0 && !grpCol.equals(tmpValGrpCol) ) {
						form = new DecimalFormat(strFormat);
						rsRowCount++; 
						int stIdxSubTots = 0;
						if (vecValues[0]!=null) {
							vecValues[0].add("Group Total");
							for (int colNo=1; colNo < rsColCount; colNo++) {
								if (colNo==idxGrpCol) vecValues[colNo].add("Grp Total"); //the label group total
								else 
								if (isInArray(idxTotCols,colNo)) {
									vecValues[colNo].add(form.format(subTots[stIdxSubTots]));
									stIdxSubTots++;
								} else vecValues[colNo].add("0.00");
							}
						}
						//reinit tot vars
						for (int i=0; i<subTots.length; i++) subTots[i] = 0;
					}
					for (int i=0; i<subTots.length; i++) subTots[i] += rs.getDouble(idxTotCols[i]+1);
					grpCol = tmpValGrpCol;
				}

				for(int colNo=0; colNo < rsColCount; colNo++)  
				{
					String strVal = rs.getString(colNo+1);
					if( (strVal==null) || (strVal.trim().equals("")) )	strVal="--";	// i.e if string is null value
					if(rsRowCount==0)	vecValues[colNo]=new Vector();
					vecValues[colNo].add(strVal);
				}

					///// BELOW CODE FOR GROUPING AND BREAKING ON CHANGE //////
				if(arrGroupCols.length>0)
				{
					for(int i=arrGroupCols.length-2; i >= 0; i--)	/// Traverse thro Group Cols except the last col
					{
						int intColNo = Integer.parseInt(arrGroupCols[i]);
						String strVal = vecValues[intColNo].get(rsRowCount).toString().trim();
						if(strVal.equals("--"))	strVal="0";	// i.e if string is null value subst by ZERO
						if(rsRowCount==0)
						{
							strGrpColVal[i] = strVal;
							HTBrkVals.put( Integer.toString(rsRowCount)+"_"+Integer.toString(intColNo), Integer.toString(intColNo) );
						}
						else
						{
							if(!strVal.equalsIgnoreCase(strGrpColVal[i]))
							{
								for(int j=i; j<arrGroupCols.length-1; j++)	
								{
									//System.out.println("To HT: "+rsRowCount+"_"+arrGroupCols[j]+"-->"+intColNo);
									HTBrkVals.put( Integer.toString(rsRowCount)+"_"+arrGroupCols[i], arrGroupCols[i] );
								}
								//System.out.println("To HT: "+rsRowCount+"-->"+arrGroupCols[arrGroupCols.length-1]);
								HTBrkVals.put( Integer.toString(rsRowCount), arrGroupCols[arrGroupCols.length-1] );

								//System.out.println("To HT: "+rsRowCount+"_"+intColNo+"-->"+intColNo);
								HTBrkVals.put( Integer.toString(rsRowCount)+"_"+Integer.toString(intColNo), Integer.toString(intColNo) );
								strGrpColVal[i] = strVal;
							} ////  End of if (strVal not equal to strGrpColVal[i])
						} ////  End of else part of if(rsRowCount==0)
					} ////  End of for(0 to arrGroupCols.length)

					for(int i=0; i<arrGrpAggFns.length; i++)
					{
						int intLstGrpColNo = Integer.parseInt(arrGroupCols[arrGroupCols.length-1]);
						String strVal = vecValues[intLstGrpColNo].get(rsRowCount).toString().trim();
						if(strVal.equals("--"))	strVal="0";	// i.e if string is null value subst by ZERO
						int intAggFnColNo = Integer.parseInt(arrGrpAggFnCols[i]);
						String strAggFnVal = vecValues[intAggFnColNo].get(rsRowCount).toString().trim();
						if(strAggFnVal.equals("--"))	strAggFnVal="0";	// i.e if string is null value subst by ZERO

						if(rsRowCount==0)
						{
							strGrpColVal[arrGroupCols.length-1] = strVal;
							brkRowCnt[i] = 1;
							//System.out.println("Aggregating... "+strAggFnVal);
							fltAggFnVal[i] = Double.parseDouble(strAggFnVal);
							System.out.println("\n STrAggg-->"+strAggFnVal+"\tFltAFFF-->"+fltAggFnVal[i]);
							HTBrkVals.put( "0", "" );
						}
						else
						{
								///// Check if there is a break (either by change in the value of itself or forced from a superior cols break)
							if( !strVal.equalsIgnoreCase(strGrpColVal[arrGroupCols.length-1]) || HTBrkVals.containsKey(Integer.toString(rsRowCount)) )
							{
								if(arrGrpAggFns[i].equalsIgnoreCase("AVG"))	fltAggFnVal[i] /= brkRowCnt[i];

								//System.out.println("To HT: "+rsRowCount+"_"+arrGrpAggFnCols[i]+"-->"+Double.toString(fltAggFnVal[i]));
								HTBrkVals.put( Integer.toString(rsRowCount)+"_"+arrGrpAggFnCols[i], Double.toString(fltAggFnVal[i]) );
								if( !HTBrkVals.containsKey(Integer.toString(rsRowCount)) )	HTBrkVals.put( Integer.toString(rsRowCount), arrGrpAggFnCols[i] );
								strGrpColVal[arrGroupCols.length-1] = strVal;
								brkRowCnt[i] = 1;
								fltAggFnVal[i] = Double.parseDouble(strAggFnVal);
							} ////  End of if (strVal not equal to strGrpColVal[arrGroupCols.length-1])
							else
							{
								//System.out.println("Aggregating... "+strAggFnVal);
								if(arrGrpAggFns[i].equalsIgnoreCase("SUM"))	
								{
											
								fltAggFnVal[i] +=Double.parseDouble(strAggFnVal);
								

								}
								if(arrGrpAggFns[i].equalsIgnoreCase("MIN"))	fltAggFnVal[i] = Math.min( fltAggFnVal[i], Double.parseDouble(strAggFnVal) );
								if(arrGrpAggFns[i].equalsIgnoreCase("MAX"))	fltAggFnVal[i] = Math.max( fltAggFnVal[i], Double.parseDouble(strAggFnVal) );
								if(arrGrpAggFns[i].equalsIgnoreCase("AVG"))
								{
									brkRowCnt[i]++;
									fltAggFnVal[i] += Double.parseDouble(strAggFnVal);
									
								}
							} ////  End of else part of if(strVal not equal to strGrpColVal[arrGroupCols.length-1])
						} ////  End of else part of if(rsRowCount==0)
					} ////  End of for(0 to arrGroupAggFns.length)
				} ////  End of if(arrGroupCols.length>0)
				rsRowCount++;
			}	////  End of While(rs.next)
			
			//Added by Ashraf: we need to create a new Group total row for the last status //NPA-Report
			if (grpParam!=null && !grpParam.trim().equals("") && !grpParam.trim().equals("null")) {
				form = new DecimalFormat(strFormat);
				int stIdxSubTots = 0;
				//changes by Ashraf (for grouping total in fast print)
				if (vecValues[0]!=null) {
					vecValues[0].add("Group Total");
					for (int colNo=1; colNo < rsColCount; colNo++) {
						if (colNo==idxGrpCol) vecValues[colNo].add("Grp Total"); //the label group total
						else 
						if (isInArray(idxTotCols,colNo)) {
							vecValues[colNo].add(form.format(subTots[stIdxSubTots]));
							stIdxSubTots++;
						} else vecValues[colNo].add("0.00");
					}
				}
				rsRowCount++; //
			}
			
			for(int i=0; i<arrGrpAggFns.length; i++)
			{
				//System.out.println("To HT: "+rsRowCount+"_"+arrGrpAggFnCols[i]+"-->"+Double.toString(fltAggFnVal[i]));
				HTBrkVals.put( Integer.toString(rsRowCount), arrGrpAggFnCols[i] );
				System.out.println("\nGrp Ttl -->"+arrGrpAggFnCols[i]);
				HTBrkVals.put( Integer.toString(rsRowCount)+"_"+arrGrpAggFnCols[i], Double.toString(fltAggFnVal[i]) );
			}
			if(rs!=null)
//				rs.close();
			//rs = null;

			System.out.println("ResultSet Values now in Vector Array. \nGroup Break Values into Hashtable. \nNo of Rows in Rset is--"+rsRowCount);

		///// VARIABLES FOR MAINTAINING PAGE NOS, START POS, DISPLAY ETC.
			no = (rsRowCount/NoRecords);
			mod = (rsRowCount%NoRecords);
			noOfPages = ( (mod!=0)?(no+1):no );

			EndPos = (StartPos+NoRecords); 	
			if(EndPos>=rsRowCount && mod!=0)	EndPos=(StartPos+mod); 	 

			pageNo = StartPos/NoRecords+1;

				/*************** BREAKING PARMETER INFORMATION BASED ON DELIMITERS 
									AND COLLECTING THEM IN A STRING ARRAY ********************/
			System.out.println("the cjolumvasfosdofsdf"+strColTitles);
			String[] arrColTitles = splitString( strColTitles.toString(), "," ); // BREAKING COLUMN HEADERS
			String[] arrColAligns = splitString( strColAlign.toString(), "," );	// BREAKING THE ALIGNMENT INFORMATION FOR THE COLUMN VALUE DISPLAY
			String[] arrColStyles = splitString( strColStyle.toString(), "," );	// BREAKING COLUMN CSS CLASSES
			String[] arrColTitleBrks = splitString( strColTitleBrk.toString(), "," ); //BREAKING COLUMN TITLE-BREAKS
			String[] arrSkipCols = splitString( strSkipCol.toString(), "," );	//BREAKING SKIPCOLUMNS
			String[] arrLinks = splitString( strLink.toString(), "," );		//BREAKING LINKS
			String[] arrTotCols = splitString( strTotal.toString(), "," );	//BREAKING Columns to be totalled
		    String DataValues[][]=new String[rsRowCount][100];//by madhavi for fast print
		    
		    
		    Font HEADLINE_FONT2 = new Font( Font.TIMES_ROMAN , 13,Font.BOLD, new Color(0,0,128) );
			 Font HEADLINE_FONT1 = new Font( Font.TIMES_ROMAN , 9,Font.BOLD, new Color(0,0,128) );
			 Font TEXT_FONT = new Font( Font.HELVETICA , 8,Font.BOLD, new Color(0,0,0) );
			 //Font HEADLINE_FONT1 = new Font( Font.TIMES_ROMAN , 10,Font.BOLD, new Color(0,0,128) );
			 Font TEXT_FONT1 = new Font( Font.HELVETICA , 7,Font.NORMAL, new Color(0,0,128) );
		   
		  // response.setContentType("application/pdf"); 
		  // String fileName="Default";
	      // response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
		   
		   if(strSlNoFlg.trim().equals("1"))
			   ColSpace="10,"+ColSpace;
		   
		   
		   System.out.println("the values are..."+ColSpace);
		   
		   int headerwidths1[]=new int[ColSpace.split(",").length];
		   
		   String colspacearray[]=ColSpace.split(",");
		   int totalcolumsSpaces=0;
		   for(int j=0; j<colspacearray.length; j++)
		   {
		   	System.out.println("the values are..."+colspacearray[j]);
		   	totalcolumsSpaces+=Integer.parseInt(colspacearray[j]);
			   headerwidths1[j]=Integer.parseInt(colspacearray[j]);
		   }
		   
		   Document document = null;
		   if(totalcolumsSpaces<=130)
			    document = new Document(PageSize.A4, 30, 10, 50, 50);
			   if(totalcolumsSpaces>130)
				   document = new Document(PageSize.A3.rotate(),30, 10, 100f, 0f);
			 //response.setHeader("Content-Disposition", "attachment; filename=Genrep.pdf");   
			 
		 
		    writer = PdfWriter.getInstance(document,out);
		   document.open();
		   
		   int groupParams=0;
		   if(!"null".equals(strGroup.trim()))
		   {
		   if(!"".equals(strGroup))
			   groupParams=strGroup.substring(0,strGroup.indexOf("~")).split(",").length;
		   }
		   
		   boolean colgroupflag=false;
		   
		   if(!"null".equals(strColTitleBrk.trim()))
		   {
			   if(!"".equals(strColTitleBrk))
				   colgroupflag=true;
		   }
		   System.out.println("the total column spaces are"+totalcolumsSpaces);
		   
		   int tablesize=colspacearray.length;
		   
		   PdfPTable t=new PdfPTable(tablesize);
			 t.setWidthPercentage(100);
			 t.setWidths(headerwidths1);
			 
			 
			 
			 
			 PdfPCell c1 = new PdfPCell(new Phrase(strBankname,HEADLINE_FONT2));
			 c1.setBorderColor(new Color(0,0,0));
			 c1.setBorderWidth(0);
			 c1.setColspan(tablesize);
			 c1.setPadding(5);
			 c1.setHorizontalAlignment(1);
			 t.addCell(c1);
			 
			 
			  c1 = new PdfPCell(new Phrase(strBranchname,HEADLINE_FONT2));
			 c1.setBorderColor(new Color(0,0,0));
			 c1.setBorderWidth(0);
			 c1.setColspan(tablesize);
			 c1.setPadding(5);
			 c1.setHorizontalAlignment(1);
			 t.addCell(c1);
			 
			 
			  c1 = new PdfPCell(new Phrase(strAddress1,HEADLINE_FONT2));
			 c1.setBorderColor(new Color(0,0,0));
			 c1.setBorderWidth(0);
			 c1.setColspan(tablesize);
			 c1.setPadding(5);
			 c1.setHorizontalAlignment(1);
			 t.addCell(c1);
			 
			 
			 if(strPgHdrFtr.trim().length()!=0 && strPgHdrFtr.trim().indexOf('~')!=0)
				{
					String strTmpHdrAlign="center";
					strPgHdr = ( strPgHdrFtr.indexOf('~')!=(-1) )?strPgHdrFtr.substring(0,strPgHdrFtr.indexOf('~')):strPgHdrFtr;
					
					System.out.println("strPgHdr......................."+strPgHdr);
			     PageHdr=strPgHdr;//by madhavi
			     
				}
			 
			 
			  c1 = new PdfPCell(new Phrase(strPgHdr,HEADLINE_FONT2));
			 c1.setBorderColor(new Color(0,0,0));
			 c1.setBorderWidth(0);
			 c1.setColspan(tablesize);
			 c1.setPadding(5);
			 c1.setHorizontalAlignment(1);
			 t.addCell(c1);
			 
			 
			 /* if(totalcolumsSpaces<=130)
			 c1 = new PdfPCell(new Phrase("______________________________________________________________________________________________________________________________",HEADLINE_FONT1));
			 if(totalcolumsSpaces>130)
				 c1 = new PdfPCell(new Phrase("_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",HEADLINE_FONT1));
			 c1.setBorderColor(new Color(0,0,128));
			 c1.setBorderWidth(0);
			 c1.setColspan(tablesize);
			 c1.setHorizontalAlignment(1);
			 c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
			 t.addCell(c1); */
			 
			 if(strSlNoFlg.trim().equals("1"))
			 {
			 	c1 = new PdfPCell(new Phrase("Serial.No.",TEXT_FONT1));
				c1.setBorderColor(new Color(0,0,0));
				c1.setBorderWidth(1);
				c1.setNoWrap(false);
				c1.setPadding(5);
				c1.setHorizontalAlignment(1);
				t.addCell(c1);
			 }
			 
			 
			 int ColTitleBrkCtr = 0;
			 
			 int colAlign=0;
			 
			 for(int colCtr=0; colCtr < arrColTitles.length; colCtr++)
			 {
			 	if( getElemIdx(arrSkipCols,Integer.toString(colCtr))!=(-1) )	continue;
			 	boolean chk = false;
			 	int intColBegin=0, intColEnd=0, intIdx1=0, intIdx2=0;
			 	
			 	
			 	
			 	colAlign="left".equalsIgnoreCase(arrColAligns[colCtr])?Element.ALIGN_LEFT:"right".equalsIgnoreCase(arrColAligns[colCtr])?Element.ALIGN_RIGHT:Element.ALIGN_CENTER;
			 	
			 	if(ColTitleBrkCtr<arrColTitleBrks.length)
			 	{
			 		chk = true;
			 		intIdx1 = arrColTitleBrks[ColTitleBrkCtr].indexOf(';');
			 		intIdx2 = arrColTitleBrks[ColTitleBrkCtr].indexOf(';',intIdx1+1);
			 		intColBegin = Integer.parseInt( arrColTitleBrks[ColTitleBrkCtr].substring(0,intIdx1) );
			 		intColEnd = Integer.parseInt( arrColTitleBrks[ColTitleBrkCtr].substring(intIdx1+1,intIdx2) ); 
			 	}
			 	if(colCtr==intColBegin && chk)
			 	{
			  	
			 		c1 = new PdfPCell(new Phrase(arrColTitleBrks[ColTitleBrkCtr].substring(intIdx2+1) ,TEXT_FONT1));
					c1.setBorderColor(new Color(0,0,0));
					c1.setBorderWidth(1);
					c1.setNoWrap(false);
					c1.setPadding(5);
					c1.setHorizontalAlignment(colAlign);
					c1.setColspan((intColEnd-intColBegin)+1);
					t.addCell(c1);
			 		
			 						
			 		ColTitleBrkCtr++;
			 		colCtr+=(intColEnd-intColBegin);
			 	}
			 	else
			 	{
			 
					 
				c1 = new PdfPCell(new Phrase(arrColTitles[colCtr] ,TEXT_FONT1));
				c1.setBorderColor(new Color(0,0,0));
				c1.setBorderWidth(1);
				c1.setNoWrap(false);
				c1.setPadding(5);
				if(colgroupflag)
					c1.setRowspan(2);
				c1.setHorizontalAlignment(colAlign);
				t.addCell(c1);
			 	
			 	}	
			 }
			 
			 
			 
			 for(ColTitleBrkCtr = 0; ColTitleBrkCtr < arrColTitleBrks.length; ColTitleBrkCtr++)
				{
					System.out.println("the length is..."+arrColTitleBrks.length+"----"+arrColTitleBrks[ColTitleBrkCtr]);
				 	int intIdx1 = arrColTitleBrks[ColTitleBrkCtr].indexOf(';');
					int intIdx2 = arrColTitleBrks[ColTitleBrkCtr].indexOf(';',intIdx1+1);
					int intColBegin = Integer.parseInt( arrColTitleBrks[ColTitleBrkCtr].substring(0,intIdx1) );
					int intColEnd = Integer.parseInt( arrColTitleBrks[ColTitleBrkCtr].substring(intIdx1+1,intIdx2) ); 
					
					if(ColTitleBrkCtr==0)
					{
						int colspanColgrp=intColBegin;
						if(strSlNoFlg.trim().equals("1"))	
							colspanColgrp=colspanColgrp+1;
						c1 = new PdfPCell(new Phrase("" ,TEXT_FONT1));
						c1.setBorderColor(new Color(0,0,0));
						c1.setBorderWidth(1);
						c1.setNoWrap(false);
						c1.setPadding(5);
						c1.setHorizontalAlignment(1);
						c1.setColspan(colspanColgrp);
						t.addCell(c1);
						
						
					}
					
					for(int colCtr=intColBegin; colCtr <= intColEnd; colCtr++)
					{
						
						
						
						colAlign="left".equalsIgnoreCase(arrColAligns[colCtr])?Element.ALIGN_LEFT:"right".equalsIgnoreCase(arrColAligns[colCtr])?Element.ALIGN_RIGHT:Element.ALIGN_CENTER;
						c1 = new PdfPCell(new Phrase(arrColTitles[colCtr] ,TEXT_FONT1));
						c1.setBorderColor(new Color(0,0,0));
						c1.setBorderWidth(1);
						c1.setNoWrap(false);
						c1.setPadding(5);
						c1.setHorizontalAlignment(colAlign);
						
						t.addCell(c1);
						
					}
				}
			 
			 /* if(totalcolumsSpaces<=130)
				 c1 = new PdfPCell(new Phrase("_______________________________________________________________________________________________________________________________",HEADLINE_FONT1));
				 if(totalcolumsSpaces>130)
					 c1 = new PdfPCell(new Phrase("_______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",HEADLINE_FONT1));
			 c1.setBorderColor(new Color(0,0,128));
			 c1.setBorderWidth(0);
			 c1.setColspan(tablesize);
			 c1.setHorizontalAlignment(1);
			 c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
			 t.addCell(c1); */
			 
			 System.out.println("the values are 334334344"+vecValues.length+"-----------------"+rsRowCount);
			 
			 if(rsRowCount==0)
			 {
				 c1 = new PdfPCell(new Phrase("No Records Found",HEADLINE_FONT1));
				 c1.setBorderColor(new Color(0,0,128));
				 c1.setBorderWidth(0);
				 c1.setColspan(tablesize);
				 c1.setHorizontalAlignment(1);
				 c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
				 t.addCell(c1);
			 }
			 
			 if(rsRowCount>0)
			 {
			 
				 
				 int headerdisplayRows=7;
				 if(colgroupflag)
					 headerdisplayRows=headerdisplayRows+1;
				 
				 t.setHeaderRows(headerdisplayRows);
				 
				 int totRow = 0;
				 
				 	double intPgTotal[]= new double[arrTotCols.length];
					double total[]= new double[arrTotCols.length];//by madhavi for printing into txt file
					int slNo=0;
					
					for(int row=StartPos;row<EndPos;row++)
					{
						slNo++;
						//////// BELOW ROW DISPLAYED IN CASE OF GROUPING ON MULTIPLE COLUMNS
					  for(int j=0; j<arrGroupCols.length-1; j++)
					  {

							Object objRowBrkVal = HTBrkVals.get(Integer.toString(row)+"_"+Integer.toString(j));
							if( objRowBrkVal != null )	/// ie if rowbreak exists at this pos
							{
					
							  for(int i=0; i<vecValues.length; i++) {  
								if( getElemIdx(arrSkipCols,Integer.toString(i))!=(-1) )	continue;
								int idx = getElemIdx(arrGroupCols,Integer.toString(i));
								if( idx != (-1) ) {
									if( arrGroupCols[idx].equals(objRowBrkVal.toString()) ) {
						
										colAlign="left".equalsIgnoreCase(arrColAligns[i])?Element.ALIGN_LEFT:"right".equalsIgnoreCase(arrColAligns[i])?Element.ALIGN_RIGHT:Element.ALIGN_CENTER;
										c1 = new PdfPCell(new Phrase(vecValues[i].get(row).toString() ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setNoWrap(false);
										c1.setPadding(5);
										c1.setHorizontalAlignment(colAlign);
										t.addCell(c1);
										
										
					} else {	
					c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
									c1.setBorderColor(new Color(0,0,0));
									c1.setBorderWidth(1);
									c1.setPadding(5);
									c1.setNoWrap(false);
									c1.setHorizontalAlignment(1);
									t.addCell(c1);
					 
									}
								} else {
					
									c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
									c1.setBorderColor(new Color(0,0,0));
									c1.setBorderWidth(1);
									c1.setPadding(5);
									c1.setHorizontalAlignment(1);
									t.addCell(c1);
					 
								}
							  }	 //// End of for (1 to vecvalues.length) loop

					
							}  //// End of if (RowBreak != null)
						  
					  }
								
								
							if( HTBrkVals.containsKey(Integer.toString(row)) )	/// ie if rowbreak exists at this pos
							{
							
								
								
								int tmpColNo = Integer.parseInt(arrGroupCols[arrGroupCols.length-1]);
								
								//System.out.println("the values are...group values.."+tmpColNo);
					
								for(int i=0; i<vecValues.length; i++)
								{
									if( getElemIdx(arrSkipCols,Integer.toString(i))!=(-1) )	continue;
									
									if(i!=tmpColNo)
										continue;
									 
									if(strSlNoFlg.trim().equals("1")){
									c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
									c1.setBorderColor(new Color(0,0,0));
									c1.setBorderWidth(1);
									c1.setPadding(5);
									c1.setNoWrap(false);
									c1.setHorizontalAlignment(1);
									t.addCell(c1);
									 }
									
									if(i==tmpColNo)
									{
										colAlign="left".equalsIgnoreCase(arrColAligns[tmpColNo])?Element.ALIGN_LEFT:"right".equalsIgnoreCase(arrColAligns[tmpColNo])?Element.ALIGN_RIGHT:Element.ALIGN_CENTER;
										c1 = new PdfPCell(new Phrase(vecValues[tmpColNo].get(row).toString() ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setNoWrap(false);
										c1.setPadding(5);
										c1.setHorizontalAlignment(colAlign);
										t.addCell(c1);
										
									
									}
									else
									{
										c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setPadding(5);
										c1.setNoWrap(false);
										c1.setHorizontalAlignment(1);
										t.addCell(c1);
					 
									} 
								}
								
								c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
								c1.setBorderColor(new Color(0,0,0));
								c1.setColspan(tablesize-groupParams);
								c1.setBorderWidth(1);
								c1.setPadding(5);
								c1.setNoWrap(false);
								c1.setHorizontalAlignment(1);
								t.addCell(c1);
					
							} 
					

					 if ( vecValues[0]!=null && !vecValues[0].get(row).toString().equalsIgnoreCase("group total") ) { 
					
					 } else { 
					
					 }
					
						if ( vecValues[0]!=null && !vecValues[0].get(row).toString().equalsIgnoreCase("group total") ) {
							if(strSlNoFlg.trim().equals("1")) {
							
										c1 = new PdfPCell(new Phrase("N".equalsIgnoreCase(romanLettersReuire)?slNo+"":GenRepUtil.ConvertingRomanNumbers(slNo) ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setNoWrap(false);
										c1.setPadding(5);
										c1.setHorizontalAlignment(1);
										t.addCell(c1);
					
								
					 
							}
						} else {
							totRow += slNo-1 ; 
							slNo = 0; 
							
							
							c1 = new PdfPCell(new Phrase("Group Total" ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setNoWrap(false);
										c1.setPadding(5);
										c1.setHorizontalAlignment(1);
										t.addCell(c1);
						 
						}
						
							if (vecValues[0]!=null) 
							for(int col=0; col<vecValues.length; col++)	{
								if( getElemIdx(arrSkipCols,Integer.toString(col))!=(-1) )	
								continue;
								int idx = getElemIdx(arrTotCols,Integer.toString(col));
								if( idx != (-1) ) {
									String strTotColVal = vecValues[col].get(row).toString();
									if(strTotColVal.equals("-") || strTotColVal.equals("--") )	strTotColVal="0";	
									intPgTotal[idx] += Double.parseDouble( strTotColVal );
								}
								String arrLinkCols[] = new String[arrLinks.length];
								for(int i=0;i<arrLinks.length;i++) arrLinkCols[i] = arrLinks[i].substring(0,arrLinks[i].indexOf('-'));
								idx = getElemIdx(arrGroupCols,Integer.toString(col));
							  if( idx != (-1) ) {
					
										c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setNoWrap(false);
										c1.setPadding(5);
										c1.setHorizontalAlignment(1);
										t.addCell(c1);
					
							  } else {
								int intLnkIdx = getElemIdx(arrLinkCols,Integer.toString(col));
								if( intLnkIdx!=(-1) ) {
									int idx1 = arrLinks[intLnkIdx].indexOf('-');
									int idx2 = arrLinks[intLnkIdx].indexOf('~');
									String strUrl;
									if(idx2==(-1)) strUrl = arrLinks[intLnkIdx].substring(idx1+1);
									else strUrl = arrLinks[intLnkIdx].substring(idx1+1,idx2) + vecValues[Integer.parseInt( arrLinks[intLnkIdx].substring(idx2+1) )].get(row) ;
					
									colAlign="left".equalsIgnoreCase(arrColAligns[col])?Element.ALIGN_LEFT:"right".equalsIgnoreCase(arrColAligns[col])?Element.ALIGN_RIGHT:Element.ALIGN_CENTER;
						      			c1 = new PdfPCell(new Phrase(vecValues[col].get(row).toString() ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setPadding(5);
										c1.setNoWrap(false);
										c1.setHorizontalAlignment(colAlign);
										t.addCell(c1);
						
						
						
					 
								} else {
					
						
						 if( vecValues[col]!=null && !vecValues[col].get(row).equals("0.00") ) {
							 colAlign="left".equalsIgnoreCase(arrColAligns[col])?Element.ALIGN_LEFT:"right".equalsIgnoreCase(arrColAligns[col])?Element.ALIGN_RIGHT:Element.ALIGN_CENTER;
						 				c1 = new PdfPCell(new Phrase(vecValues[col].get(row).toString() ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setPadding(5);
										c1.setNoWrap(false);
										c1.setHorizontalAlignment(colAlign);
										t.addCell(c1);
								
						 } else { 
						   			c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
										c1.setBorderColor(new Color(0,0,0));
										c1.setBorderWidth(1);
										c1.setPadding(5);
										c1.setNoWrap(false);
										c1.setHorizontalAlignment(1);
										t.addCell(c1);
						 } 
					
						}
						}		
						}		
					 
						}	
					 
						
										
					if(arrTotCols.length>0)
					{
				
				
				 
						 form = new DecimalFormat(strFormat); 
					  if (strSlNoFlg.trim().equals("1")) {
					  
					  				c1 = new PdfPCell(new Phrase("N".equalsIgnoreCase(romanLettersReuire)?totRow+"":GenRepUtil.ConvertingRomanNumbers(totRow) ,TEXT_FONT));
									c1.setBorderColor(new Color(0,0,0));
									c1.setBorderWidth(1);
									c1.setNoWrap(false);
									c1.setPadding(5);
									c1.setHorizontalAlignment(1);
									t.addCell(c1);
				
					         
				
					  }
					  for(int i=0; i<vecValues.length ;i++)	{
						if( getElemIdx(arrSkipCols,Integer.toString(i))!=(-1) )	continue;
						int idx = 0;
						if( ( idx = getElemIdx(arrTotCols,Integer.toString(i)) ) != (-1) ) {

					
					
						//if ((grpParam!=null && !grpParam.trim().equals("") && !grpParam.trim().equals("null")) ) out.print(form.format(intPgTotal[idx]/2));
						
						
									c1 = new PdfPCell(new Phrase(form.format(intPgTotal[idx])+"" ,TEXT_FONT));
									c1.setBorderColor(new Color(0,0,0));
									c1.setBorderWidth(1);
									c1.setPadding(5);
									c1.setNoWrap(false);
									c1.setHorizontalAlignment(2);
									t.addCell(c1);
					
					 } else if( ( idx = getElemIdx(arrTotCols,Integer.toString(i+1)) ) != (-1) )	{ 
						
						c1 = new PdfPCell(new Phrase("Total" ,TEXT_FONT));
									c1.setBorderColor(new Color(0,0,0));
									c1.setBorderWidth(1);
									c1.setPadding(5);
									c1.setNoWrap(false);
									c1.setHorizontalAlignment(0);
									t.addCell(c1);
						
						
					 } else { 
						c1 = new PdfPCell(new Phrase("" ,TEXT_FONT));
									c1.setBorderColor(new Color(0,0,0));
									c1.setBorderWidth(1);
									c1.setPadding(5);
									c1.setNoWrap(false);
									c1.setHorizontalAlignment(1);
									t.addCell(c1);
					 
						}
					  }	
				 } 			
									
			 }				
			 document.add(t);
			 document.close();
			 writer.flush();
		} // end of main try
		catch (Exception e) {
			System.out
					.println("Main Exception in generating the excel report..........."
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("finally");
		}
		return file;
	}

	public String[] splitString(String strMain, String strDelim) {
		StringTokenizer st = new StringTokenizer(strMain, strDelim);
		String strArr[] = new String[st.countTokens()];
		for (int i = 0; st.hasMoreTokens(); i++)
			strArr[i] = st.nextToken();
		return strArr;
	}

	// ////Function accepts a string array & searches for a string element in
	// it.
	// ////Returns the index if it exists else -1 is returned
	public int getElemIdx(String[] strArr, String strChk) {
		for (int i = 0; i < strArr.length; i++)
			if (strChk.equals(strArr[i].trim()))
				return i;
		return (-1);
	}

	private boolean isInArray(int[] arr, int val) {
		boolean b = false;
		int l = arr.length;
		for (int i = 0; i < l; i++) {
			if (arr[i] == val) {
				b = true;
				break;
			} else
				b = false;
		}
		return b;
	}

}
