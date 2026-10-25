# Mini-framework MVC

## Sessions utilisateur

Une session peut être injectée directement dans une méthode de contrôleur avec
`com.miniframework.mvc.Session` :

```java
@UrlAnnotation(value = "/login", httpmethode = HTTPmethode.POST)
@JsonAnnotation
public Map<String, String> login(String username, Session session) {
    session.set("username", username);
    return Map.of("message", "Connexion réussie");
}

@UrlAnnotation(value = "/profil", httpmethode = HTTPmethode.GET)
@JsonAnnotation
public Map<String, Object> profil(Session session) {
    return Map.of("username", session.get("username"));
}
```

Le binder injecte automatiquement `Session` à partir de `request.getSession()`.
L’injection directe de `jakarta.servlet.http.HttpSession` est également
supportée lorsque l’API Servlet est nécessaire.

Ne retournez jamais l’objet `Session` directement dans une réponse JSON :
Gson pourrait tenter de sérialiser l’objet `HttpSession` interne. Retournez
uniquement les valeurs ou les structures extraites de la session.
