package controller;

public class UrlInfo {
    private String url ;
    private Class<?> clazz;
    private HTTPmethode action ;
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Class<?> getClazz() {
        return clazz;
    }
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
    public HTTPmethode getAction() {
        return action;
    }
    public void setAction(HTTPmethode action) {
        this.action = action;
    } 
    
}
