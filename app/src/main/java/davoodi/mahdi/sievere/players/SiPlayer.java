package davoodi.mahdi.sievere.players;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.data.SiQueue;

public class SiPlayer extends MediaPlayer {
    public static SiPlayer instance;
    public static Track playing = null;

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
                playing = track;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playAgain(Context context) throws Exception {
        playTrack(context, SiQueue.getTrackToPlay());
    }

    public int[] convertTime(long value) {
        int duration = (int) value;
        int hours = (duration / 3600000);
        int minutes = (duration / 60000) % 60000;
        int seconds = duration % 60000 / 1000;

        return new int[]{hours, minutes, seconds};
    }
}