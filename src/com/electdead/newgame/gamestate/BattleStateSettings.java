package com.electdead.newgame.gamestate;

import com.electdead.newgame.main.MainApp;

public class BattleStateSettings {
    public static volatile boolean PAUSE;
    public static volatile boolean DEMO_MODE;
    public static volatile boolean DEBUG_MODE;
    public static volatile boolean DEBUG_BOX;
    public static volatile boolean DEBUG_TARGET;
    public static volatile boolean DEBUG_GRID;

//    public static int leftSpawnPoint = GridOld.INDENT_LEFT;
    public static int leftSpawnPoint = 330;
    public static int rightSpawnPoint = MainApp.WIDTH + 50;

    /* Test */
    public static int testSpawnTimer;
    public static int testSpawnTimer2;
}
