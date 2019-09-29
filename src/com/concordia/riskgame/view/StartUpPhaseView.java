package com.concordia.riskgame.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.concordia.riskgame.controller.StartupPhaseController;
import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class ReinforcementView.

 */
public class StartUpPhaseView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel cardsContainerPanel;
	private CardLayout cardLayout;
	final static String MAPPANEL = "Card with MapLoaderView";
    final static String PLAYERPANEL = "Card with Add/Removing Player";
    final static String ASSIGNARMYPANEL = "Card with Assign Armies View";
    
	private StartupPhaseController startupController;
	private Gameplay gamePlay;
	private JFrame viewWindow;
	private TitledBorder border;
	private JPanel loadMapViewPanel;
	private JPanel addPlayersViewPanel;
	private JButton loadMapButton;
	private JButton addPlayerButton;
	private JButton mapSelectorButton;
	private FileNameExtensionFilter fileFilter;
	private JFileChooser mapSelector;
	private JTextField mapPath;
	private JLabel playerNameLabel;
	private JLabel mapSelectorLabel;
	private JTextField playerName;
	private ArrayList<String> countriesinMapstub;
	private JLabel removeplayerLabel;
	private JTextField removePlayerName;
	private JButton removePlayerButton;
	private JButton populateCountriesButton;
	
	private JComboBox<String> playerList;
	private JLabel playerListLabel;
	private JComboBox countryList;
	private JLabel countryListLabel;
	private JButton placeArmyButton;
	private JPanel placeArmiesViewPanel;
	
			

	/**
	 * Instantiates a new reinforcement view.
	 */
	public StartUpPhaseView() {
		startupController=new StartupPhaseController();
		gamePlay=new Gameplay();
		gamePlay.setCurrentPhase("Startup");
		initaliseUI();
		
	}

	/**
	 * Initalise UI.
	 */
	public void mapSelectUI() {
		loadMapViewPanel = new JPanel();
		loadMapViewPanel.setVisible(true);
		loadMapViewPanel.setLayout(null);
		

		mapPath = new JTextField();
		mapSelectorLabel = new JLabel("Select the map file");
		mapSelectorButton = new JButton("Browse");
		loadMapViewPanel.add(mapSelectorButton);
		loadMapViewPanel.add(mapSelectorLabel);
		loadMapViewPanel.add(mapPath);
		mapPath.setVisible(true);
		mapPath.setBounds(186, 30, 121, 20);
		mapSelectorLabel.setVisible(true);
		mapSelectorLabel.setBounds(56, 30, 121, 20);
		mapSelectorButton.setBounds(310, 30, 121, 20);
		mapSelectorButton.setVisible(true);
		mapSelectorButton.addActionListener(this);

		
		loadMapButton = new JButton("Load Map");
		loadMapViewPanel.add(loadMapButton);
		loadMapButton.setBounds(186, 60, 121, 21);
		loadMapButton.setVisible(true);
		loadMapButton.addActionListener(this);
			
		
		
	}
	
	public void editPlayerUI() {
		addPlayersViewPanel= new JPanel();
		addPlayersViewPanel.setVisible(true);
		addPlayersViewPanel.setLayout(null);			
		
		playerNameLabel = new JLabel("Enter Player name to be added");
		addPlayersViewPanel.add(playerNameLabel);
		playerNameLabel.setVisible(true);
		playerNameLabel.setBounds(56, 30, 121, 20);
		
		playerName = new JTextField();
		addPlayersViewPanel.add(playerName);
		playerName.setVisible(true);
		playerName.setBounds(186, 30, 121, 20);
		
		addPlayerButton=new JButton("Add Player");
		addPlayerButton.setBounds(310, 30, 121, 20);
		addPlayerButton.setVisible(true);
		addPlayersViewPanel.add(addPlayerButton);
		addPlayerButton.addActionListener(this);
		
		removeplayerLabel = new JLabel("Enter Player name to be removed");
		addPlayersViewPanel.add(removeplayerLabel);
		removeplayerLabel.setVisible(true);
		removeplayerLabel.setBounds(56, 60, 121, 20);
		
		removePlayerName = new JTextField();
		addPlayersViewPanel.add(removePlayerName);
		removePlayerName.setVisible(true);
		removePlayerName.setBounds(186, 60, 121, 20);
		
		removePlayerButton=new JButton("Remove Player");
		removePlayerButton.setBounds(310, 60, 121, 20);
		removePlayerButton.setVisible(true);
		addPlayersViewPanel.add(removePlayerButton);
		removePlayerButton.addActionListener(this);
				
		populateCountriesButton = new JButton("Populate Countries");
		addPlayersViewPanel.add(populateCountriesButton);
		populateCountriesButton.setBounds(186, 150, 150, 21);
		populateCountriesButton.setVisible(true);
		populateCountriesButton.addActionListener(this);
		
		
		populateCountriesButton = new JButton("Populate Countries and Assign Armies");
		addPlayersViewPanel.add(populateCountriesButton);
		populateCountriesButton.setBounds(186, 150, 200, 21);
		populateCountriesButton.setVisible(true);
		populateCountriesButton.addActionListener(this);
	
		
	}
	
	
	public void placeArmiesUI() {
		placeArmiesViewPanel=new JPanel();
		ArrayList<String> playerNames=new ArrayList<String>();
		for(Player player:gamePlay.getPlayers())
			playerNames.add(player.getPlayerName());
		playerList=new JComboBox<String>(playerNames.toArray(new String[playerNames.size()]));
		placeArmiesViewPanel.add(playerList);
		playerListLabel=new JLabel("Select the player ");
		placeArmiesViewPanel.setVisible(true);
		viewWindow.add(placeArmiesViewPanel);
		
		
	}
	
	
	public void initaliseUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		viewWindow=new JFrame("Game Startup Phase");
		viewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mapSelectUI();
		editPlayerUI();
		placeArmiesUI();
					
		
		cardsContainerPanel=new JPanel(new CardLayout());
		cardsContainerPanel.add(loadMapViewPanel,MAPPANEL);
		cardsContainerPanel.add(addPlayersViewPanel,PLAYERPANEL);
		cardsContainerPanel.add(placeArmiesViewPanel, ASSIGNARMYPANEL);
		
		viewWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
		cardLayout=(CardLayout) cardsContainerPanel.getLayout();
		cardLayout.show(cardsContainerPanel, MAPPANEL);
		
		
		viewWindow.pack();
		viewWindow.setSize(900, 800);
		viewWindow.setLocationRelativeTo(null);
		viewWindow.setVisible(true);
		
		 
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		
		System.out.println(event.getSource());
		if (event.getSource()==mapSelectorButton)
		{
			mapSelector = new JFileChooser();
			fileFilter = new FileNameExtensionFilter(" .map", "map", "map");
			mapSelector.setDialogTitle("Select the desired map file");
			mapSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
			mapSelector.setFileFilter(fileFilter);
			mapSelector.showOpenDialog(mapSelector);
			mapSelector.setLocation(500, 200);
			mapSelector.setSize(500, 500);
			mapSelector.setVisible(true);
							
		}
		
		else if (event.getSource() == loadMapButton) {
			// cardLayout.show(cardsContainerPanel, PLAYERPANEL);

			if (!(mapPath.getText().contentEquals(""))) {
				Boolean result = startupController.setSelectedMap(mapPath.getText());
				if (!result) {
					JOptionPane.showMessageDialog(viewWindow, "Invalid map selected.Please select a valid map",
							"Error Message", JOptionPane.ERROR_MESSAGE);
				} else {
					cardLayout.show(cardsContainerPanel, PLAYERPANEL);
				}

			} else

			{
				JOptionPane.showMessageDialog(viewWindow, "Please enter a valid path", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else if(event.getSource()==addPlayerButton)
		{
			if(!(playerName.getText().contentEquals("")))
				startupController.addPlayer(playerName.getText());
			else
				JOptionPane.showMessageDialog(viewWindow,
				          "Please enter a player name", "Error Message",
				          JOptionPane.ERROR_MESSAGE);
						
		}
		
		else if(event.getSource()==removePlayerButton)
		{
			if(!(playerName.getText().contentEquals("")))
				startupController.removePlayer(playerName.getText());
			else
				JOptionPane.showMessageDialog(viewWindow,
				          "Please enter a player name", "Error Message",
				          JOptionPane.ERROR_MESSAGE);
						
		}
		
		
		
		else if(event.getSource().toString().contains("Populate"))
		{
		//	System.out.println("Inside populate countries");
			
			if(!(startupController.validateStartupInputs()))
			{
				cardLayout.show(cardsContainerPanel, ASSIGNARMYPANEL);
				JOptionPane.showMessageDialog(viewWindow,
					          "Inputs not provided- Please provide both the player names and map file", "Error Message",
		          JOptionPane.ERROR_MESSAGE);
				
			}
			else
			{
			startupController.initialisePlayers();	
			JOptionPane.showMessageDialog(viewWindow,
				          "Player Initialisation complete-Press OK to continue", "Information Message",
	          JOptionPane.INFORMATION_MESSAGE);
				cardLayout.show(cardsContainerPanel, ASSIGNARMYPANEL);
			}
				
		}
		
		

}
}
