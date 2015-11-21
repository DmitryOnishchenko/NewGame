package com.electdead.newgame.engine;

import static com.electdead.newgame.engine.EngineV2.getTime;

public class RenderStats {
    /* Info */
    private String fpsExpected = Integer.toString(EngineV2.MAX_FPS);
    private int fpsActual;
    private String report = "FPS: 0/0 | 0 ms";

    private long startTime;
    private long start;
    private long end;

    public void start() {
        start = getTime();
    }

    public void end() {
        end = EngineV2.getTime();

        if (getTime() - startTime >= 1000) {
            buildReport();
            fpsActual = 0;
            startTime += 1000;
        }
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void updateFps() {
        fpsActual++;
    }

    public String report() {
        return report;
    }

    private void buildReport() {
        StringBuilder builder = new StringBuilder("FPS:");

        builder.append(fpsActual).append('/').append(fpsExpected);
        builder.append(" | ");
        builder.append(end - start).append(" ms");
        report = builder.toString();
    }
}
