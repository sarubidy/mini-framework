package controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontControllerServlet extends HttpServlet {
    private HashMap<String,Method> mapping = new HashMap<>();

    @Override
    public void init() throws ServletException {
        String packageName = getServletConfig().getInitParameter("controller-package");
        try {
            Utilitaire.getClassesWithAnnotation(packageName, Annotation.class,UrlAnnotation.class,mapping);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

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

    response.setContentType("text/html;charset=UTF-8");
    String contextPath = request.getContextPath();
    String requestURI = request.getRequestURI();
    String route = requestURI.substring(contextPath.length());
    if (route.equals("")) {
        route = "/";
    }

    Method method = mapping.get(route);

    if (method == null) {
        for (HashMap.Entry<String, Method> entry : mapping.entrySet()) {
            String url = entry.getKey();
            Method m = entry.getValue();

            response.getWriter().println(
                "<p>URL : " + url + " -> Méthode : " + m.getName() + "</p>"
            );
        }        return;
    }

    response.getWriter().println("<h2>URL trouvée</h2>");
    response.getWriter().println("<p>Route : " + route + "</p>");
    response.getWriter().println("<p>Méthode appelée : " + method.getName() + "</p>");
}

}