package com.concordia.riskgame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import com.concordia.riskgame.controller.MapEditorController;
import com.concordia.riskgame.model.Modules.Continent;
import com.concordia.riskgame.model.Modules.Country;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Constants;

public class WorldDominationView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorldDominationView() {
		
		super("World Domination View");
        setSize(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT);
        setMinimumSize(new Dimension(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        initUI();
       		
	}
	
	
	public void initUI() 
	{
		/*
		 * setLayout(null); Border blackline =
		 * BorderFactory.createLineBorder(Color.black); JPanel playerLabelViewPanel =
		 * new JPanel(); JLabel playerLabel = new JLabel("World Domination View");
		 * playerLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		 * playerLabel.setBounds(140, 450, playerLabel.getPreferredSize().width + 500,
		 * playerLabel.getPreferredSize().height); playerLabelViewPanel.setBounds(10,
		 * 470, this.getSize().width - 40, 35); add(playerLabelViewPanel);
		 * playerLabelViewPanel.setBackground(Color.lightGray);
		 * playerLabelViewPanel.setLayout(new FlowLayout()); playerLabel.setFont(new
		 * Font("dialog", 1, 15)); playerLabelViewPanel.setBorder(blackline);
		 * playerLabelViewPanel.add(playerLabel);
		 */
    
        	
	JScrollPane playerScrollPane=createTable();
	playerScrollPane.setBounds(10, 20, this.getSize().width - 40, 350);
	add(playerScrollPane);

	}
	
	 public JScrollPane createTable() {
	    	
			String[] columnNames = { "Player Name ", "Percentage Of Map Owned", "Number of Armies Owned", "Continents Owned"};
			Gameplay gameInstance=Gameplay.getInstance();
			int rowCount=gameInstance.getPlayers().size();
			float countryCount=gameInstance.getSelectedMap().listOfCountryNames().size();
			List<Player> playerList= gameInstance.getPlayers();
			Object[][] data = new Object[rowCount][4];
			int row=0;
			for(Player player:playerList) {
				
				String playerName=player.getPlayerName();
				List<Continent> continentList=gameInstance.getSelectedMap().getOwnedContinents(playerName);
				String continentsOwned="";
				for(Continent continent:continentList)
					continentsOwned+=", "+continent.getContinentName();
				continentsOwned=(continentsOwned.contentEquals(""))?("None"):(continentsOwned.substring(1));
				
				float playerOwnedCountryCount=gameInstance.getSelectedMap().getOwnedCountries(playerName).size();
				float percentageMapOwned=(playerOwnedCountryCount/countryCount)*100;
				Object[] rowData= {playerName,percentageMapOwned,player.getArmyCount(),continentsOwned};
					data[row]=rowData;
				++row;
				}
						
			for(int i=0;i<data.length;i++)
				{for(int j=0;j<data[i].length;j++)
					System.out.print(data[i][j].toString()+" ");
				System.out.println("\n");
				}
			final JTable table = new JTable(data, columnNames);
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			table.setFillsViewportHeight(true);
			table.setEnabled(false);

	//Create the scroll pane and add the table to it.
			JScrollPane scrollPane = new JScrollPane(table);

	//Add the scroll pane to this panel.
			return (scrollPane);
	    	
	    }

}
