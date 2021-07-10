package davoodi.mahdi.sievere.players;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

import davoodi.mahdi.sievere.components.Track;

public class SiPlayer extends MediaPlayer {
    Context context;

    public SiPlayer(Context context) {
        super();
        this.context = context;
    }

    public void playTrack(Track track) {
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

    public String convertTime(long value) {
        String audioTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            audioTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            audioTime = String.format("%02d:%02d", mns, scs);
        }
        return audioTime;
    }
}