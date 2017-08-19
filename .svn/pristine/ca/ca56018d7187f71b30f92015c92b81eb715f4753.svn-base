<%@page import="com.vsoftcorp.kls.report.action.ProductWiseCollectionReportActionServlet"%>
<%@page import="org.apache.shiro.session.Session"%>
<%@page import="com.vsoftcorp.kls.report.action.InterestChargedActionServlet"%>
<%
String error = "<h1> Error Occured While Report Generation  </h1>";
String serverFileName=ProductWiseCollectionReportActionServlet.callFastPrint((String)session.getAttribute("txtFileName"));
%><html>
<body>
	<form id="test">
		<table>
			<tr>
				<td>
					<div id="OvrLap"></DIV>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<script>
printFromApplet("<%=serverFileName%>");
	alert("Printing is complete");

	function printFromApplet(serverFileName) {

		callApplet(serverFileName);

	}

	function callApplet(serverFileName) {
		var value = navigator.userAgent;
		var st = '<applet code="AWTApplet.class" width="10" height="10" id="myApplet" archive="../Applet/AWTApplet.jar" MAYSCRIPT>';
		st = st + '<param name="userAgent" value="'+value+'"></param>';
		st = st
				+ '<param name="serverFileName" value="'+serverFileName+'"></param>';
		st = st + '<param name="noOfLinesPerPage" value="61"></param>';
		st = st + '<param name="condensedYesNo" value="Y"></param>';
		st = st + '</applet>';

		document.getElementById("OvrLap").innerHTML = st;
		//alert("the value is... "+st);

		//    deleteServerFile(serverFileName);    
	}
</script>

