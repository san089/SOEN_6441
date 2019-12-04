package com.concordia.riskgame.view;

import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JFrame commonViewFrame;
    private JLabel tournamentView;
    private JScrollPane resultScrollPane;
    private ArrayList<String> resultList;
    private int gameCount;
    private int mapCount;

    /**
     * Tournament view constructor
     * @param result
     * @param gameCount
     * @param mapCount
     */

    public TournamentView(ArrayList<String> result, int gameCount, int mapCount){
        this.resultList = result;
        this.gameCount = gameCount;
        this.mapCount = mapCount;
        commonViewFrame = new JFrame("Tournament Result");
        commonViewFrame.setSize(Constants.COMMON_VIEW_WIDTH, Constants.COMMON_VIEW_HEIGHT);
        commonViewFrame.setMinimumSize(new Dimension(Constants.COMMON_VIEW_WIDTH, Constants.COMMON_VIEW_HEIGHT));
        commonViewFrame.setResizable(false);
        commonViewFrame.setLocationRelativeTo(null);
        commonViewFrame.setLayout(null);
        commonViewFrame.setVisible(true);
        commonViewFrame.setAlwaysOnTop(true);
        commonViewFrame.isResizable();
        initUI();

    }

    public void initUI()
    {

        resultScrollPane=createTable();
        resultScrollPane.setBounds(10, ((commonViewFrame.getHeight())/4)+5, commonViewFrame.getSize().width - 40, 250);
        commonViewFrame.add(resultScrollPane);

    }

    /**
     * Create tournament table
     * @return
     */

    public JScrollPane createTable() {

        String[] columnNames = new String[gameCount+1];
        columnNames[0] = "";
        for (int i = 1; i <= gameCount; i++) {
            columnNames[i] = "Game" + i;
        }
        Object[][] data = new Object[mapCount][gameCount+1];
        for(int i = 0; i < mapCount; i++) {
            Object[] columnData = new Object[gameCount+1];
            columnData[0] = "Map" + (i + 1);
            for (int j = 1; j <= gameCount; j++){
                columnData[j] = resultList.get(i+(j-1)*mapCount);
            }
            data[i]=columnData;
        }

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        //Add the scroll pane to this panel.
        return (scrollPane);

    }




}
