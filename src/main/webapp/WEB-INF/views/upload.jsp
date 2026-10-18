<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Upload CIN</title></head>
<body>
    <h1>Upload des deux côtés de la CIN</h1>
    <form method="post" enctype="multipart/form-data"
          action="${pageContext.request.contextPath}/upload">
        <label>Recto CIN
            <input type="file" name="formulaire.mapFile.rectoCIN" required>
        </label>
        <label>Verso CIN
            <input type="file" name="formulaire.mapFile.verseauCIN" required>
        </label>
        <button type="submit">Envoyer les fichiers</button>
    </form>
</body>
</html>