<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Lecture fichier</title></head>
<body>
    <h1>${nom}</h1>
    <p>Type : ${type}</p>
    <% if (request.getAttribute("image") != null) { %>
        <img src="${image}" alt="${nom}" style="max-width: 800px; max-height: 600px;">
    <% } else { %>
        <pre>${contenu}</pre>
    <% } %>
    <a href="${pageContext.request.contextPath}/upload/list">Retour à la liste</a>
</body>
</html>