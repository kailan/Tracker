package pw.kmp.tracker.trackers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TrackerManager {

    private final Set<Tracker> trackers;

    public TrackerManager() {
        trackers = new HashSet<>();
    }

    public Set<Tracker> getTrackers() {
        return trackers;
    }

    public <T extends Tracker> T getTracker(Class<T> type) {
        Optional<Tracker> tracker = trackers.stream().filter(t -> type.isAssignableFrom(t.getClass())).findAny();
        if (tracker.isPresent()) {
            //noinspection unchecked
            return (T) tracker.get();
        }
        return null;
    }

    public void registerTracker(Tracker t) {
        trackers.add(t);
        t.enable();
    }

    public void unregisterTracker(Tracker t) {
        trackers.remove(t);
        t.disable();
    }

}
