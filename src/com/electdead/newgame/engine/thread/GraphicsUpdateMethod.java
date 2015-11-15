package com.electdead.newgame.engine.thread;

import com.electdead.newgame.engine.EngineV2;
import com.electdead.newgame.gameobjectV2.GameObject;

import java.util.List;

public class GraphicsUpdateMethod extends UpdateMethod {
    public void update(int lastProcessed, int toProcess) {
//        List<GameObjectOld> list = DevGameState.grid.getAllObjects();
        List<GameObject> list = EngineV2.gameObjects;

        for (; lastProcessed < toProcess; ) {
            list.get(lastProcessed++).updateGraphics();

            updater.report(lastProcessed);
        }
    }
}
