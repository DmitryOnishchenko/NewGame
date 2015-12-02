package com.electdead.newgame.gamestate.battle;

import com.electdead.newgame.engine.GridOld;
import com.electdead.newgame.main.MainApp;

public class BattleStateSettings {
    /* Dirty flags */
    public static volatile boolean NEED_DELETE;
    public static volatile boolean NEED_RELOCATE;

    public static volatile boolean PAUSE;
    public static volatile boolean DEMO_MODE;
    public static volatile boolean DEBUG_MODE;
    public static volatile boolean DEBUG_BOX;
    public static volatile boolean DEBUG_TARGET;
    public static volatile boolean DEBUG_GRID;

    public static int leftSpawnPoint = GridOld.INDENT_LEFT;
//    public static int leftSpawnPoint = 500;
    public static int rightSpawnPoint = MainApp.WIDTH + 50;

    /* Test */
    public static int testSpawnTimer;
    public static int testSpawnTimer2;
}
