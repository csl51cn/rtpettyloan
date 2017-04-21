package com.global.fems.interfaces.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author:feng.chen
 * @date:2014-2-17
 * @version:V1.0
 */
public class BaseServlet extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7705433878216446910L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public BaseServlet() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("content-type", "application/x-www-form-urlencoded");
		super.service(req, resp);
	}

	/**
	 * 
	 * @param response
	 * @param rspXml
	 * @throws IOException
	 */
	protected void response(HttpServletResponse response, String rspXml)
			throws IOException {
		PrintWriter writer = response.getWriter();
		writer.print(rspXml);
		writer.flush();
		writer.close();
	}

}
