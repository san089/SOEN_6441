package com.concordia.riskgame.view;

import com.concordia.riskgame.model.Modules.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

// TODO: Auto-generated Javadoc
public class GameLauncherView extends JFrame implements ActionListener {

	private JFrame gameWindow;
	private TitledBorder border;
	private JPanel gamePanel;
	private JButton startButton;
	private JButton createMapButton;
	private JButton editMapButton;
	private JButton tournamentButton;
	private JButton loadGameButton;
	private JButton exitButton;
	private JLabel titleLabel;
	private ReinforcementView rView;
	private MapEditor mapEditorView;




	/**
	 * Instantiates a new game launcher view.
	 */
	public GameLauncherView() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		gameWindow=new JFrame("****RISK GAME*****");
		gameWindow.setVisible(true);
		gameWindow.setSize(900, 800);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new JPanel();
		gamePanel.setVisible(true);
		gameWindow.add(gamePanel);
		startButton=new JButton("Start Game");
		startButton.setVisible(true);
		gamePanel.add(startButton);
		startButton.addActionListener(this);
		CreateMenuBar();

		
		
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.println(event.getSource());
		if(event.getSource().toString().contains("Start Game"))
		{
			System.out.println("Start Game button clicked");
			gamePanel.setVisible(false);
			rView=new ReinforcementView();
			rView.setVisible(true);
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
				Map newMap=new Map();
				mapEditorView=new MapEditor(newMap);
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
