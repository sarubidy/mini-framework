package demo;

import annotation.Annotation;
import annotation.Authenticated;
import annotation.JsonAnnotation;
import annotation.ObjetAnnotation;
import annotation.RoleAllowed;
import annotation.UrlAnnotation;
import com.miniframework.mvc.Session;
import model.HTTPmethode;
import model.MapFile;
import model.ModelAndView;
import model.UploadedFile;
import util.UploadStore;

@Annotation
public class DemoController {
    @UrlAnnotation(value = "/", httpmethode = HTTPmethode.GET)
    public String accueil() {
        return "Le mini-framework fonctionne sur Tomcat.";
    }

    @UrlAnnotation(value = "/json", httpmethode = HTTPmethode.GET)
    @JsonAnnotation
    public DemoUser json() {
        return new DemoUser(1, "Sarobidy 3993", "sarobidy@gmail.com");
    }

    @UrlAnnotation(value = "/login", httpmethode = HTTPmethode.GET)
    public ModelAndView loginForm() {
        return new ModelAndView("login");
    }

    @UrlAnnotation(value = "/login", httpmethode = HTTPmethode.POST)
    public ModelAndView login(String username, String password, Session session) {
        String role = findRole(username, password);
        if (role == null) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Identifiants invalides.");
            return modelAndView;
        }
        session.set("authenticatedUser", username);
        session.set("authenticatedRole", role);
        ModelAndView modelAndView = new ModelAndView("login-result");
        modelAndView.addObject("username", username);
        modelAndView.addObject("role", role);
        return modelAndView;
    }

    @UrlAnnotation(value = "/profil", httpmethode = HTTPmethode.GET)
    @Authenticated
    public ModelAndView profil(Session session) {
        ModelAndView modelAndView = new ModelAndView("profil");
        modelAndView.addObject("username", (String) session.get("authenticatedUser"));
        modelAndView.addObject("role", (String) session.get("authenticatedRole"));
        return modelAndView;
    }

    @UrlAnnotation(value = "/admin", httpmethode = HTTPmethode.GET)
    @RoleAllowed("ADMIN")
    public ModelAndView admin() {
        return new ModelAndView("admin");
    }

    private String findRole(String username, String password) {
        if ("alice".equals(username) && "alice123".equals(password)) {
            return "USER";
        }
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "ADMIN";
        }
        return null;
    }

    @UrlAnnotation(value = "/vue", httpmethode = HTTPmethode.GET)
    public ModelAndView vue() {
        ModelAndView modelAndView = new ModelAndView("resultat");
        modelAndView.addObject("message", "Vue rendue par ModelAndView");
        return modelAndView;
    }

    @UrlAnnotation(value = "/form", httpmethode = HTTPmethode.GET)
    public ModelAndView form() {
        return new ModelAndView("formulaire");
    }

    @UrlAnnotation(value = "/form", httpmethode = HTTPmethode.POST)
    public ModelAndView submitForm(String nom, int age) {
        ModelAndView modelAndView = new ModelAndView("resultat");
        modelAndView.addObject("message", "Bonjour " + nom + ", age : " + age);
        return modelAndView;
    }

    @UrlAnnotation(value = "/form-objet", httpmethode = HTTPmethode.GET)
    public ModelAndView formObjet() {
        return new ModelAndView("formulaire-objet");
    }

    @UrlAnnotation(value = "/form-objet", httpmethode = HTTPmethode.POST)
    public ModelAndView submitFormObjet(@ObjetAnnotation ProfilForm profil) {
        ModelAndView modelAndView = new ModelAndView("resultat");
        modelAndView.addObject("message", "Profil : " + profil.nom + ", age : " + profil.age);
        return modelAndView;
    }

    @UrlAnnotation(value = "/upload", httpmethode = HTTPmethode.GET)
    public ModelAndView upload() {
        return new ModelAndView("upload");
    }

    @UrlAnnotation(value = "/upload", httpmethode = HTTPmethode.POST)
    public ModelAndView submitUpload(@ObjetAnnotation UploadForm formulaire) {
        MapFile fichiers = formulaire.mapFile;
        UploadStore.store(fichiers);
        ModelAndView modelAndView = new ModelAndView("upload-list");
        modelAndView.addObject("fichiers", buildFileLinks());
        return modelAndView;
    }

    @UrlAnnotation(value = "/upload/list", httpmethode = HTTPmethode.GET)
    public ModelAndView listUploads() {
        ModelAndView modelAndView = new ModelAndView("upload-list");
        modelAndView.addObject("fichiers", buildFileLinks());
        return modelAndView;
    }

    @UrlAnnotation(value = "/upload/delete", httpmethode = HTTPmethode.POST)
    public ModelAndView deleteUpload(String id) {
        UploadStore.delete(id);
        ModelAndView modelAndView = new ModelAndView("upload-list");
        modelAndView.addObject("fichiers", buildFileLinks());
        return modelAndView;
    }

    @UrlAnnotation(value = "/upload/read", httpmethode = HTTPmethode.GET)
    public ModelAndView readUpload(String id) {
        UploadedFile file = UploadStore.find(id);
        ModelAndView modelAndView = new ModelAndView("upload-read");
        if (file == null) {
            modelAndView.addObject("nom", "Fichier introuvable");
            modelAndView.addObject("contenu", "L'identifiant demandé n'existe pas.");
            return modelAndView;
        }
        modelAndView.addObject("nom", file.getNomFichier());
        modelAndView.addObject("type", file.getType());
        if (file.getType() != null && file.getType().startsWith("image/")) {
            String image = "data:" + file.getType() + ";base64,"
                    + java.util.Base64.getEncoder().encodeToString(file.getContenu());
            modelAndView.addObject("image", image);
        } else {
            modelAndView.addObject("contenu",
                    new String(file.getContenu(), java.nio.charset.StandardCharsets.UTF_8));
        }
        return modelAndView;
    }

    @UrlAnnotation(value = "/form-avance", httpmethode = HTTPmethode.GET)
    public ModelAndView formAvance() {
        return new ModelAndView("formulaire-avance");
    }

    @UrlAnnotation(value = "/form-avance", httpmethode = HTTPmethode.POST)
    public ModelAndView submitFormAvance(@ObjetAnnotation PersonneForm personne1,
            @ObjetAnnotation PersonneForm personne2, String commentaire) {
        ModelAndView modelAndView = new ModelAndView("resultat");
        String message = "Personne 1 : " + personne1.nom + ", lot : " + personne1.adresse.lot
                + " | Personne 2 : " + personne2.nom + ", lot : " + personne2.adresse.lot
                + " | " + commentaire;
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @UrlAnnotation(value = "/form-recursif", httpmethode = HTTPmethode.GET)
    public ModelAndView formRecursif() {
        return new ModelAndView("formulaire-recursif");
    }

    @UrlAnnotation(value = "/form-recursif", httpmethode = HTTPmethode.POST)
    public ModelAndView submitFormRecursif(@ObjetAnnotation PersonneForm personne) {
        ModelAndView modelAndView = new ModelAndView("resultat");
        modelAndView.addObject("message", "Personne : " + personne.nom
                + ", adresse : lot " + personne.adresse.lot
                + ", parcelle " + personne.adresse.parcelle);
        return modelAndView;
    }

    @UrlAnnotation(value = "/form-multiple", httpmethode = HTTPmethode.GET)
    public ModelAndView formMultiple() {
        return new ModelAndView("formulaire-multiple");
    }

    @UrlAnnotation(value = "/form-multiple", httpmethode = HTTPmethode.POST)
    public ModelAndView submitFormMultiple(@ObjetAnnotation PersonneForm personne1,
            @ObjetAnnotation PersonneForm personne2) {
        ModelAndView modelAndView = new ModelAndView("resultat");
        modelAndView.addObject("message", "Personne 1 : " + personne1.nom + " (" + personne1.age
                + ") | Personne 2 : " + personne2.nom + " (" + personne2.age + ")");
        return modelAndView;
    }

    @UrlAnnotation(value = "/form-mixte", httpmethode = HTTPmethode.GET)
    public ModelAndView formMixte() {
        return new ModelAndView("formulaire-mixte");
    }

    @UrlAnnotation(value = "/form-mixte", httpmethode = HTTPmethode.POST)
    public ModelAndView submitFormMixte(@ObjetAnnotation PersonneForm personne,
            String commentaire, int score) {
        ModelAndView modelAndView = new ModelAndView("resultat");
        modelAndView.addObject("message", personne.nom + " | age : " + personne.age
                + " | commentaire : " + commentaire
                + " | score : " + score);
        return modelAndView;
    }

    private String buildFileLinks() {
        StringBuilder links = new StringBuilder();
        for (UploadedFile file : UploadStore.list()) {
            links.append("<li><a href=\"/exo/upload/read?id=")
                    .append(file.getId())
                    .append("\">")
                    .append(file.getNomFichier())
                    .append(" (")
                    .append(file.getType())
                    .append(")</a>")
                    .append("<form method=\"post\" action=\"/exo/upload/delete\" style=\"display:inline\">")
                    .append("<input type=\"hidden\" name=\"id\" value=\"")
                    .append(file.getId())
                    .append("\"><button type=\"submit\">Supprimer</button></form></li>");
        }
        return links.length() == 0 ? "<li>Aucun fichier uploadé.</li>" : links.toString();
    }

    public static class UploadForm {
        private MapFile mapFile;

        public UploadForm() {
        }
    }

    public static class PersonneForm {
        private String nom;
        private int age;
        private AdresseForm adresse;

        public PersonneForm() {
        }
    }

    public static class AdresseForm {
        private String lot;
        private String parcelle;

        public AdresseForm() {
        }
    }

    public static class ProfilForm {
        private String nom;
        private int age;

        public ProfilForm() {
        }
    }

    public static class DemoUser {
        private final int id;
        private final String nom;
        private final String email;

        public DemoUser(int id, String nom, String email) {
            this.id = id;
            this.nom = nom;
            this.email = email;
        }
    }
}
