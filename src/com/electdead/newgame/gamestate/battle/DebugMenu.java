package com.electdead.newgame.gamestate.battle;

import java.awt.*;

public class DebugMenu {
    public void render(Graphics2D g2) {
        g2.drawString("To spawn Human Soldier press \"A\"", 100, 72);
        g2.drawString("To spawn Human Archer press \"S\"", 100, 90);

        g2.drawString("Exit - \"Esc\"", 480, 72);
        if (!BattleStateSettings.DEMO_MODE) {
            g2.drawString("To start Demo Mode press \"H\"", 580, 90);
        } else {
            g2.setPaint(Color.RED);
            g2.drawString("To stop Demo Mode press \"H\"", 580, 90);
        }
        if (!BattleStateSettings.PAUSE) {
            g2.setPaint(Color.WHITE);
            g2.drawString("PAUSE - \"Space\"", 580, 108);
        } else {
            g2.setPaint(Color.RED);
            g2.drawString("UNPAUSE - \"Space\"", 580, 108);
        }
        g2.setPaint(Color.WHITE);
        g2.drawString("New Game - \"Enter\"", 750, 108);

        g2.drawString("To spawn Orc Soldier press \"K\"", 1000, 72);
        g2.drawString("To spawn Orc Soldier Elite press \"I\"", 1000, 90);
        g2.drawString("To spawn Orc Archer press \"L\"", 1000, 108);

        /* Debug menu */
        if (BattleStateSettings.DEBUG_MODE) {
            drawDebugMenu(g2);
            g2.setPaint(Color.RED);
            g2.drawString("To deactivate Debug mode press \"J\" again", 580, 72);
        } else {
            g2.drawString("To activate Debug mode press \"J\" (for debug only!)", 580, 72);
        }
    }

    private void drawDebugMenu(Graphics2D g2) {
        g2.setPaint(Color.RED);
        int rectY = 16;
        int stringY = 30;
        g2.drawRect(290, 6, 610, 44);

        g2.setPaint(Color.WHITE);
        g2.drawString("To activate box model: \"1\"", 300, stringY);
        if (BattleStateSettings.DEBUG_BOX) {
            g2.drawRect(296, rectY, 200, 20);
        }

        g2.setPaint(Color.YELLOW);
        g2.drawString("To activate target tracking: \"2\"", 500, stringY);
        if (BattleStateSettings.DEBUG_TARGET) {
            g2.drawRect(496, rectY, 200, 20);
        }

        g2.setPaint(Color.GREEN);
        g2.drawString("To activate grid: \"3\"", 700, stringY);
        if (BattleStateSettings.DEBUG_GRID) {
            g2.drawRect(696, rectY, 200, 20);
        }
    }
}
