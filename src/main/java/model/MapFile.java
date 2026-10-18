package model;

public class MapFile {
    private String nomFichier;
    private String type;
    private byte[] rectoCIN;
    private byte[] verseauCIN;

    public String getNomFichier() {
        return nomFichier;
    }

    public String getType() {
        return type;
    }

    public byte[] getRectoCIN() {
        return rectoCIN;
    }

    public byte[] getVerseauCIN() {
        return verseauCIN;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRectoCIN(byte[] rectoCIN) {
        this.rectoCIN = rectoCIN;
    }

    public void setVerseauCIN(byte[] verseauCIN) {
        this.verseauCIN = verseauCIN;
    }
}