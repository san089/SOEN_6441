package com.concordia.riskgame.view;

import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.utilities.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MapEditor extends JFrame {

    public Map gameMap;
    private ToolBar toolBar;
    private MapEditorController mapEditorController;



    public MapEditor(Map gameMap) {
        super("Game Window");
        this.gameMap = gameMap;
        setSize(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT);
        setMinimumSize(new Dimension(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        mapEditorController = new MapEditorController(this);
        addComponents(mapEditorController);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameLauncherView mainMenuScreen = new GameLauncherView();
                mainMenuScreen.setVisible(true);
                dispose();
            }
        });
    }
    public void addComponents(MapEditorController mapEditorController) {
        setLayout(null);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        Dimension frameSize = this.getSize();
        toolBar = new ToolBar(mapEditorController);
        toolBar.setBounds(0, 0, frameSize.width, 40);
        add(toolBar);

    }

}
