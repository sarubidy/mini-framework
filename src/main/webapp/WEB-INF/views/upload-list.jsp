<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Fichiers uploadés</title></head>
<body>
    <h1>Fichiers uploadés</h1>
    <ul>${fichiers}</ul>
    <a href="${pageContext.request.contextPath}/upload">Uploader d'autres fichiers</a>
</body>
</html>