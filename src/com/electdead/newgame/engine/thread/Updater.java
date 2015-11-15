package com.electdead.newgame.engine.thread;

import com.electdead.newgame.engine.EngineV2;

import javax.swing.*;

public class Updater extends Thread {
    private String name;
    private boolean prime;
    private volatile boolean done;

    private volatile int toProcess;
    private volatile int lastProcessed;
    private volatile int totalWork;

    private Updater nextUpdater;
    private UpdateMethod updateMethod;

    public Updater(String name, UpdateMethod updateMethod, boolean prime) {
        this.name = name;
        this.updateMethod = updateMethod;
        updateMethod.updater = this;
        this.prime = prime;
    }

    public Updater getNextUpdater() {
        return nextUpdater;
    }

    public void setNextUpdater(Updater nextUpdater) {
        this.nextUpdater = nextUpdater;
    }

    public void startProcess(int totalWork) {
        if (prime) {
            this.toProcess = totalWork;
        }
        this.totalWork = totalWork;
        this.done = false;
        if (nextUpdater != null) {
            nextUpdater.startProcess(totalWork);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int sleepFor = 1;

                if (prime) {
                    while (done || toProcess == 0) {
                        yield();
//                        System.out.println(name + ":: sleep for");
                        sleep(sleepFor);
                    }
                } else {
                    while (toProcess == 0) {
                        yield();
//                        System.out.println(name + ":: sleep for");
                        sleep(sleepFor);
                    }
                }

                if (prime) {
//                    System.out.println("\n\t================================");
                }

                done = false;
                updateMethod.update(lastProcessed, toProcess);

                if (lastProcessed == totalWork) {
//                    System.out.println(name + ":: end. \t\tProcessed: " + totalWork);
                    done = true;
                    toProcess = 0;
                    lastProcessed = 0;
                }

                sleep(sleepFor);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public void report(int lastProcessed) {
        this.lastProcessed = lastProcessed;
        if (nextUpdater != null) {
            nextUpdater.toProcess = lastProcessed;
        } else {
            EngineV2.loopEnded();
        }
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
