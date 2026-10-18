package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import model.MapFile;
import model.UploadedFile;

public final class UploadStore {
    private static final Map<String, UploadedFile> FILES = new ConcurrentHashMap<>();

    private UploadStore() {
    }

    public static void store(MapFile mapFile) {
        if (mapFile.getRectoCIN() != null) {
            addFile("recto-" + mapFile.getNomFichier(), mapFile.getType(), mapFile.getRectoCIN());
        }
        if (mapFile.getVerseauCIN() != null) {
            addFile("verseau-" + mapFile.getNomFichier(), mapFile.getType(), mapFile.getVerseauCIN());
        }
    }

    public static List<UploadedFile> list() {
        List<UploadedFile> files = new ArrayList<>(FILES.values());
        Collections.reverse(files);
        return files;
    }

    public static UploadedFile find(String id) {
        return FILES.get(id);
    }

    public static boolean delete(String id) {
        return FILES.remove(id) != null;
    }

    private static void addFile(String name, String type, byte[] content) {
        String id = UUID.randomUUID().toString();
        FILES.put(id, new UploadedFile(id, name, type, content));
    }
}