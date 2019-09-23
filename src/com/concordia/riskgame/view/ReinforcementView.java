package com.concordia.riskgame.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ReinforcementView extends JFrame implements ActionListener {

	private JFrame viewWindow;
	private TitledBorder border;
	private JPanel viewPanel;
	private JButton submitSelectionButton;
	private JButton mapSelectorButton;
	FileNameExtensionFilter fileFilter;
	private JFileChooser mapSelector;
	private JComboBox<String> playerCount;
	private String[] playerCountValues= {"1","2","3","4","5","6","7","8"};
			
	
	public ReinforcementView() {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		viewWindow=new JFrame("REINFORCEMENT VIEW");
		viewWindow.setVisible(true);
		viewWindow.setSize(500, 500);
		viewWindow.setLocation(600,200);
		viewWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		viewPanel = new JPanel();
		viewPanel.setVisible(true);
		viewWindow.add(viewPanel);
	
		
		playerCount=new JComboBox<String>(playerCountValues);
		viewPanel.add(playerCount);
		
		mapSelectorButton=new JButton("Browse");
		mapSelectorButton.setVisible(true);
		mapSelectorButton.addActionListener(this);
		viewPanel.add(mapSelectorButton);
		
		
		submitSelectionButton=new JButton("Submit");
		viewPanel.add(submitSelectionButton);
		submitSelectionButton.setVisible(true);
		submitSelectionButton.addActionListener(this);
					
	}





	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().toString().contains("Browse"))
		{
			System.out.println("Inside Submit Player");
		
			mapSelector = new JFileChooser();
			fileFilter = new FileNameExtensionFilter(" .map", "map", "map");
			mapSelector.setDialogTitle("Select the desired map file");
			mapSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
			mapSelector.setFileFilter(fileFilter);
			mapSelector.showOpenDialog(mapSelector);
			mapSelector.setLocation(500, 200);
			mapSelector.setSize(500, 500);
			mapSelector.setVisible(true);
			viewPanel.add(mapSelector);
		//	mapSelector.setVisible(true);
						
		}
		else if(event.getSource().toString().contains("Submit"))
		{
			String playerCountValue=playerCount.getSelectedItem().toString();
			//System.out.println(playerCountValue);
		}
			
		
		
	}

	
	
}
