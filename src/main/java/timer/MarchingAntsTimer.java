package timer;

import javax.swing.*;

public class MarchingAntsTimer {
    private static final int DELAY = 100;

    private final Timer timer;

    public MarchingAntsTimer(Runnable runnable) {
        timer = new Timer(DELAY, e -> runnable.run());
        timer.setRepeats(true);
        timer.start();
    }
}
