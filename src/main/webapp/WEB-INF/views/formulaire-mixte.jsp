<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Binding mixte</title></head>
<body>
    <h1>Objet et paramètres simples</h1>
    <form method="post" action="${pageContext.request.contextPath}/form-mixte">
        <input name="personne.nom" placeholder="Nom">
        <input name="personne.age" type="number" placeholder="Age">
        <input name="commentaire" placeholder="Commentaire">
        <input name="score" type="number" placeholder="Score">
        <button type="submit">Envoyer les données mixtes</button>
    </form>
</body>
</html>