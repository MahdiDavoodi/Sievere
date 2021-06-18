package davoodi.mahdi.sievere.components;

public class Track {

    public static final String KEY_ID = "id";
    public static final String KEY_PATH = "path";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ALBUM_ID = "albumId";
    public static final String KEY_ALBUM = "album";
    public static final String KEY_ARTIST_ID = "artistId";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_NUMBER = "trackNumber";
    public static final String KEY_BITRATE = "bitrate";
    public static final String KEY_YEAR = "year";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_PLAYED = "playedCount";


    private final int albumId,
            artistId,
            id,
            length,
            trackNumber,
            bitrate,
            year,
            playedCount;

    private final String path,
            title,
            albumName,
            artistName,
            genre;

    public Track(int id,
                 String path,
                 String title,
                 int albumId,
                 String albumName,
                 int artistId,
                 String artistName,
                 int length,
                 int trackNumber,
                 int bitrate,
                 int year,
                 String genre,
                 int playedCount) {
        this.albumId = albumId;
        this.artistId = artistId;
        this.id = id;
        this.length = length;
        this.trackNumber = trackNumber;
        this.bitrate = bitrate;
        this.year = year;
        this.playedCount = playedCount;
        this.path = path;
        this.title = title;
        this.albumName = albumName;
        this.artistName = artistName;
        this.genre = genre;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public int getBitrate() {
        return bitrate;
    }

    public int getYear() {
        return year;
    }

    public int getPlayedCount() {
        return playedCount;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getGenre() {
        return genre;
    }
}
