package com.concordia.riskgame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Constants;

public class WorldDominationView extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private Gameplay gameInstance;
	private int rowCount;
	private List<Player> playerList;
	private Map gameMap;
	private JScrollPane playerScrollPane;
	

	public WorldDominationView() {
		super("World Domination View");
		setSize(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT);
        setMinimumSize(new Dimension(Constants.MAP_EDITOR_WIDTH, Constants.MAP_EDITOR_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

		gameInstance=Gameplay.getInstance();
		gameInstance.addObserver(this);
		rowCount=gameInstance.getPlayers().size();
		gameMap=gameInstance.getSelectedMap();
		playerList= gameInstance.getPlayers();
		
        initUI();
       		
	}
	
	
	public void initUI() 
	{
	    	
	playerScrollPane=createTable();
	playerScrollPane.setBounds(10, 20, this.getSize().width - 40, 350);
	add(playerScrollPane);

	}
	
	 public JScrollPane createTable() {
	    	
			String[] columnNames = { "Player Name ", "Percentage Of Map Owned", "Number of Armies Owned", "Continents Owned"};
			Object[][] data = new Object[rowCount][4];
			float countryCount=gameMap.listOfCountryNames().size();			
			int row=0;
			for(Player player:playerList) {
				
				String playerName=player.getPlayerName();
				List<Continent> continentList=gameMap.getOwnedContinents(playerName);
				String continentsOwned="";
				for(Continent continent:continentList)
					continentsOwned+=", "+continent.getContinentName();
				continentsOwned=(continentsOwned.contentEquals(""))?("None"):(continentsOwned.substring(1));
				
				float playerOwnedCountryCount=gameMap.getOwnedCountries(playerName).size();
				float percentageMapOwned=(playerOwnedCountryCount/countryCount)*100;
				Object[] rowData= {playerName,percentageMapOwned,player.getArmyCount(),continentsOwned};
					data[row]=rowData;
				++row;
				}
						
		/*
		 * for(int i=0;i<data.length;i++) {for(int j=0;j<data[i].length;j++)
		 * System.out.print(data[i][j].toString()+" "); System.out.println("\n"); }
		 */	final JTable table = new JTable(data, columnNames);
			table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			table.setFillsViewportHeight(true);
			table.setEnabled(false);

	//Create the scroll pane and add the table to it.
			JScrollPane scrollPane = new JScrollPane(table);

	//Add the scroll pane to this panel.
			return (scrollPane);
	    	
	    }


	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Inside observer");
		try {
			Field playerField=o.getClass().getDeclaredField("players");
			playerField.setAccessible(true);
			System.out.println("SIZE "+((ArrayList<Player>)(playerField.get(o))).size());
			//String playerName=((ArrayList<Player>)(playerField.get(o))).get(1).getPlayerName();
			//System.out.println(playerName);
			Field mapField=o.getClass().getDeclaredField("selectedMap");
			mapField.setAccessible(true);
			System.out.println("SIZE "+((ArrayList<Player>)(playerField.get(o))).size());
			
		this.gameMap=(Map)(mapField.get(o));
		this.playerList=((ArrayList<Player>)(playerField.get(o)));
		this.rowCount=playerList.size();
		remove(playerScrollPane);
		initUI();
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
