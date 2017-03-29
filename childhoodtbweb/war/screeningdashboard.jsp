<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.ihsinformatics.childhoodtb_web.server.MobileService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Insert title here</title>
<head>
<base href="${pageContext.request.contextPath}">
<link rel="stylesheet" href="tbreach4web_pk/tbr3design.css" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script>
$(document).ready(function() 
{
 	if(jQuery)
 	{
	    $('#treatment_type').click(function() {
	    	$('input[name="paramTreatmentTypeRadio"]').prop('disabled', !this.checked);
		});
		$('#patient_type').click(function() {
	    	$('input[name="paramPatientTypeRadio"]').prop('disabled', !this.checked);
		});
	}
	
// 	var household = document.getElementById("household");
// 	var hospital = document.getElementById("hospital");
// 	if(household.checked == true)
// 	{
// 		alert('household');
// 	}
// 	else if(hospital.checked == true)
// 	{
// 		alert('hospital');
// 	}
});
 
    </script>

<%
String bothChecked =" checked";
	String householdChecked ="";
	String hospitalChecked = "";
%>
<div class="centerDiv">
<form id="screeningDashboardForm" name="ScreeningDashboard" action="tbreach4web_pk/ScreeningDashboardServlet" method="get">
<% 
//allow access if only login is successful

String username = "";
String password = "";
String[] users = null;
String[] locations = null;

HttpSession userSession = request.getSession(false);
String userName = (String) session.getAttribute("user_name");
if(userName == null) 
{
	//response.sendRedirect("/tbreach4web_pk/dashboardlogin.jsp");
	RequestDispatcher rd = request.getRequestDispatcher("screeningdashboardlogin.jsp");
	   rd.forward(request, response);
}
else
{
	username = (String) userSession.getAttribute("user_name");
	password = (String) userSession.getAttribute("pwd");
	users = MobileService.getService().getUsers(username, password);
	locations = MobileService.getService().getLocations(username, password);
}
%>
<label class="titleHeading">Screening Summary</label>
<div class="inputDiv">
<div>
<table class="table">
		<tr>
			<td><label class="criteriaLabel">Username</label></td>
			<td><label class="criteriaLabel"><%=username %></label></td>
		</tr>
		<tr>
			<td><label class="criteriaLabel">Time</label></td>
			<td><select class="criteriaDropdown" name="time" id="timeOptions">
			<option>Daily</option>
			<option>Weekly</option>
			<option>Monthly</option>
			</select>
			</td>
		</tr>
		<tr >
			<td><label class="criteriaLabel">Treatment Type</label></td>
			<td class="criteriaDropdown"><input type="checkbox" id="treatment_type" name="treatment_type">
			<input type="radio" name="paramTreatmentTypeRadio" id="household" value="Household" disabled="disabled" checked>Household 
			<input type="radio" name="paramTreatmentTypeRadio" id="hospital" value="Hospital" disabled="disabled" >Hospital</td>
		</tr>
		<tr >
			<td><label class="criteriaLabel">Patient Type</label></td>
			<td class="criteriaDropdown"><input type="checkbox" name="patient_type" id="patient_type">
			<input type="radio" name="paramPatientTypeRadio" id="adult" value="Adult" disabled="disabled" checked>Adult 
			<input type="radio" name="paramPatientTypeRadio" id="paediatric" value="Paediatric" disabled="disabled">Paediatric</td>
		</tr>
		<tr>
			<td><label class="criteriaLabel">Treatment Centre</label></td>
			<td><select class="criteriaDropdown" id="treatmentCenterId">
<%
//if(locations != null)
//{
	//for(int i = 0; i<locations.length; i++)
	//{
		%>
<%-- 		<option value=<%= locations[i] %>><%= locations[i] %></option> --%>
		
		<% 
	//} 
//}
%>
</select>
			</td>
		</tr>
	</table>
</div>

<div>
<table class="table">
<tr>
<td><input class="button" type="button" id="view" value="View Data" name="generate" onclick="getParameters()"/></td>
<td><input class="button" type="submit" id="csv" value="Download CSV" name="generateCsv"/></td>
<td><input class="button" type="submit" value="Logout" name="Logout" id="logoutId"/></td>
</tr>
</table>
</div>
</div>
</br>

<div class="outputDiv">
</br>
<table class="table" border=1>
	<tr style="font-weight: bold;">
		<td>Location</td>
		<td>Username</td>
		<td>Type</td>
		<td>Suspects</td>
		<td>Non Suspects</td>
	</tr>
		<%
			String sc = "";
			int susp = 0;
			int nSusp = 0;
			//(screeningData != null && screeningData.length > 0)
			//{
// 				sc = screeningData[0][0];
				
// 				for (int i = 0; i < screeningData.length; i++)
// 				{
// 					String location = screeningData[i][0];
					String user = "" ;
					String type = "";
					String suspectsCount = "";
					String nonSuspectsCount = "";

// 					String user = screeningData[i][1];
// 					String type = screeningData[i][2];
// 					String nonSuspectsCount = screeningData[i][3];
					
					%>
					
					<%
// 					if (!location.equals (sc))
// 					{
// 						sc = location;
						%>
						<tr style="font-weight: bold;">
							<td></td>
							<td></td>
							<td>Total</td>
							<td><%=susp%></td>
							<td><%=nSusp%></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<%
						//nSusp = 0;
					//}
					
				//	nSusp += Integer.parseInt (nonSuspectsCount);
					
				%>
				
				<tr>
						<td><%=sc%></td>
						<td><%=user%></td>
						<td><%=type%></td>
						<td><%=suspectsCount%></td>
						<td><%=nonSuspectsCount%></td>
					</tr>
					
				<%//}
			//}%>
			<tr style="font-weight: bold;">
				<td></td>
				<td></td>
				<td>Total</td>
				<td><%=susp%></td>
				<td><%=nSusp%></td>
			</tr>
			<tr></tr>
	</table>
	</br>
</div>
</form>
</div>
<!-- <div id="menu"> -->
<!--    <ul id="menu"> -->
<!-- 	<li><a href="#" title="View Data" class="selected">View Data</a></li> -->
<!-- 	<li><a href="#" title="Download CSV">Download CSV</a></li> -->
<!-- 	<li><a href="#" title="Logout">Logout</a></li> -->
<!--     </ul> -->
<!-- </div>  -->

</body>

</html>