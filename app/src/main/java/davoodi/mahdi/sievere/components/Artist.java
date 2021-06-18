package davoodi.mahdi.sievere.components;

public class Artist {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TRACKS = "tracks";
    public static final String KEY_ALBUMS = "albums";

    private final int id,
            tracks, albums;
    private final String name;

    public Artist(int id, String name, int tracks, int albums) {
        this.id = id;
        this.tracks = tracks;
        this.albums = albums;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getName() {
        return name;
    }
}
