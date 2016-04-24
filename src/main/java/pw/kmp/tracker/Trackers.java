package pw.kmp.tracker;

import pw.kmp.tracker.trackers.Tracker;
import pw.kmp.tracker.trackers.TrackerManager;

public class Trackers {

    private static TrackerManager trackers;

    public static TrackerManager getManager() {
        if (trackers == null) trackers = new TrackerManager();
        return trackers;
    }

    public static <T extends Tracker> T getTracker(Class<T> type) {
        return getManager().getTracker(type);
    }

}
