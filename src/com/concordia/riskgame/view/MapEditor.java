package com.concordia.riskgame.view;

import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MapEditor extends JFrame {

    public Map gameMap;

    public MapEditor(Map gameMap) {
        super("Game Window");
        this.gameMap = gameMap;
        setSize(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT);
        setMinimumSize(new Dimension(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        /*mapEditorController = new MapEditorController(this);
        addComponents(mapEditorController);*/
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameLauncherView mainMenuScreen = new GameLauncherView();
                mainMenuScreen.setVisible(true);
                dispose();
            }
        });
    }
}
