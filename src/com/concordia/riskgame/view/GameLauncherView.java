/**
 *
 * <h1> THIS IS THE MAIN CLASS </h1>
 * <b> Initial Game launch will happen from this class </b>
 * This Module is the main module for game launch. It also has a view which is the first view user will interact.
 *
 * @author Sucheta.
 *
 * @version 2.0
 * @see https://www.ultraboardgames.com/risk/game-rules.php
 * @see https://www.hasbro.com/common/instruct/risk.pdf
 *
 */
package com.concordia.riskgame.view;

import com.concordia.riskgame.controller.CommandController;
import com.concordia.riskgame.controller.StartupPhaseController;
import com.concordia.riskgame.model.Modules.Map;
import com.concordia.riskgame.utilities.*;
import com.concordia.riskgame.utilities.ScannerUtil;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

/**
 * ===============================================
 * THIS IS THE DRIVER CLASS FOR PROJECT
 * ================================================
 * THIS CLASS IS THE INITAL VIEW OF THE GAME.
 */
public class GameLauncherView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JFrame gameWindow;
	final static String GAMELAUNCHERPANEL = "Card with Game Launching View";
	private CardLayout cardLayout;
	private JPanel cardsContainerPanel;

	private JPanel gameLaunchPanel;
	private JButton startButton;
	private DominationMapTools mapTool;


	private com.concordia.riskgame.view.MapEditorView mapEditorView;
	
	/**
	 * Instantiates a new game launcher view.
	 *
	 * @throws IOException throws an invalid input exceptions.
	 */
	public GameLauncherView() throws IOException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		gameWindow=new JFrame("***********************************************************RISK GAME****************************************************");
		
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapTool=new ConquestMapAdapter(new ConquestMapTools());
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
			Scanner sc = ScannerUtil.sc;
			String user_input;
			while (true)
			{
				user_input = sc.nextLine();
				CommandController.parseCommand(user_input);
			}
		}

	}


	/**
	 * Method to initialise card layout variables.
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
	 * create menu bar method
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
	 * @throws IOException throws an invalid input exception.
	 */
	public static void main(String args[]) throws IOException {
		new GameLauncherView();


	}


}
