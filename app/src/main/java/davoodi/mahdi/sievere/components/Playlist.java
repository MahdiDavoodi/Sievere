package davoodi.mahdi.sievere.components;

public class Playlist {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TRACKS = "tracks";

    private final int id,
            tracks;
    private final String name;

    public Playlist(int id, String name, int tracks) {
        this.id = id;
        this.tracks = tracks;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getTracks() {
        return tracks;
    }

    public String getName() {
        return name;
    }
}
