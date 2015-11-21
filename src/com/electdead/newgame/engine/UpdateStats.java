package com.electdead.newgame.engine;

import static com.electdead.newgame.engine.EngineV2.*;

public class UpdateStats {
    /* Info */
    private String tpsExpected = Integer.toString(EngineV2.UPDATES_PER_SEC);
    private int tpsActual;
    private String report = "TPS: 0/0 | 0 ms";

    private long startTime;
    private long start;
    private long end;

    public void start() {
        start = getTime();
    }

    public void end() {
        end = getTime();

        if (getTime() - startTime >= 1000) {
            buildReport();
            tpsActual = 0;
            startTime += 1000;
        }
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void updateTps() {
        tpsActual++;
    }

    public String report() {
        return report;
    }

    private void buildReport() {
        StringBuilder builder = new StringBuilder("TPS:");

        builder.append(tpsActual).append('/').append(tpsExpected);
        builder.append(" | ");
        builder.append(end - start).append(" ms");
        report = builder.toString();
    }
}
