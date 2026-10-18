package model;

public class UploadedFile {
    private final String id;
    private final String nomFichier;
    private final String type;
    private final byte[] contenu;

    public UploadedFile(String id, String nomFichier, String type, byte[] contenu) {
        this.id = id;
        this.nomFichier = nomFichier;
        this.type = type;
        this.contenu = contenu;
    }

    public String getId() {
        return id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public String getType() {
        return type;
    }

    public byte[] getContenu() {
        return contenu;
    }
}