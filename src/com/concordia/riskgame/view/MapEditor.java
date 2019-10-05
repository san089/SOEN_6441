package com.concordia.riskgame.view;

import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.utilities.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MapEditor extends JFrame {

    private JLabel countriesLabel;
    private JLabel continentLabel;
    private ToolBar toolBar;
    public Map gameMap;
    private MapEditorController mapEditorController;
    private JTree mapTree;
    private JScrollPane treeScrollPane;
    private JPanel contientLabelViewPanel;
    private JPanel countryLabelViewPanel;
    private List<String> count = new ArrayList<String>();
    private String data[][];
    private String countryColumn[];
    private JTable tableMatrix;
    private JScrollPane scrollPane;





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
        Border blackline = BorderFactory.createLineBorder(Color.black);
        Dimension frameSize = this.getSize();
        toolBar = new ToolBar(mapEditorController);
        toolBar.setBounds(0, 0, frameSize.width, 40);
        contientLabelViewPanel = new JPanel();
        continentLabel = new JLabel("Continent Tree");
        Dimension continentSize = continentLabel.getPreferredSize();
        continentLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        continentLabel.setBounds(140, 50, continentSize.width + 500, continentSize.height);
        contientLabelViewPanel.setBounds(10, 35, frameSize.width - 950, 35);
        add(contientLabelViewPanel);
        contientLabelViewPanel.setBackground(Color.lightGray);
        contientLabelViewPanel.setLayout(new FlowLayout());
        continentLabel.setFont(new Font("dialog", 1, 15));
        contientLabelViewPanel.setBorder(blackline);
        contientLabelViewPanel.add(continentLabel);

        treeScrollPane = new JScrollPane(mapTree);
        treeScrollPane.setBounds(10, 70, frameSize.width - 950, frameSize.height - 600);
        countryLabelViewPanel = new JPanel();
        countriesLabel = new JLabel("Countries Matrix");
        Dimension countriesSize = countriesLabel.getPreferredSize();
        countriesLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
        countriesLabel.setBounds(240, 50, countriesSize.width + 500, countriesSize.height);
        countryLabelViewPanel.setBounds(contientLabelViewPanel.getBounds().x + (int) (contientLabelViewPanel.getBounds().getWidth()), 35,frameSize.width - 300, 35);
        add(countryLabelViewPanel);
        countryLabelViewPanel.setBackground(Color.lightGray);
        countryLabelViewPanel.setLayout(new FlowLayout());
        countriesLabel.setFont(new Font("dialog", 1, 15));
        countryLabelViewPanel.setBorder(blackline);
        countryLabelViewPanel.add(countriesLabel);
        scrollPane = new JScrollPane(tableMatrix);
        scrollPane.setBounds(treeScrollPane.getBounds().x + (int) (treeScrollPane.getBounds().getWidth()), 70,frameSize.width - 300, frameSize.height - 600);

        add(scrollPane);
        add(treeScrollPane);
        add(toolBar);


    }

}
