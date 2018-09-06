package com.zhangb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SessionShareServlet() {
        super();
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getContextPath());
		System.out.println(request.getServletPath());
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if ("/index".equals(request.getServletPath())) {
			HttpSession session = request.getSession();
			session.setAttribute(username, password);
			response.sendRedirect("login.jsp?username=" + username);
		} else {
			HttpSession session = request.getSession();
			String value = (String) session.getAttribute(username);
			System.out.println(value);
			response.sendRedirect("index.jsp");
		}
	}

}
