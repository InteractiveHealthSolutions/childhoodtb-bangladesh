/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
package com.ihsinformatics.childhoodtb_web.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.uhn.hl7v2.util.MessageQuery.Result;

public class NonSuspectLoginServlet extends HttpServlet
{

	/**
	 * 
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("type/html");
        //PrintWriter out = response.getWriter();
//        
//	        if(request.getParameter("authenticate") != null)
//	        {
//	        	String result= "";
//	        	result = MobileService.getService().authenticate(request.getParameter("username"), request.getParameter("password"));
//        	
//        }
//        
//        if(request.getParameter("authenticate") != null)
//        {
//	        String username= request.getParameter("username");
//	        String password = request.getParameter("password");
//	        
//	        //String dateString = request.getParameter("toDate").toString();
//	        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd") ;
//	       // Date date = (Date) formatter.parse(dateString);
//	        
//	        String result= "";
//	    	result = MobileService.getService().authenticate(request.getParameter("username"), request.getParameter("password"));
//	    	request.setAttribute("visible", result);
//	    	request.setAttribute("user", username);
//	    	
//	    	System.out.println(request.getParameter("authenticate")) ;
//	    	System.out.println(request.getParameter("generate"));
//	    	
//	    	request.getRequestDispatcher("/nonsuspectdata.jsp").forward(request, response);
//        }
//
		
		String result = "";
		RequestDispatcher  rd;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		result = MobileService.getService().authenticate(username, password);
		
        if(result.equals("SUCCESS"))
        {
        	HttpSession userSession = request.getSession();
        	userSession.setAttribute("username", username);
        	userSession.setAttribute("password", password);
        	userSession.setMaxInactiveInterval(30*60);
        	
        	rd = request.getRequestDispatcher("/nonsuspectdata.jsp");
        	rd.forward(request, response);
        }
        else
        {
        	request.setAttribute("authentic", "failed");
        	rd = request.getRequestDispatcher("dashboardlogin.jsp");
        	
        	rd.include(request, response);
        }
        
        //out.close();
    }
	
	private static final long serialVersionUID = 1501875646687116538L;
	

}
