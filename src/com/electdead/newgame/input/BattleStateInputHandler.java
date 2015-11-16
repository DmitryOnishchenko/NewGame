package com.electdead.newgame.input;

import com.electdead.newgame.gamestate.BattleState;
import com.electdead.newgame.gamestate.BattleStateSettings;

import java.awt.event.KeyEvent;
import java.util.Random;

public class BattleStateInputHandler {
    private BattleState battleState;

    private Random random = new Random();

    public BattleStateInputHandler(BattleState battleState) {
        this.battleState = battleState;
    }

    public void processInput(KeyEvent event) {
        if (event != null && event.getID() == KeyEvent.KEY_PRESSED) {
            // Spawn events
            if (event.getKeyChar() == 'a') {
                battleState.createDemoUnit(
                        "Human Soldier", BattleStateSettings.leftSpawnPoint, battleState.getRandomPointY());
            } else if (event.getKeyChar() == 's') {
                battleState.createDemoUnit(
                        "Human Archer", BattleStateSettings.leftSpawnPoint, battleState.getRandomPointY());
            } else if (event.getKeyChar() == 'k') {
                battleState.createDemoUnit(
                        "Orc Soldier", BattleStateSettings.rightSpawnPoint, battleState.getRandomPointY());
            } else if (event.getKeyChar() == 'i') {
                battleState.createDemoUnit(
                        "Orc Soldier Elite", BattleStateSettings.rightSpawnPoint, battleState.getRandomPointY());
            } else if (event.getKeyChar() == 'l') {
                battleState.createDemoUnit(
                        "Orc Archer", BattleStateSettings.rightSpawnPoint, battleState.getRandomPointY());
            } else if (event.getKeyChar() == 'h') {
                BattleStateSettings.DEMO_MODE = !BattleStateSettings.DEMO_MODE;
            }
            // Esc
            else if (event.getKeyCode() == 27) {
                System.exit(0);
            }
            // Space
            else if (event.getKeyChar() == ' ') {
                BattleStateSettings.PAUSE = !BattleStateSettings.PAUSE;
            }
            // Enter
            else if (event.getKeyCode() == 10) {
                battleState.startNewGame();
            }
            // Debug mode
            else if (event.getKeyChar() == 'j') {
                BattleStateSettings.DEBUG_MODE = !BattleStateSettings.DEBUG_MODE;
                if (!BattleStateSettings.DEBUG_MODE) {
                    BattleStateSettings.DEBUG_BOX = false;
                    BattleStateSettings.DEBUG_TARGET = false;
                    BattleStateSettings.DEBUG_GRID = false;
                }
            } else if (BattleStateSettings.DEBUG_MODE) {
                if (event.getKeyChar() == '1') {
                    BattleStateSettings.DEBUG_BOX = !BattleStateSettings.DEBUG_BOX;
                } else if (event.getKeyChar() == '2') {
                    BattleStateSettings.DEBUG_TARGET = !BattleStateSettings.DEBUG_TARGET;
                } else if (event.getKeyChar() == '3') {
                    BattleStateSettings.DEBUG_GRID = !BattleStateSettings.DEBUG_GRID;
                }
            }
        }
    }
}
