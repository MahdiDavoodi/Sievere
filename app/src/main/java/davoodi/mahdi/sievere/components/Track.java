package davoodi.mahdi.sievere.components;

import android.content.Context;
import android.net.Uri;

import davoodi.mahdi.sievere.R;

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


    Context context;
    private long id, dateAdded, length;

    private int albumId,
            artistId,
            trackNumber,
            bitrate,
            year,
            playedCount;

    private String fileName,
            title,
            albumName,
            artistName,
            genre;

    private Uri uri;

    public Track(Context context,
                 long id,
                 Uri uri,
                 String fileName,
                 String title,
                 String artistName,
                 String albumName,
                 long length,
                 int bitrate,
                 int year,
                 String genre
            , long dateAdded) {
        this.context = context;
        this.id = id;
        this.bitrate = bitrate;
        this.year = year;
        this.fileName = fileName;
        this.title = title;
        this.albumName = albumName;
        this.artistName = artistName;
        this.length = length;
        this.genre = genre;
        this.uri = uri;
        this.dateAdded = dateAdded;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public void setLength(long length) {
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

    public long getLength() {
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
        if (title == null)
            return fileName;
        else return title;
    }

    public String getAlbumName() {
        if (albumName == null)
            return context.getResources().getString(R.string.single);
        else return albumName;
    }

    public String getArtistName() {
        if (artistName == null)
            return context.getResources().getString(R.string.unknown);
        else return artistName;
    }

    public String getGenre() {
        if (genre == null)
            return context.getResources().getString(R.string.blank);
        else return genre;
    }
}