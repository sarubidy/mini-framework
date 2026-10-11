<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Binding avance</title></head>
<body>
    <h1>Binding d'objets imbriques</h1>
    <form method="post" action="${pageContext.request.contextPath}/form-avance">
        <fieldset>
            <legend>Personne 1</legend>
            <input name="personne1.nom" placeholder="Nom">
            <input name="personne1.age" type="number" placeholder="Age">
            <input name="personne1.adresse.lot" placeholder="Lot">
            <input name="personne1.adresse.parcelle" placeholder="Parcelle">
        </fieldset>
        <fieldset>
            <legend>Personne 2</legend>
            <input name="personne2.nom" placeholder="Nom">
            <input name="personne2.age" type="number" placeholder="Age">
            <input name="personne2.adresse.lot" placeholder="Lot">
            <input name="personne2.adresse.parcelle" placeholder="Parcelle">
        </fieldset>
        <label>Commentaire <input name="commentaire"></label>
        <button type="submit">Envoyer</button>
    </form>
</body>
</html>