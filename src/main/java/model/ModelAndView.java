package model;

import java.util.HashMap;

public class ModelAndView {
    private String urlSuivant;
    private HashMap<String,String> list ;

    public ModelAndView() {
    }

    public ModelAndView(String urlSuivant) {
        this.urlSuivant = urlSuivant;
    }

    public String getUrlSuivant() {
        return urlSuivant;
    }
    public void setUrlSuivant(String urlSuivant) {
        this.urlSuivant = urlSuivant;
    }
    public HashMap<String, String> getList() {
        return list;
    }
    public void setList(HashMap<String, String> list) {
        this.list = list;
    }

    public void addObject(String key, String value) {
        if (list == null) {
            list = new HashMap<>();
        }
        list.put(key, value);
    }
}

