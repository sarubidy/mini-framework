<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Connexion</title></head>
<body>
    <h1>Connexion réussie</h1>
    <p>Utilisateur : <strong>${username}</strong></p>
    <p>Rôle : <strong>${role}</strong></p>
    <p><a href="${pageContext.request.contextPath}/profil">Ouvrir le profil protégé</a></p>
    <p><a href="${pageContext.request.contextPath}/admin">Tester la page ADMIN</a></p>
</body>
</html>
