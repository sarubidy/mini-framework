package demo;

import annotation.Annotation;
import annotation.JsonAnnotation;
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