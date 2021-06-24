package davoodi.mahdi.sievere.components;

import android.net.Uri;

public class Track {

    public static final String KEY_ID = "id";
    public static final String KEY_URI = "uri";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ALBUM_ID = "albumId";
    public static final String KEY_ALBUM = "albumName";
    public static final String KEY_ARTIST_ID = "artistId";
    public static final String KEY_ARTIST = "artistName";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_NUMBER = "trackNumber";
    public static final String KEY_BITRATE = "bitrate";
    public static final String KEY_YEAR = "year";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_PLAYED = "playedCount";


    private long id, dateAdded;

    private int albumId,
            artistId,
            trackNumber,
            bitrate,
            year,
            playedCount;

    private String title,
            albumName,
            artistName,
            length,
            genre;

    private Uri uri;

    public Track(long id,
                 Uri uri,
                 String title,
                 String artistName,
                 String albumName,
                 String length,
                 int bitrate,
                 int year,
                 String genre
            , long dateAdded) {
        this.id = id;
        this.bitrate = bitrate;
        this.year = year;
        this.title = title;
        this.albumName = albumName;
        this.artistName = artistName;
        this.length = length;
        this.genre = genre;
        this.uri = uri;
        this.dateAdded = dateAdded;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPlayedCount(int playedCount) {
        this.playedCount = playedCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public Uri getUri() {
        return uri;
    }

    public long getId() {
        return id;
    }

    public String getLength() {
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
