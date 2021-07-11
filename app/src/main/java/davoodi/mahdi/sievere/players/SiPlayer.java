package davoodi.mahdi.sievere.players;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

import davoodi.mahdi.sievere.components.Track;

public class SiPlayer extends MediaPlayer {
    public static SiPlayer instance;

    public SiPlayer() {
        super();
        instance = this;
    }

    public static SiPlayer getInstance() {
        return instance;
    }

    public void playTrack(Context context, Track track) {
        try {
            if (track != null) {
                reset();
                setDataSource(context, track.getUri());
                prepare();
                start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] convertTime(long value) {
        int duration = (int) value;
        int hours = (duration / 3600000);
        int minutes = (duration / 60000) % 60000;
        int seconds = duration % 60000 / 1000;

        return new int[]{hours, minutes, seconds};
    }
}