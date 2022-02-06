package davoodi.mahdi.sievere.components;

public class Album {
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artistId";
    public static final String KEY_ARTIST_NAME = "artistName";
    public static final String KEY_TRACKS = "tracks";
    public static final String KEY_YEAR = "year";


    private final int id,
            tracks,
            artistId,
            year;
    private final String title,
            artistName;

    public Album(int id,
                 String title,
                 int artistId,
                 String artistName,
                 int tracks,
                 int year) {
        this.id = id;
        this.tracks = tracks;
        this.artistId = artistId;
        this.year = year;
        this.title = title;
        this.artistName = artistName;
    }

    public int getId() {
        return id;
    }

    public int getTracks() {
        return tracks;
    }

    public int getArtistId() {
        return artistId;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }
}
