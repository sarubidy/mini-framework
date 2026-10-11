<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Binding récursif</title></head>
<body>
    <h1>Objet imbriqué</h1>
    <form method="post" action="${pageContext.request.contextPath}/form-recursif">
        <input name="personne.nom" placeholder="Nom">
        <input name="personne.age" type="number" placeholder="Age">
        <input name="personne.adresse.lot" placeholder="Lot">
        <input name="personne.adresse.parcelle" placeholder="Parcelle">
        <button type="submit">Tester l'adresse</button>
    </form>
</body>
</html>