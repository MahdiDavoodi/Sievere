package davoodi.mahdi.sievere.data;

import java.util.ArrayList;
import java.util.Collections;

import davoodi.mahdi.sievere.components.Track;

public class SiQueue {
    public static int position = 0;
    public static ArrayList<Track> queue;
    public static boolean isOnRepeat = false;
    public static boolean isOnRepeatOne = false;

    public static void setQueue(ArrayList<Track> input) {
        queue = new ArrayList<>(input);
    }

    public static void shuffle() {
        if (queue != null)
            Collections.shuffle(queue);
        position = 0;
    }

    public static Track getTrackToPlay() {
        if (queue != null)
            return queue.get(position);
        else return null;
    }

    public static boolean isQueueReady() {
        return SiQueue.queue != null && SiQueue.position > -1;
    }

    public static void updatePosition(int input) {
        if (queue != null)
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
}
