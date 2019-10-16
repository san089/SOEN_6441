package com.concordia.riskgame.view;

//import com.concordia.riskgame.controller.ReinforcementView;
import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.controller.StartupPhaseController;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.utilities.MapTools;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.TitledBorder;

// TODO: Auto-generated Javadoc
public class GameLauncherView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame gameWindow;
	final static String GAMELAUNCHERPANEL = "Card with Game Launching View";
	private CardLayout cardLayout;
	private JPanel cardsContainerPanel;

	private JPanel gameLaunchPanel;
	private JButton startButton;
	private MapTools mapTool;


	private com.concordia.riskgame.view.MapEditorView mapEditorView;
	
	/**
	 * Instantiates a new game launcher view.
	 */
	public GameLauncherView() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		gameWindow=new JFrame("***********************************************************RISK GAME****************************************************");
		
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapTool=new MapTools();
		gameLaunchPanel = new JPanel();
		gameLaunchPanel.setLayout(null);
		gameLaunchPanel.setVisible(true);


		startButton=new JButton("Start Game");
		startButton.setVisible(true);
		startButton.setBounds(300, 210, 121, 21);
		gameLaunchPanel.add(startButton);

		startButton.addActionListener(this);

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


	/**
	 * Initalise card layout UI.
	 */
	public void initaliseCardLayoutUI() {

		cardsContainerPanel=new JPanel(new CardLayout());
		cardsContainerPanel.add(gameLaunchPanel,GAMELAUNCHERPANEL);

		gameWindow.getContentPane().add(cardsContainerPanel, BorderLayout.CENTER);
		cardLayout=(CardLayout) cardsContainerPanel.getLayout();
		cardLayout.show(cardsContainerPanel, GAMELAUNCHERPANEL);

		gameWindow.pack();
		//gameWindow.setFont(Font.CE);
		gameWindow.setSize(700, 700);
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
			new StartupPhaseController(new StartUpPhaseView(gameWindow,cardsContainerPanel));
		}
		
	}


	/**
	 * create menu bar method.
	 */
	private void CreateMenuBar() {

		JMenu menu;
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

				
				Map existingMap=new Map();
				String sFinal=mapTool.pickMapFile(existingMap);
				System.out.println(sFinal);
				if(sFinal == null || (sFinal.isEmpty())) {}
				else {
					boolean isMapValid=mapTool.parseAndValidateMap(existingMap,3);
					if(isMapValid) {
						JOptionPane.showMessageDialog(null, "Map successfully loaded");
						MapEditorView mapEditorView=new MapEditorView(existingMap);
						mapEditorView.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "Invalid Map selected");
						System.out.println(existingMap.getErrorMessage());
					}
				}
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
		new GameLauncherView();

	}


}
