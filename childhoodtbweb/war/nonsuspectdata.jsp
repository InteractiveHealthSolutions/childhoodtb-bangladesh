<%-- Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi --%>
<%@page import="com.ihsinformatics.childhoodtb_web.server.NonSuspectLoginServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.ihsinformatics.childhoodtb_web.server.MobileService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
  p    {color:green}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TBReach4 Pakistan Non-Suspect Dashboard</title>
</head>



<body>
<form id="nonSuspectForm" name="NonSuspect" action="NonSuspectDataServlet" method="get">
<% 
//allow access if only login is successful

String username = "";
String password = "";
String[] users = null;
String[] locations = null;

HttpSession userSession = request.getSession(false);
String userName = (String) session.getAttribute("username");
if(userName == null) 
{
	//response.sendRedirect("/tbreach4web_pk/dashboardlogin.jsp");
	RequestDispatcher rd = request.getRequestDispatcher("dashboardlogin.jsp");
	   rd.forward(request, response);
}
else
{
	username = (String) userSession.getAttribute("username");
	password = (String) userSession.getAttribute("password");
	users = MobileService.getService().getUsers(username, password);
	locations = MobileService.getService().getLocations(username, password);
}

    String[][] screeningData = null;
	String bothChecked =" checked";
	String adultChecked ="";
	String paediatricChecked = "";
	String value1 = "2014-10-01";
	String value2 = "2014-10-01";
	
	if(request.getParameter("patientType") == null)
	{
		//screeningData = MobileService.getService ().getNonSuspectScreeningData(request.getParameter("fromDate"), request.getParameter("toDate"), request.getParameter("suspectedBy"), request.getParameter("treatmentCenter"), request.getParameter("patientType"));
	}
	else
	{
		if(request.getParameter("patientType").equals("adult"))
		{
			adultChecked = " checked";
			paediatricChecked = "";
			bothChecked = "";
		}
		else if(request.getParameter("patientType").equals("paeds"))
		{
			paediatricChecked = " checked";
			adultChecked = "";
			bothChecked = "";
		}
		else if(request.getParameter("patientType").equals("both"))
		{
			bothChecked = " checked";
			adultChecked = "";
			paediatricChecked = "";
		}
		
		screeningData = MobileService.getService ().getNonSuspectScreeningData(request.getParameter("fromDate"), request.getParameter("toDate"), request.getParameter("suspectedBy"), request.getParameter("treatmentCenter"), request.getParameter("patientType"));
	}
 %>

<b>Non-Suspects Screening Summary</b>
</br>
Username : <%=username %>&nbsp;&nbsp; <input type="submit" value="Logout" name="Logout" id="logoutId"/>&nbsp;&nbsp; 
<input type="submit" id="csv" value="Non-Suspect Demographics" name="generateDemoCsv"/>

<table style="border: 1px; border-color: black;">

<p id="demo" ></p>
<tr>
<td>Date Screened: From <input type="date" id="fromDateId" name="from" value=<%= value1 %>/></td>
<td>To <input type="date" id="toDateId"  name="to" value=<%= value2 %>/></td>
</tr>

<tr>
<td>Suspect By: 
<select name="suspectBy" id="suspectById">
<%
if(users != null)
{
	for(int i = 0; i<users.length; i++)
	{
		%>
		<option value=<%= users[i] %>><%= users[i] %></option>
		
		<% 
	}
} %>
</select></td>

<td>
Treatment Center: 
<select name="treatmentCenter" id="treatmentCenterId">
<%
if(locations != null)
{
	for(int i = 0; i<locations.length; i++)
	{
		%>
		<option value=<%= locations[i] %>><%= locations[i] %></option>
		
		<% 
	} 
}%>
</select>
</tr>

<tr>
<td><input type="radio" name="paramRadio" id="adultId" value="adult" <%= adultChecked %>>Adult Non-Suspects</td>
<td><input type="radio" name="paramRadio" id="paediatricId" value="paediatric" <%= paediatricChecked %>>Paediatric Non-Suspects</td>
<td><input type="radio" name="paramRadio" id="bothId" value="both" <%= bothChecked %>>Both</td>
</tr>

<tr>
<td><input type="button" id="generate" value="View Data" name="generate" onclick="getParameters()"/></td>
<td><input type="submit" id="csv" value="Download CSV" name="generateCsv"/></td>
</tr>
</table>
-----------------------------------------------------------------------------------------------------------
<table border=1>
	<tr style="font-weight: bold;">
		<td>Location</td>
		<td>Username</td>
		<td>Type</td>
		<td>Non Suspects</td>
	</tr>
		<%
			String sc = "";
			int nSusp = 0;
			if(screeningData != null && screeningData.length > 0)
			{
				sc = screeningData[0][0];
				
				for (int i = 0; i < screeningData.length; i++)
				{
					String location = screeningData[i][0];
					String user = screeningData[i][1];
					String type = screeningData[i][2];
					String nonSuspectsCount = screeningData[i][3];
					
					%>
					
					<%
					if (!location.equals (sc))
					{
						sc = location;
						%>
						<tr style="font-weight: bold;">
							<td></td>
							<td></td>
							<td>Total</td>
							<td><%=nSusp%></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<%
						nSusp = 0;
					}
					
					nSusp += Integer.parseInt (nonSuspectsCount);
					
				%>
				
				<tr>
						<td><%=sc%></td>
						<td><%=user%></td>
						<td><%=type%></td>
						<td><%=nonSuspectsCount%></td>
					</tr>
					
				<%}
			}%>
			<tr style="font-weight: bold;">
				<td></td>
				<td></td>
				<td>Total</td>
				<td><%=nSusp%></td>
			</tr>
			<tr></tr>
	</table>

<input type="hidden" name="date1" id="dateFromId"/>
<input type="hidden" name="date2" id="dateToId"/>
</form>
</body>

<script type="text/javascript">


function getParameters()
{
	var fromDate = document.getElementById("fromDateId");
	var toDate = document.getElementById("toDateId");
	var suspectedBy = document.getElementById("suspectById");
	var treatmentCenter = document.getElementById("treatmentCenterId");
	var adult = document.getElementById("adultId");
	var paediatric = document.getElementById("paediatricId");
	var both = document.getElementById("bothId");
	
	var fromDateParam = fromDate.value; 
    var toDateParam = toDate.value;
	var suspectedByParam = suspectedBy.value;
	var treatmentCenterParam = treatmentCenter.value;
	var patientTypeParam;
	
	if(adult.checked == true)
	{
		patientTypeParam = "adult";
	}
	else if(paediatric.checked == true)
	{
		patientTypeParam = "paeds";
	}
	else if(both.checked == true)
	{
		patientTypeParam = "both";
	} 
	
	window.location = "/tbreach4web_pk/nonsuspectdata.jsp?fromDate=" + fromDateParam +"&toDate=" + toDateParam + "&suspectedBy=" + suspectedByParam + "&treatmentCenter=" + treatmentCenterParam + "&patientType="+ patientTypeParam;
	
}


function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}
//alert(location.search.split('suspectedBy=')[1]||'').split('&')[0]);

var toDateParameter = getURLParameter("toDate");
var fromDateParameter = getURLParameter("fromDate");
if(toDateParameter != null && fromDateParameter!=null)
{
	document.getElementById("demo").innerHTML = "Non Suspects data between " + fromDateParameter  + " and "+ toDateParameter + ".";
	document.getElementById("dateFromId").value = fromDateParameter;
	document.getElementById("dateToId").value = toDateParameter;
}
else
{
	document.getElementById("demo").innerHTML = "Please select to and from Date.";	
}

</script>

</html>
