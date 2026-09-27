package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.HTTPmethode;
import model.MethodeInfo;
import model.UrlInfo;
import model.ModelAndView;
import util.JsonUtil;

public class FrontControllerServlet extends HttpServlet {
    private HashMap<UrlInfo, MethodeInfo> mapping = new HashMap<>();
    private String prefixe = "";
    private String suffixe = "";

    @Override
    public void init() throws ServletException {
        this.mapping = (HashMap<UrlInfo, MethodeInfo>) getServletContext().getAttribute("mapping");
        this.prefixe = (String) getServletContext().getInitParameter("view-prefix");
        this.suffixe = (String) getServletContext().getInitParameter("view-suffix");
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

        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String route = requestURI.substring(contextPath.length());
        if (route.equals("")) {
            route = "/";
        }

        HTTPmethode httpMethode = HTTPmethode.valueOf(request.getMethod());

        UrlInfo recherche = new UrlInfo();
        recherche.setUrl(route);
        recherche.setAction(httpMethode);

        MethodeInfo method = mapping.get(recherche);

        if (method == null) {
            for (HashMap.Entry<UrlInfo, MethodeInfo> entry : mapping.entrySet()) {
                UrlInfo url = entry.getKey();
                MethodeInfo m = entry.getValue();

                response.getWriter().println(
                        "<p>URL : " + url.getUrl() + " ,  Méthode : " + m.getMethode() + "  , Action :"
                                + url.getAction());
            }
            return;
        }

        try {
            Class<?> clazz = method.getMethode().getDeclaringClass();
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Object resultat = method.getMethode().invoke(instance);
            if (resultat instanceof ModelAndView) {
                ModelAndView mv = (ModelAndView) resultat;
                String urlSuivant = mv.getUrlSuivant();
                urlSuivant = prefixe + urlSuivant + suffixe;
                request.setAttribute("prefixe", prefixe);
                for(Map.Entry<String, String> entry : mv.getList().entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(urlSuivant).forward(request, response);
            } else if (resultat instanceof String) {
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(resultat);
            } else {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JsonUtil.toJson(resultat));
            }

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'invocation de la méthode", e);
        }
    }

}