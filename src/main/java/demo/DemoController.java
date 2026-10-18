package demo;

import annotation.Annotation;
import annotation.JsonAnnotation;
import annotation.ObjetAnnotation;
import annotation.UrlAnnotation;
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

    private String buildFileLinks() {
        StringBuilder links = new StringBuilder();
        for (UploadedFile file : UploadStore.list()) {
            links.append("<li><a href=\"/exo/upload/read?id=")
                    .append(file.getId())
                    .append("\">")
                    .append(file.getNomFichier())
                    .append(" (" )
                    .append(file.getType())
                .append(")</a>")
                .append("<form method=\"post\" action=\"/exo/upload/delete\" style=\"display:inline\">")
                .append("<input type=\"hidden\" name=\"id\" value=\"")
                .append(file.getId())
                .append("\"><button type=\"submit\">Supprimer</button></form></li>");
        }
        return links.length() == 0 ? "<li>Aucun fichier uploadé.</li>" : links.toString();
    }

    private int size(byte[] value) {
        return value == null ? 0 : value.length;
    }

    public static class UploadForm {
        private MapFile mapFile;

        public UploadForm() {
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