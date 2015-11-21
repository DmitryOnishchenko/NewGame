package com.electdead.newgame.engine;

import static com.electdead.newgame.engine.EngineV2.getTime;

public class UpdateThread extends Thread {
    /* Engine */
    private EngineV2 engine;

    /* Update info */
    private UpdateStats updateStats;

    public UpdateThread(EngineV2 engine, UpdateStats updateStats) {
        this.engine = engine;
        this.updateStats = updateStats;
    }

    @Override
    public void run() {
        long timer = getTime();
        long previous = getTime();
        long lag = 0;

        updateStats.setStartTime(timer);

        while (true) {
            long current = getTime();
            long elapsed = current - previous;
            previous = current;
            lag += elapsed;


            engine.processInput();

            while (lag >= EngineV2.MS_PER_UPDATE) {
                updateStats.start();
                engine.update();
                lag -= EngineV2.MS_PER_UPDATE;

                updateStats.updateTps();
            }
            updateStats.end();


            long sleepFor = current + EngineV2.MS_PER_UPDATE - getTime();
            try {
                if (sleepFor > 0) Thread.sleep(sleepFor);
            } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
    }
}
