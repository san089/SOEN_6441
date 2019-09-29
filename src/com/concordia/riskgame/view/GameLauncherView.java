package com.concordia.riskgame.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
	private StartUpPhaseView sView;
	private Scanner in;
	
	
	/**
	 * Instantiates a new game launcher view.
	 */
	public GameLauncherView() {
		in=new Scanner(System.in);
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
		while(true) {
			
			//read Inputs
		}
		
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		//System.out.println(event.getSource());
		if(event.getSource()==startButton)
		{
			gameWindow.setVisible(false);
			sView=new StartUpPhaseView();
			sView.setVisible(true);
		}
			
	}
	
	
	/**
	 * create menu bar method
	 */
	private void CreateMenuBar() {
	
        JMenu menu, submenu;  
        JMenuItem i1, i2, i3, i4, i5;  
        JMenuBar mb=new JMenuBar();  
        menu=new JMenu("Map Editor");  
        i1=new JMenuItem("Create New Map");  
        i2=new JMenuItem("Edit Existing Map");   
        menu.add(i1); menu.add(i2); 
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
