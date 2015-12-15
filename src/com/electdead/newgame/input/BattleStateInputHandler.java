package com.electdead.newgame.input;

import com.electdead.newgame.gamestate.battle.BattleState;
import com.electdead.newgame.gamestate.battle.BattleStateSettings;

import java.awt.event.KeyEvent;

public class BattleStateInputHandler {
    private BattleState battleState;

    public BattleStateInputHandler(BattleState battleState) {
        this.battleState = battleState;
    }

    public void processInput(KeyEvent event) {
        if (event != null && event.getID() == KeyEvent.KEY_PRESSED) {

            char key = event.getKeyChar();
            // Spawn events
            if (key == 'a') {
                battleState.createDemoUnit(
                        "Human Soldier", BattleStateSettings.leftSpawnPoint, battleState.getRandomPointY());
            } else if (key == 's') {
                battleState.createDemoUnit(
                        "Human Archer", BattleStateSettings.leftSpawnPoint, battleState.getRandomPointY());
            } else if (key == 'k') {
                battleState.createDemoUnit(
                        "Orc Soldier", BattleStateSettings.rightSpawnPoint, battleState.getRandomPointY());
            } else if (key == 'i') {
                battleState.createDemoUnit(
                        "Orc Soldier Elite", BattleStateSettings.rightSpawnPoint, battleState.getRandomPointY());
            } else if (key == 'l') {
                battleState.createDemoUnit(
                        "Orc Archer", BattleStateSettings.rightSpawnPoint, battleState.getRandomPointY());
            } else if (key == 'h') {
                BattleStateSettings.DEMO_MODE = !BattleStateSettings.DEMO_MODE;
            }
            // Esc
            else if (event.getKeyCode() == 27) {
                System.exit(0);
            }
            // Space
            else if (key == ' ') {
                BattleStateSettings.PAUSE = !BattleStateSettings.PAUSE;
            }
            // Enter
            else if (event.getKeyCode() == 10) {
                battleState.startNewGame();
            }
            // Debug mode
            else if (key == 'j') {
                BattleStateSettings.DEBUG_MODE = !BattleStateSettings.DEBUG_MODE;
                if (!BattleStateSettings.DEBUG_MODE) {
                    BattleStateSettings.DEBUG_BOX = false;
                    BattleStateSettings.DEBUG_TARGET = false;
                    BattleStateSettings.DEBUG_GRID = false;
                }
            } else if (BattleStateSettings.DEBUG_MODE) {
                if (key == '1') {
                    BattleStateSettings.DEBUG_BOX = !BattleStateSettings.DEBUG_BOX;
                } else if (key == '2') {
                    BattleStateSettings.DEBUG_TARGET = !BattleStateSettings.DEBUG_TARGET;
                } else if (key == '3') {
                    BattleStateSettings.DEBUG_GRID = !BattleStateSettings.DEBUG_GRID;
                }
            }
        }
    }
}
