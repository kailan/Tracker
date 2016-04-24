package pw.kmp.tracker.trackers;

import pw.kmp.tracker.damage.DamageCause;

public class UnknownCause extends DamageCause {

    @Override
    public String getName() {
        return "???";
    }

}
