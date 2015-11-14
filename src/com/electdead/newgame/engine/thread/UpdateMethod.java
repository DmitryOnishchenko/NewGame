package com.electdead.newgame.engine.thread;

public abstract class UpdateMethod {
    protected Updater updater;

    public abstract void update(int lastProcessed, int toProcess);
}
