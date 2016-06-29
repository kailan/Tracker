package pw.kmp.tracker.trackers;

import java.util.LinkedHashSet;
import java.util.Optional;

public class TrackerManager {

    private final LinkedHashSet<Tracker> trackers;

    public TrackerManager() {
        trackers = new LinkedHashSet<>();
    }

    public LinkedHashSet<Tracker> getTrackers() {
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
        if(getTracker(t.getClass()) != null) {
            throw new RuntimeException("Cannot register duplicate tracker " + t.getClass());
        } else {
            trackers.add(t);
            t.enable();
        }
    }

    public void unregisterTracker(Tracker t) {
        trackers.remove(t);
        t.disable();
    }

}
