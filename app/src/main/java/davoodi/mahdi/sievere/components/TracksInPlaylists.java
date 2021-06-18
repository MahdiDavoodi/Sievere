package davoodi.mahdi.sievere.components;

public class TracksInPlaylists {
    public static final String KEY_ID = "id";
    public static final String KEY_TRACK = "track";
    public static final String KEY_PLAYLIST = "playlist";

    private final int id,
            trackId,
            playlistId;

    public TracksInPlaylists(int id, int trackId, int playlistId) {
        this.id = id;
        this.trackId = trackId;
        this.playlistId = playlistId;
    }

    public int getId() {
        return id;
    }

    public int getTrackId() {
        return trackId;
    }

    public int getPlaylistId() {
        return playlistId;
    }
}
