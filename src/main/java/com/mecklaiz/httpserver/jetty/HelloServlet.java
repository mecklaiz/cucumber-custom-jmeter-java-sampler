package com.mecklaiz.httpserver.jetty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet
{
    @Override
    protected void doGet( HttpServletRequest request,
                          HttpServletResponse response ) throws ServletException,
            IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println(" <h1>Hello from HelloServlet</h1>");
        out.println(" <pre>");
        out.println("  requestURI=" + request.getRequestURI());
        out.println(" </pre>");
        out.println("</html>");
    }
}