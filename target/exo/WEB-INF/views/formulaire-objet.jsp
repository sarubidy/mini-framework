<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Formulaire objet</title></head>
<body>
    <h1>Formulaire objet</h1>
    <form method="post" action="${pageContext.request.contextPath}/form-objet">
        <label>Nom <input type="text" name="profil.nom"></label>
        <label>Age <input type="number" name="profil.age"></label>
        <button type="submit">Envoyer</button>
    </form>
</body>
</html>