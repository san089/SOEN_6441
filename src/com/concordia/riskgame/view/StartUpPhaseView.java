/**
 *
 * This Module is the view for card exchange
 *
 * @author Hai Feng
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 *
 */

package com.concordia.riskgame.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Phases;


/**
 * The Class Initiates the Startup View in the Project after the MapEditor View.
 *
 */
public class StartUpPhaseView extends JFrame  {

	private static final long serialVersionUID = 1L;
	public static JPanel cardsContainerPanel;
	public static CardLayout cardLayout;

	public final static String MAPPANEL = "Card with MapLoaderView";
	public final static String PLAYERPANEL = "Card with Add/Removing Player";
	public final static String ASSIGNARMYPANEL = "Card with Assign Armies View";
	public final static String[] playerCountValues = { "2", "3", "4", "5", "6" };
	public final static String[] playerStrategies= {"Human","Aggressive","Benevolent","Random","Cheater"};

	//private StartupPhaseController startupController;
	//private Gameplay gamePlay;
	private JFrame gameWindow;
	private JPanel StartupViewPanel;
	private JButton addPlayerButton;
	private JButton mapSelectorButton;
	private JTextField mapPath;
	private JLabel playerNameLabel;
	private JLabel mapSelectorLabel;
	private JTextField playerName;
	private JLabel removeplayerLabel;
	private JTextField removePlayerName;
	private JButton removePlayerButton;
	private JButton populateCountriesButton;
	private JList<String> currentPlayerList;
	private DefaultListModel<String> model;
	private JButton showMapButton;

	private JComboBox<String> playerCount;
	private JLabel playerCountLabel;

	private JComboBox<String> playerStrategy;



	private JComboBox<String> playerList;
	private JLabel playerListLabel;
	private JPanel placeArmiesViewPanel;





	/**
	 * Instantiates a new start up phase view.
	 *
	 * @param frame the frame
	 * @param panel the panel
	 */
	public StartUpPhaseView(JFrame frame,JPanel panel) {
		this.gameWindow=frame;
		StartUpPhaseView.cardsContainerPanel=panel;
	//	this.gamePlay = Gameplay.getInstance();
		Gameplay.getInstance().setCurrentPhase(Phases.Startup);
		initaliseUI();
		new PhaseView();

	}



	/**
	 * Initalise UI.
	 */
	public void initaliseUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		mapSelectUI();
		editPlayerUI();
		placeArmiesUI();


		cardsContainerPanel.add(StartupViewPanel,MAPPANEL);
		cardsContainerPanel.add(placeArmiesViewPanel, ASSIGNARMYPANEL);

		gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
		cardLayout=(CardLayout) cardsContainerPanel.getLayout();
		cardLayout.show(cardsContainerPanel, MAPPANEL);

	}



	/**
	 * Initalise UI.
	 */
	public void mapSelectUI() {
		StartupViewPanel = new JPanel();
		StartupViewPanel.setVisible(true);
		StartupViewPanel.setLayout(null);


		mapSelectorLabel = new JLabel("Select the map file");
		mapSelectorButton=(new JButton("Browse"));
		StartupViewPanel.add(getMapSelectorButton());
		StartupViewPanel.add(mapSelectorLabel);
		mapSelectorLabel.setVisible(true);
		mapSelectorLabel.setBounds(36, 60, 121, 20);
		mapSelectorButton.setBounds(216, 60, 121, 20);
		mapSelectorButton.setVisible(true);


		model = new DefaultListModel<String>();
		currentPlayerList=new JList<String>(model);
		playerListLabel=new JLabel("Player List");
		StartupViewPanel.add(currentPlayerList);
		StartupViewPanel.add(playerListLabel);
		playerListLabel.setBounds(530, 30, 121, 21);
		currentPlayerList.setBounds(530, 50, 121, 200);
		currentPlayerList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		currentPlayerList.setVisible(true);




	}


	/**
	 * Edits the player UI.
	 */
	public void editPlayerUI() {

		playerCount = new JComboBox<String>(playerCountValues);
		playerCountLabel = new JLabel("Select player count");
		StartupViewPanel.add(playerCountLabel);
		playerCountLabel.setVisible(true);
		playerCountLabel.setBounds(36, 90, 121,20);
		StartupViewPanel.add(playerCount);
		playerCount.setBounds(216, 90, 121, 20);




		playerNameLabel = new JLabel("Enter Player name to add");
		StartupViewPanel.add(playerNameLabel);
		playerNameLabel.setVisible(true);
		playerNameLabel.setBounds(36, 120, 200, 21);

		playerName = new JTextField();
		StartupViewPanel.add(playerName);
		playerName.setVisible(true);
		playerName.setBounds(216, 120, 121, 20);

		addPlayerButton=new JButton("Add Player");
		addPlayerButton.setBounds(216, 150, 121, 20);
		addPlayerButton.setVisible(true);
		StartupViewPanel.add(addPlayerButton);

		playerStrategy = new JComboBox<String>(playerStrategies);
		//	playerStrategyLabel = new JLabel("Select player type");
		//	StartupViewPanel.add(playerStrategyLabel);
		//	playerStrategyLabel.setVisible(true);
		//	playerStrategyLabel.setBounds(36, 150, 121,20);
		StartupViewPanel.add(playerStrategy);
		playerStrategy.setBounds(350, 120, 121, 20);


		removeplayerLabel = new JLabel("Enter Player name to remove");
		StartupViewPanel.add(removeplayerLabel);
		removeplayerLabel.setVisible(true);
		removeplayerLabel.setBounds(36, 180, 200, 21);

		removePlayerName = new JTextField();
		StartupViewPanel.add(removePlayerName);
		removePlayerName.setVisible(true);
		removePlayerName.setBounds(216, 180, 121, 20);

		removePlayerButton=new JButton("Remove Player");
		removePlayerButton.setBounds(350, 180, 121, 20);
		removePlayerButton.setVisible(true);
		StartupViewPanel.add(removePlayerButton);


		populateCountriesButton = new JButton("Populate Countries and Assign Armies");
		StartupViewPanel.add(populateCountriesButton);
		populateCountriesButton.setBounds(36, 210, 250, 30);
		populateCountriesButton.setVisible(true);



		showMapButton = new JButton("Show Current Game Map");
		StartupViewPanel.add(showMapButton);
		showMapButton.setBounds(300, 210, 200, 30);
		//showMapButton.setHorizontalAlignment(SwingConstants.LEFT);
		showMapButton.setVisible(true);

	}



	/**
	 * Place armies UI.
	 */
	public void placeArmiesUI() {
		placeArmiesViewPanel=new JPanel();
		ArrayList<String> playerNames=new ArrayList<String>();
		for(Player player:Gameplay.getInstance().getPlayers())
			playerNames.add(player.getPlayerName());
		playerList=new JComboBox<String>(playerNames.toArray(new String[playerNames.size()]));
		placeArmiesViewPanel.add(playerList);


		playerListLabel=new JLabel("Select the player ");
		placeArmiesViewPanel.setVisible(true);


	}

	/**
	 * Getter to the element shopMapButton
	 * @return shopMapButton
	 */
	public JButton getShowMapButton() {
		return showMapButton;
	}

	/**
	 * Getter method for the Playername text field
	 * @return playerName
	 */
	public JTextField getPlayerName() {
		return playerName;
	}

	/**
	 * Getter method for the populate countries Button
	 * @return populateCountriesButton
	 */
	public JButton getPopulateCountriesButton() {
		return populateCountriesButton;
	}

	/**
	 * Getter method for removeplayer Button
	 * @return removePlayerButton
	 */
	public JButton getRemovePlayerButton() {
		return removePlayerButton;
	}

	/**
	 * Getter method for the getRemovePlayerName text field
	 * @return removePlayerName
	 */
	public JTextField getRemovePlayerName() {
		return removePlayerName;
	}

	/**
	 * Getter method for the addplayerbutton
	 * @return addPlayerButton
	 */
	public JButton getAddPlayerButton() {
		return addPlayerButton;
	}

	/**
	 * Getter method for the mapPath text field
	 * @return mapPath
	 */
	public JTextField getMapPath() {
		return mapPath;
	}

	/**
	 * Setter method for mappath TextField
	 * @param mapPath mapPath
	 */
	public void setMapPath(JTextField mapPath) {
		this.mapPath = mapPath;
	}

	/**
	 * Getter method for the Map selection button
	 * @return mapSelectorButton
	 */
	public JButton getMapSelectorButton() {
		return mapSelectorButton;
	}

	/**
	 * Getter method for the playercount drop down
	 * @return playerCount
	 */
	public JComboBox<String> getPlayerCount() {
		return playerCount;
	}

	/**
	 * Getter method for the cardLayout
	 * @return cardLayout
	 */
	public CardLayout getCardLayout() {
		return cardLayout;
	}

	/**
	 * Getter method for the Model
	 * @return model
	 */
	public DefaultListModel<String> getModel() {
		return model;
	}

	/**
	 * Getter method for the currentplayerlist
	 * @return currentPlayerList
	 */
	public JList<String> getCurrentPlayerList() {
		return currentPlayerList;
	}



	public JComboBox<String> getPlayerStrategy() {
		return playerStrategy;
	}



}