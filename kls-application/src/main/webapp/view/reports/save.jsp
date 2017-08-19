<%@page import="org.apache.shiro.session.Session"%>
<%@page import="com.vsoftcorp.kls.report.util.ReportUtil"%>
<%
String error = "<h1> Error Occured While Report Generation  </h1>";
String filename=ReportUtil.saveToFile((String)session.getAttribute("outDatName"),"",(String)session.getAttribute("txtFileName"));
System.out.println("file====="+(String)session.getAttribute("reportName"));
String reportName=(String)session.getAttribute("reportName");
System.out.println("file====="+filename);
  response.setContentType("APPLICATION/OCTET-STREAM");   
  response.setHeader("Content-Disposition","attachment; filename="+reportName);   
  
  java.io.FileInputStream fileInputStream=new java.io.FileInputStream(filename);  
            
  int i;   
  while ((i=fileInputStream.read()) != -1) {  
    out.write(i);   
  }   
  fileInputStream.close();   
%>   