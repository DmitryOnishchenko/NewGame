package com.electdead.newgame.engine;

import static com.electdead.newgame.engine.EngineV2.getTime;

public class InputHandlerThread extends Thread {
    /* Engine */
    private EngineV2 engine;

    public InputHandlerThread(EngineV2 engine) {
        this.engine = engine;
    }

    @Override
    public void run() {
        while (true) {
            long current = getTime();

            engine.processInput();

            long sleepFor = current + EngineV2.MS_PER_UPDATE / 2 - getTime();
            try {
                if (sleepFor > 0) Thread.sleep(sleepFor);
            } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
    }
}

