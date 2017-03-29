<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.ihsinformatics.childhoodtb_web.server.MobileService"%>
<%
	String jsonResponse = MobileService.getService().handleEvent (request);
	System.out.println (jsonResponse);
	out.println (jsonResponse);
%>