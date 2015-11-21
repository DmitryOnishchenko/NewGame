package com.electdead.newgame.engine;

import static com.electdead.newgame.engine.EngineV2.getTime;

public class RenderThread extends Thread {
    /* Engine */
    private EngineV2 engine;

    /* Render info */
    private RenderStats renderStats;

    public RenderThread(EngineV2 engine, RenderStats renderStats) {
        this.engine = engine;
        this.renderStats = renderStats;
    }

    @Override
    public void run() {
        long timer = getTime();
        long previous = getTime();
        long lag = 0;

        renderStats.setStartTime(timer);

        while (true) {
            long current = getTime();
            long elapsed = current - previous;
            previous = current;
            lag += elapsed;

            renderStats.start();

            double deltaTime = 1 - lag / EngineV2.MS_PER_UPDATE;
            engine.render(deltaTime);
            renderStats.updateFps();

            renderStats.end();

            if (EngineV2.USE_FPS_LIMIT) {
                long sleepFor = current + EngineV2.MS_PER_FRAME - getTime();
                try {
                    if (sleepFor > 0) Thread.sleep(sleepFor);
                } catch (InterruptedException ex) { ex.printStackTrace(); }
            }
        }
    }
}
