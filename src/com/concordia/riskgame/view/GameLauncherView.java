package com.concordia.riskgame.view;

//import com.concordia.riskgame.controller.ReinforcementView;
import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.model.Modules.Map;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.TitledBorder;

// TODO: Auto-generated Javadoc
public class GameLauncherView extends JFrame implements ActionListener {

	private JFrame gameWindow;
	final static String GAMELAUNCHERPANEL = "Card with Game Launching View";
	private CardLayout cardLayout;
	private JPanel cardsContainerPanel;

	private TitledBorder border;
	private JPanel gameLaunchPanel;
	private JButton startButton;
	private JButton MapEditor;
	private JButton editMapButton;
	private JButton tournamentButton;
	private JButton loadGameButton;
	private JButton exitButton;
	private JLabel titleLabel;

	//private ReinforcementView rView;
	private com.concordia.riskgame.view.MapEditorView mapEditorView;
	private StartUpPhaseView sView;
	private Scanner in;


//>>>>>>> SOEN6441_sucheta
	/**
	 * Instantiates a new game launcher view.
	 */
	public GameLauncherView() {
		in=new Scanner(System.in);
		JFrame.setDefaultLookAndFeelDecorated(true);
		gameWindow=new JFrame("****RISK GAME*****");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gameLaunchPanel = new JPanel();
		gameLaunchPanel.setLayout(null);
		gameLaunchPanel.setVisible(true);


		startButton=new JButton("Start Game");
		startButton.setVisible(true);
		startButton.setBounds(386, 30, 121, 20);
		MapEditor=new JButton("Map Editor");
		MapEditor.setVisible(true);
		MapEditor.setBounds(386, 60, 121, 20);
		gameLaunchPanel.add(MapEditor);
		gameLaunchPanel.add(startButton);

		startButton.addActionListener(this);
		MapEditor.addActionListener(this);

		initaliseCardLayoutUI();

		CreateMenuBar();
		while(true) {
			Scanner sc = new Scanner(System.in);
			String user_input;
			while (true)
			{
				user_input = sc.nextLine();
				CommandController.parseCommand(user_input);
			}
		}

	}


	public void initaliseCardLayoutUI() {

		cardsContainerPanel=new JPanel(new CardLayout());
		cardsContainerPanel.add(gameLaunchPanel,GAMELAUNCHERPANEL);

		gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
		cardLayout=(CardLayout) cardsContainerPanel.getLayout();
		cardLayout.show(cardsContainerPanel, GAMELAUNCHERPANEL);

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

		if(event.getSource()==startButton)
		{
			sView=new StartUpPhaseView(gameWindow,cardsContainerPanel);
		}
		else if(event.getSource()==MapEditor)
		{
			//new MapEditorView(new Map());
		}

	}


	/**
	 * create menu bar method
	 */
	private void CreateMenuBar() {

		JMenu menu, submenu;
		JMenuItem newMap, existingMap;
		JMenuBar mb=new JMenuBar();
		menu=new JMenu("Map Editor");
		newMap=new JMenuItem(new AbstractAction("Create New Map") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				//gameWindow.setVisible(false);
				Map newMap=new Map();
				mapEditorView=new MapEditorView(newMap);
				mapEditorView.setVisible(true);


			}
		});
		existingMap=new JMenuItem(new AbstractAction("Edit Existing Map") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

			}
		});
		menu.add(newMap); menu.add(existingMap);
		mb.add(menu);
		gameWindow.setJMenuBar(mb);
		gameWindow.setVisible(true);


	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		GameLauncherView game=new GameLauncherView();

	}


}
