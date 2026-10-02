package controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import annotation.Annotation;
import annotation.JsonAnnotation;
import annotation.UrlAnnotation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.HTTPmethode;
import model.MethodeInfo;
import model.UrlInfo;
import model.ModelAndView;
import util.Utilitaire;

public class FrontControllerServlet extends HttpServlet {
    private HashMap<UrlInfo, MethodeInfo> mapping = new HashMap<>();
    private String prefixe = "";
    private String suffixe = "";
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        String packageName = getServletContext().getInitParameter("controller-package");
        try {
            Utilitaire.getClassesWithAnnotation(
                    packageName,
                    Annotation.class,
                    UrlAnnotation.class,
                    this.mapping);
        } catch (Exception e) {
            throw new ServletException("Impossible de charger les contrôleurs annotés", e);
        }
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
            Method methode = method.getMethode();
            Object instance = methode.getDeclaringClass().getDeclaredConstructor().newInstance();
            Object resultat = methode.invoke(instance);

            if (methode.isAnnotationPresent(JsonAnnotation.class)) {
                response.setContentType("application/json;charset=UTF-8");
                Object data = resultat instanceof ModelAndView
                        ? ((ModelAndView) resultat).getList()
                        : resultat;
                response.getWriter().print(gson.toJson(data));
                return;
            }

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2>URL trouvée</h2>");
            response.getWriter().println("<p>Route : " + route + "</p>");
            response.getWriter().println("<p>Méthode appelée : " + methode + "</p>");
            if (resultat != null) {
                response.getWriter().println("<p>Résultat : " + resultat + "</p>");
            }

            if (resultat instanceof ModelAndView) {
                ModelAndView mv = (ModelAndView) resultat;
                String urlSuivant = mv.getUrlSuivant();
                urlSuivant = prefixe + urlSuivant + suffixe;
                request.setAttribute("prefixe", prefixe);
                for(Map.Entry<String, String> entry : mv.getList().entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(urlSuivant).forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'invocation de la méthode", e);
        }
    }

}