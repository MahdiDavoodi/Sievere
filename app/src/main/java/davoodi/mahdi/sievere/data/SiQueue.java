package davoodi.mahdi.sievere.data;

import java.util.ArrayList;
import java.util.Collections;

import davoodi.mahdi.sievere.components.Track;

public class SiQueue {
    public static int position = 0;
    public static ArrayList<Track> queue, initialQueue;
    public static boolean isOnRepeat = false, isOnRepeatOne = false, isOnShuffle = false;

    public static void setQueue(ArrayList<Track> input) {
        queue = new ArrayList<>(input);
        initialQueue = new ArrayList<>(input);
    }

    public static void shuffle() {
        if (isQueueReady())
            Collections.shuffle(queue);
        isOnShuffle = true;
    }

    public static void unShuffle() {
        if (isQueueReady() && isOnShuffle)
            queue = new ArrayList<>(initialQueue);
    }

    public static Track getTrackToPlay() {
        if (isQueueReady())
            return queue.get(position);
        else return null;
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
