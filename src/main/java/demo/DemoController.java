package demo;

import annotation.Annotation;
import annotation.JsonAnnotation;
import annotation.ObjetAnnotation;
import annotation.UrlAnnotation;
import model.HTTPmethode;
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
    //@JsonAnnotation
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