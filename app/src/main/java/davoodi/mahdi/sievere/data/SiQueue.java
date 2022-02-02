package davoodi.mahdi.sievere.data;

import java.util.ArrayList;
import java.util.Collections;

import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.players.SiPlayer;

public class SiQueue {
    public static int position = 0;
    public static int[] defaultSamples = null;
    public static ArrayList<Track> queue, initialQueue;
    public static boolean isOnRepeat = false, isOnRepeatOne = false, isOnShuffle = false;

    public static void setQueue(ArrayList<Track> input) {
        queue = new ArrayList<>(input);
        initialQueue = new ArrayList<>(input);
    }

    public static void shuffle() throws Exception {
        if (isQueueReady())
            Collections.shuffle(queue);
        isOnShuffle = true;
        if (SiPlayer.playing != null)
            position = findTrackPosition(SiPlayer.playing);
        else position = findTrackPosition(SiQueue.getTrackToPlay());
    }

    public static void unShuffle() throws Exception {
        if (isQueueReady() && isOnShuffle)
            queue = new ArrayList<>(initialQueue);
        isOnShuffle = false;
        if (SiPlayer.playing != null)
            position = findTrackPosition(SiPlayer.playing);
        else position = findTrackPosition(SiQueue.getTrackToPlay());
    }

    public static Track getTrackToPlay() throws Exception {
        if (isQueueReady())
            return queue.get(position);
        else
            throw new Exception("ERROR: Queue is not ready, but someone asked for a Track! - SiQueue.java");
    }

    public static boolean isQueueReady() {
        return queue != null && initialQueue != null;
    }

    public static void updatePosition(int input) {
        if (isQueueReady())
            if (input == 1) {
                if (position == queue.size() - 1)
                    position = 0;
                else
                    position = position + input;
            } else if (input == -1) {
                if (position == 0)
                    position = queue.size() - 1;
                else
                    position = position + input;
            }
    }

    public static int findTrackPosition(Track track) {
        return queue.indexOf(track);
    }
}
