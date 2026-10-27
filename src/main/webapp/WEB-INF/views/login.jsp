<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Test de session</title>
</head>
<body>
    <h1>Test de session utilisateur</h1>
    <p>Entrez un nom pour le stocker dans la session HTTP.</p>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label for="username">Nom d'utilisateur</label>
        <input id="username" type="text" name="username" required>
        <button type="submit">Se connecter</button>
    </form>

    <p>
        Après la connexion, consultez
        <a href="${pageContext.request.contextPath}/profil">votre profil</a>
        pour relire la valeur depuis la session.
    </p>
</body>
</html>
