<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Formulaire</title></head>
<body>
    <h1>Formulaire de test</h1>
    <form method="post" action="${pageContext.request.contextPath}/form">
        <label>Nom <input type="text" name="nom"></label>
        <label>Age <input type="number" name="age"></label>
        <button type="submit">Envoyer</button>
    </form>
</body>
</html>