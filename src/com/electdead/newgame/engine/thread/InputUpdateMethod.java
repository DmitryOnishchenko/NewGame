package com.electdead.newgame.engine.thread;

public class InputUpdateMethod extends UpdateMethod {
    public void update(int lastProcessed, int toProcess) {
//        List<GameObjectOld> list = DevGameState.grid.getAllObjects();
//        List<GameObject> list = EngineV2.gameObjects;

//        for ( ; lastProcessed < toProcess; ) {
//            list.get(lastProcessed++).updateInput();
//            lastProcessed++;
            updater.report(toProcess);
//        }
    }
}
