package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        String route = requestURI.substring(contextPath.length());

        if (route.equals("")) {
            route = "/";
        }

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println("<h1>FrontControllerServlet</h1>");
        response.getWriter().println("<p>Context path : " + contextPath + "</p>");
        response.getWriter().println("<p>Request URI : " + requestURI + "</p>");
        response.getWriter().println("<p>Route capturée : " + route + "</p>");
        response.getWriter().println("<p>Méthode HTTP : " + method + "</p>");
    }
}