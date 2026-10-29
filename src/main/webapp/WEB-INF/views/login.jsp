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
    <p style="color: firebrick">${error}</p>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label for="username">Nom d'utilisateur</label>
        <input id="username" type="text" name="username" required>
        <label for="password">Mot de passe</label>
        <input id="password" type="password" name="password" required>
        <button type="submit">Se connecter</button>
    </form>

    <h2>Données de test</h2>
    <ul>
        <li><code>alice / alice123</code> (rôle USER)</li>
        <li><code>admin / admin123</code> (rôle ADMIN)</li>
    </ul>

    <p>
        Après la connexion, consultez
        <a href="${pageContext.request.contextPath}/profil">votre profil</a>
        pour relire la valeur depuis la session.
    </p>
</body>
</html>
