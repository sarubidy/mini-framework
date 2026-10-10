package controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
            Object resultat = methode.invoke(instance, buildArguments(methode, request));

            if (methode.isAnnotationPresent(JsonAnnotation.class)) {
                response.setContentType("application/json;charset=UTF-8");
                Object data = resultat instanceof ModelAndView
                        ? ((ModelAndView) resultat).getList()
                        : resultat;
                response.getWriter().print(gson.toJson(data));
                return;
            }

            if (resultat instanceof ModelAndView) {
                ModelAndView mv = (ModelAndView) resultat;
                String urlSuivant = prefixe + mv.getUrlSuivant() + suffixe;
                request.setAttribute("prefixe", prefixe);
                if (mv.getList() != null) {
                    for (Map.Entry<String, String> entry : mv.getList().entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
                request.getRequestDispatcher(urlSuivant).forward(request, response);
                return;
            }

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2>URL trouvée</h2>");
            response.getWriter().println("<p>Route : " + route + "</p>");
            response.getWriter().println("<p>Méthode appelée : " + methode + "</p>");
            if (resultat != null) {
                response.getWriter().println("<p>Résultat : " + resultat + "</p>");
            }

        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'invocation de la méthode", e);
        }
    }

    private Object[] buildArguments(Method methode, HttpServletRequest request) throws ServletException {
        Parameter[] parameters = methode.getParameters();
        Object[] arguments = new Object[parameters.length];
        Object springContext = getServletContext().getAttribute("springContext");

        for (int index = 0; index < parameters.length; index++) {
            Class<?> parameterType = parameters[index].getType();
            if (index == 0 && springContext != null && parameterType.isInstance(springContext)) {
                arguments[index] = springContext;
                continue;
            }
            if (index == 0 && springContext == null && !isRequestParameterType(parameterType)) {
                throw new ServletException(
                        "Le premier paramètre de " + methode.getName()
                                + " nécessite un springContext disponible dans le ServletContext");
            }

            String value = request.getParameter(parameters[index].getName());
            arguments[index] = convertParameter(value, parameterType);
        }
        return arguments;
    }

    private boolean isRequestParameterType(Class<?> type) {
        return type == String.class
                || type == int.class || type == Integer.class
                || type == long.class || type == Long.class
                || type == double.class || type == Double.class
                || type == float.class || type == Float.class
                || type == short.class || type == Short.class
                || type == byte.class || type == Byte.class
                || type == boolean.class || type == Boolean.class
                || type == char.class || type == Character.class
                || type.isEnum();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object convertParameter(String value, Class<?> targetType) throws ServletException {
        if (value == null || value.isEmpty()) {
            if (!targetType.isPrimitive()) {
                return null;
            }
            if (targetType == boolean.class) {
                return false;
            }
            if (targetType == char.class) {
                return '\0';
            }
            return (targetType == byte.class) ? (byte) 0
                    : (targetType == short.class) ? (short) 0
                    : (targetType == int.class) ? 0
                    : (targetType == long.class) ? 0L
                    : (targetType == float.class) ? 0.0f
                    : 0.0d;
        }

        try {
            if (targetType == String.class) return value;
            if (targetType == int.class || targetType == Integer.class) return Integer.valueOf(value);
            if (targetType == long.class || targetType == Long.class) return Long.valueOf(value);
            if (targetType == double.class || targetType == Double.class) return Double.valueOf(value);
            if (targetType == float.class || targetType == Float.class) return Float.valueOf(value);
            if (targetType == short.class || targetType == Short.class) return Short.valueOf(value);
            if (targetType == byte.class || targetType == Byte.class) return Byte.valueOf(value);
            if (targetType == boolean.class || targetType == Boolean.class) return Boolean.valueOf(value);
            if (targetType == char.class || targetType == Character.class) return value.charAt(0);
            if (targetType.isEnum()) return Enum.valueOf((Class<? extends Enum>) targetType, value);
        } catch (IllegalArgumentException e) {
            throw new ServletException("Valeur invalide pour le paramètre de type " + targetType.getName(), e);
        }

        throw new ServletException("Type de paramètre non supporté : " + targetType.getName());
    }

}