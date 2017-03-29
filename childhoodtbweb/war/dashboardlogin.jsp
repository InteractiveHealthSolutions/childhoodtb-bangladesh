<%-- Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Non Suspect Dashboard Login</title>
</head>
<body>
<form id="nonSuspectLogin" name="NonSuspectLogin" action="NonSuspectLoginServlet" method="post">
<h3>Non Suspect Dashboard Login</h3>
Please provide OpenMRS Login Credentials.

<table style="border: 1px; border-color: black;">

<tr>
<td>Username: <input type="text" name="username" id="usernameId"></td>
<td>Password: <input type="password" name="password" id="passwordId"></td>
<tr><td><input type="submit" value="Login" name="authenticate"/></td></tr>
</tr>
</table>

</form>
</body>
<script type="text/javascript">
if('${authentic}' == "failed")
{
	alert("Authentication Failed. Please check username and password again.");
	document.getElementById('usernameId').style.borderColor = "red";
	document.getElementById('passwordId').style.borderColor = "red";
}
</script>
</html>
