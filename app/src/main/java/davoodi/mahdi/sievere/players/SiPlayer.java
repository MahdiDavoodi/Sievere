package davoodi.mahdi.sievere.players;

import android.media.MediaPlayer;

public class SiPlayer extends MediaPlayer {
    public SiPlayer() {
        super();
    }

    public void pause() {
        if (isPlaying())
            super.pause();
    }

    public void play() {
        if (isPlaying())
            super.start();
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