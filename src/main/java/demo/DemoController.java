package demo;

import annotation.Annotation;
import annotation.JsonAnnotation;
import annotation.ObjetAnnotation;
import annotation.UrlAnnotation;
import model.HTTPmethode;
import model.MapFile;
import model.ModelAndView;

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
        String message = "Fichier : " + fichiers.getNomFichier()
                + " | type : " + fichiers.getType()
                + " | recto : " + size(fichiers.getRectoCIN())
                + " octets | verso : " + size(fichiers.getVerseauCIN()) + " octets";
        ModelAndView modelAndView = new ModelAndView("resultat");
        modelAndView.addObject("message", message);
        return modelAndView;
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