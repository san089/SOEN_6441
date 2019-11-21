/**
 *
 * This Module is the view for domination which shows who owns what at any point in game.
 *
 * @author Sucheta
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */


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

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JFrame commonViewFrame;
	//private Gameplay gameInstance;
	private int rowCount;
	private List<Player> playerList;
	private Map gameMap;
	private JScrollPane playerScrollPane;
	private JLabel dominationViewLabel;



	public WorldDominationView(JFrame frame) {
		commonViewFrame=frame;


		JPanel viewLabelPanel = new JPanel();
		viewLabelPanel.setBounds(10, ((commonViewFrame.getHeight())/2)-20, commonViewFrame.getSize().width - 40, 25);
		viewLabelPanel.setBackground(Color.lightGray);
		viewLabelPanel.setBorder(Constants.blackline);
		commonViewFrame.add(viewLabelPanel);


		//gameInstance=Gameplay.getInstance();
		Gameplay.getInstance().addObserver(this);
		rowCount=Gameplay.getInstance().getPlayers().size();
		gameMap=Gameplay.getInstance().getSelectedMap();
		playerList= Gameplay.getInstance().getPlayers();


		dominationViewLabel=new JLabel("World Domination View");
		dominationViewLabel.setFont(new Font("dialog", 1, 15));
		dominationViewLabel.setSize(dominationViewLabel.getPreferredSize());
		dominationViewLabel.setLocation((viewLabelPanel.getSize().width-200)/2,((viewLabelPanel.getHeight())/4)-2);
		dominationViewLabel.setVisible(true);
		viewLabelPanel.add(dominationViewLabel);
		viewLabelPanel.repaint();



		initUI();

	}


	public void initUI()
	{

		playerScrollPane=createTable();
		playerScrollPane.setBounds(10, ((commonViewFrame.getHeight())/2)+5, commonViewFrame.getSize().width - 40, 250);
		commonViewFrame.add(playerScrollPane);

		//playerScrollPane.setBounds(10, 30, commonViewFrame.getSize().width - 40, 350);


	}

	public JScrollPane createTable() {

		String[] columnNames = { "Player Name ", "Percentage Of Map Owned", "Number of Armies Owned", "Continents Owned"};
		Object[][] data = new Object[rowCount][4];
		float countryCount=(gameMap==null)?0:gameMap.listOfCountryNames().size();
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

		final JTable table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		//	table.setAutoscrolls(true);

		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		//Add the scroll pane to this panel.
		return (scrollPane);

	}


	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {

		if(arg.toString().contains("domination")) {
			try {

				Field playerField = o.getClass().getDeclaredField("players");
				playerField.setAccessible(true);
				Field mapField = o.getClass().getDeclaredField("selectedMap");
				mapField.setAccessible(true);
				this.gameMap = (Map) (mapField.get(o));
				this.playerList = ((ArrayList<Player>) (playerField.get(o)));
				this.rowCount = playerList.size();
				commonViewFrame.remove(playerScrollPane);
				initUI();
			} catch (Exception  e) {

				//e.printStackTrace();
			}
		}

	}

}