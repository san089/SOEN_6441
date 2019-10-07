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
import java.util.Observable;
import java.util.Observer;

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
public class StartUpPhaseView extends JFrame implements ActionListener,Observer {

	private static final long serialVersionUID = 1L;
	private JPanel cardsContainerPanel;
	private CardLayout cardLayout;
	final static String MAPPANEL = "Card with MapLoaderView";
    final static String PLAYERPANEL = "Card with Add/Removing Player";
    final static String ASSIGNARMYPANEL = "Card with Assign Armies View";
    final static String[] playerCountValues = { "2", "3", "4", "5", "6" };
    
	private StartupPhaseController startupController;
	private Gameplay gamePlay;
	private JFrame gameWindow;
	private TitledBorder border;
	private JPanel StartupViewPanel;
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
	
	private JComboBox<String> playerCount;
	private JLabel playerCountLabel;
	private JLabel playerLimitLabel;
	private JButton nextCard;
	
	
	
	private JComboBox<String> playerList;
	private JLabel playerListLabel;
	private JComboBox countryList;
	private JLabel countryListLabel;
	private JButton placeArmyButton;
	private JPanel placeArmiesViewPanel;
	
			

	/**
	 * Instantiates a new reinforcement view.
	 */
	public StartUpPhaseView(JFrame frame,JPanel panel) {
		this.gameWindow=frame;
		this.cardsContainerPanel=panel;
		this.startupController=new StartupPhaseController();
		this.gamePlay=new Gameplay();
		gamePlay.setCurrentPhase("Startup");
		gamePlay.addObserver(this);
		initaliseUI();
		
	}

	/**
	 * Initalise UI.
	 */
	public void mapSelectUI() {
		StartupViewPanel = new JPanel();
		StartupViewPanel.setVisible(true);
		StartupViewPanel.setLayout(null);
		

		mapPath = new JTextField();
		mapSelectorLabel = new JLabel("Select the map file");
		mapSelectorButton = new JButton("Browse");
		StartupViewPanel.add(mapSelectorButton);
		StartupViewPanel.add(mapSelectorLabel);
		StartupViewPanel.add(mapPath);
		mapPath.setVisible(true);
		mapPath.setBounds(200, 30, 121, 20);
		mapSelectorLabel.setVisible(true);
		mapSelectorLabel.setBounds(56, 30, 121, 20);
		mapSelectorButton.setBounds(330, 30, 121, 20);
		mapSelectorButton.setVisible(true);
		mapSelectorButton.addActionListener(this);

		
		loadMapButton = new JButton("Load Map");
		StartupViewPanel.add(loadMapButton);
		loadMapButton.setBounds(186, 60, 121, 21);
		loadMapButton.setVisible(true);
		loadMapButton.addActionListener(this);
			
		nextCard=new JButton("Next View");
		StartupViewPanel.add(nextCard);
		nextCard.setBounds(186, 320, 121, 21);
		nextCard.setVisible(true);
		nextCard.addActionListener(this);
		
		
	}
	
	public void editPlayerUI() {
		
		playerCount = new JComboBox<String>(playerCountValues);
		 playerCountLabel = new JLabel("Select player count"); 
		 StartupViewPanel.add(playerCountLabel);
		 playerCountLabel.setVisible(true);
		 playerCountLabel.setBounds(56, 90, 121,20); 
		 StartupViewPanel.add(playerCount);
		 playerCount.setBounds(186, 90, 121, 20);
		
		
		
		
		playerNameLabel = new JLabel("Enter Player name to be added");
		StartupViewPanel.add(playerNameLabel);
		playerNameLabel.setVisible(true);
		playerNameLabel.setBounds(56, 120, 121, 20);
		
		playerName = new JTextField();
		StartupViewPanel.add(playerName);
		playerName.setVisible(true);
		playerName.setBounds(216, 120, 121, 20);
		
		addPlayerButton=new JButton("Add Player");
		addPlayerButton.setBounds(310, 120, 121, 20);
		addPlayerButton.setVisible(true);
		StartupViewPanel.add(addPlayerButton);
		addPlayerButton.addActionListener(this);
		
		removeplayerLabel = new JLabel("Enter Player name to be removed");
		StartupViewPanel.add(removeplayerLabel);
		removeplayerLabel.setVisible(true);
		removeplayerLabel.setBounds(56, 150, 121, 20);
		
		removePlayerName = new JTextField();
		StartupViewPanel.add(removePlayerName);
		removePlayerName.setVisible(true);
		removePlayerName.setBounds(216, 150, 121, 20);
		
		removePlayerButton=new JButton("Remove Player");
		removePlayerButton.setBounds(310, 150, 121, 20);
		removePlayerButton.setVisible(true);
		StartupViewPanel.add(removePlayerButton);
		removePlayerButton.addActionListener(this);
						
		
		populateCountriesButton = new JButton("Populate Countries and Assign Armies");
		StartupViewPanel.add(populateCountriesButton);
		populateCountriesButton.setBounds(186, 180, 200, 21);
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
		gameWindow.add(placeArmiesViewPanel);
		
		
	}
	
	
	public void initaliseUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		mapSelectUI();
		editPlayerUI();
		placeArmiesUI();
					
		
		cardsContainerPanel.add(StartupViewPanel,MAPPANEL);
		//cardsContainerPanel.add(addPlayersViewPanel,PLAYERPANEL);
		cardsContainerPanel.add(placeArmiesViewPanel, ASSIGNARMYPANEL);
		
		gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
		cardLayout=(CardLayout) cardsContainerPanel.getLayout();
		cardLayout.show(cardsContainerPanel, MAPPANEL);
		
		
		gameWindow.pack();
		gameWindow.setSize(900, 800);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		
		 
		
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
				
				if (!gamePlay.setSelectedMap(mapPath.getText())) {
					JOptionPane.showMessageDialog(gameWindow, "Invalid map selected.Please select a valid map",
							"Error Message", JOptionPane.ERROR_MESSAGE);
				} else {
					JLabel mapLoaderLabel=new JLabel("Map Loaded Succesfully");
					mapLoaderLabel.setBounds(256, 60, 121, 21);
					mapLoaderLabel.setVisible(true);
					StartupViewPanel.add(mapLoaderLabel);
				}

			} else

			{
				JOptionPane.showMessageDialog(gameWindow, "Please enter a valid path", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
				
		else if(event.getSource()==addPlayerButton)
		{	if(gamePlay.getPlayerCount()==0)
				gamePlay.setPlayerCount(Integer.parseInt(playerCount.getSelectedItem().toString()));
		
			if(playerName.getText().contentEquals(""))
				JOptionPane.showMessageDialog(gameWindow,
				          "Please enter a player name", "Error Message",
				          JOptionPane.ERROR_MESSAGE);
			else if(!gamePlay.findDuplicatePlayer(playerName.getText()))
				JOptionPane.showMessageDialog(gameWindow,
				          "Another player with the same name already exists.PLease enter another name", "Error Message",
				          JOptionPane.ERROR_MESSAGE);
			else
				gamePlay.addPlayer(playerName.getText());
						
		}
		
		else if(event.getSource()==removePlayerButton)
		{
			if(!(playerName.getText().contentEquals("")))
				gamePlay.removePlayer(playerName.getText());
			else
				JOptionPane.showMessageDialog(gameWindow,
				          "Please enter a player name", "Error Message",
				          JOptionPane.ERROR_MESSAGE);
						
		}
		
		
		
		else if(event.getSource()==populateCountriesButton)
		{
			
			if(!(startupController.validateStartupInputs()))
			{
<<<<<<< HEAD
				JOptionPane.showMessageDialog(viewWindow,
=======
				cardLayout.show(cardsContainerPanel, ASSIGNARMYPANEL);
				JOptionPane.showMessageDialog(gameWindow,
>>>>>>> master
					          "Inputs not provided- Please provide both the player names and map file", "Error Message",
		          JOptionPane.ERROR_MESSAGE);
				
			}
			else
			{
			startupController.initialisePlayers();	
			JOptionPane.showMessageDialog(gameWindow,
				          "Player Initialisation complete-Press OK to continue", "Information Message",
	          JOptionPane.INFORMATION_MESSAGE);
				cardLayout.show(cardsContainerPanel, ASSIGNARMYPANEL);
			}
				
		}
		
		else if (event.getSource()==nextCard)
		{
			cardLayout.show(cardsContainerPanel, PLAYERPANEL);
							
		}
		
		
		

}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("<--------------------------OBSERVER TRIGGERED--------------------->");
		if(Integer.parseInt(arg.toString())==gamePlay.getPlayerCount()) {
		playerLimitLabel=new JLabel("Maximum PLayer Limit Reached");
		playerLimitLabel.setBounds(310, 120, 121, 20);
		StartupViewPanel.add(playerLimitLabel);
		addPlayerButton.setVisible(false);
		playerLimitLabel.setVisible(true);
		}
		else if(Integer.parseInt(arg.toString())<gamePlay.getPlayerCount())
		{
			if(playerLimitLabel!=null)
				playerLimitLabel.setVisible(false);
			addPlayerButton.setVisible(true);
		}	
		
		
	}
}
