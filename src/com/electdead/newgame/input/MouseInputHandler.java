package com.electdead.newgame.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInputHandler extends MouseAdapter {
    private static volatile MouseEvent mouseEvent;

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseEvent = e;
    }

    public static MouseEvent getEvent() {
        return mouseEvent;
    }

    public static void clear() {
        mouseEvent = null;
    }
}
