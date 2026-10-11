<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Binding multiple</title></head>
<body>
    <h1>Deux objets du même type</h1>
    <form method="post" action="${pageContext.request.contextPath}/form-multiple">
        <fieldset>
            <legend>Personne 1</legend>
            <input name="personne1.nom" placeholder="Nom 1">
            <input name="personne1.age" type="number" placeholder="Age 1">
        </fieldset>
        <fieldset>
            <legend>Personne 2</legend>
            <input name="personne2.nom" placeholder="Nom 2">
            <input name="personne2.age" type="number" placeholder="Age 2">
        </fieldset>
        <button type="submit">Comparer les personnes</button>
    </form>
</body>
</html>