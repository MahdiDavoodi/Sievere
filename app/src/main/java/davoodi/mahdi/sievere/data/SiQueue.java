package davoodi.mahdi.sievere.data;

import java.util.ArrayList;

import davoodi.mahdi.sievere.components.Track;

public class SiQueue {
    public static int POSITION;
    public static ArrayList<Track> queue;

    public static Track getTrackToPlay() {
        if (queue != null)
            return queue.get(POSITION);
        else return null;
    }

    public static boolean isQueueReady() {
        return SiQueue.queue != null && SiQueue.POSITION > -1;
    }
}
