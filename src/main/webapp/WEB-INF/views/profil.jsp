<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Profil</title></head>
<body>
    <h1>Profil protégé</h1>
    <p>Utilisateur connecté : <strong>${username}</strong></p>
    <p>Rôle : <strong>${role}</strong></p>
    <p><a href="${pageContext.request.contextPath}/admin">Accéder à l'espace ADMIN</a></p>
</body>
</html>
