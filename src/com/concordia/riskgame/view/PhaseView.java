/**
 *
 * This Module is the view for gameplay which shows various information about the current game phase.
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

import com.concordia.riskgame.model.Modules.Gameplay;
import com.concordia.riskgame.model.Modules.Player;
import com.concordia.riskgame.utilities.Constants;
import com.concordia.riskgame.utilities.Phases;

public class PhaseView extends JFrame implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame commonViewFrame;
	private JLabel phaseViewLabel;
	private  JLabel loggerLabel;
//	private Gameplay gameInstance;
	private ArrayList<String> loggerList;
	private JScrollPane playerScrollPane;
	private JTextArea loggerText;
	private String viewLogger;
	private Player currentPlayer;
	private Phases currentPhase;
	
	

	public PhaseView() {
		
		//gameInstance=Gameplay.getInstance();
		Gameplay.getInstance().addObserver(this);
		currentPlayer=Gameplay.getInstance().getCurrentPlayer();
		currentPhase=Gameplay.getInstance().getCurrentPhase();
		loggerList=Gameplay.getInstance().getViewLogger();
		viewLogger="";
		
		
		
		commonViewFrame=new JFrame("Game Views");
		commonViewFrame.setSize(Constants.COMMON_VIEW_WIDTH, Constants.COMMON_VIEW_HEIGHT);
		commonViewFrame.setMinimumSize(new Dimension(Constants.COMMON_VIEW_WIDTH, Constants.COMMON_VIEW_HEIGHT));
		commonViewFrame.setResizable(false);
		commonViewFrame.setLocationRelativeTo(null);
		commonViewFrame.setLayout(null);
		commonViewFrame.setVisible(true);
		commonViewFrame.setAlwaysOnTop(true);
		commonViewFrame.isResizable();
		   	
    	JPanel phaseLabelViewPanel = new JPanel();
        phaseLabelViewPanel.setBounds(10, 15, commonViewFrame.getSize().width - 40, 25);
        phaseLabelViewPanel.setBackground(Color.lightGray);
        phaseLabelViewPanel.setBorder(Constants.blackline);
        commonViewFrame.add(phaseLabelViewPanel);
        
        
        JPanel loggerLabelViewPanel = new JPanel();
        loggerLabelViewPanel.setBounds(10, 76, commonViewFrame.getSize().width - 40, 20);
        loggerLabelViewPanel.setBackground(Color.lightGray);
        loggerLabelViewPanel.setBorder(Constants.blackline);
        commonViewFrame.add(loggerLabelViewPanel);
        
        
        loggerLabel=new JLabel("Current Phase Logs");
        loggerLabel.setSize(loggerLabel.getPreferredSize().width+100, loggerLabel.getPreferredSize().height);
        loggerLabel.setLocation((commonViewFrame.getSize().width-200)/2, 76);
        loggerLabel.setFont(new Font("dialog", 1, 15));
        loggerLabel.setVisible(true);
        loggerLabelViewPanel.setOpaque(false);
        commonViewFrame.add(loggerLabel);             
        loggerLabelViewPanel.repaint();
		
        
        
	    phaseViewLabel=new JLabel("Phase View");
		phaseViewLabel.setFont(new Font("dialog", 1, 15)); 
		phaseViewLabel.setBounds((commonViewFrame.getSize().width-200)/2, 5, phaseViewLabel.getPreferredSize().width + 500, phaseViewLabel.getPreferredSize().height);
		phaseViewLabel.setVisible(true);
		phaseLabelViewPanel.add(phaseViewLabel);
		repaint();
		
		loggerText=new JTextArea();
		DefaultCaret caret = (DefaultCaret)loggerText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane logScrollPane=new JScrollPane(loggerText);
		logScrollPane.setBounds(10, 95, commonViewFrame.getSize().width - 40, ((commonViewFrame.getHeight())/2)-150);
		loggerText.setBorder(Constants.blackline);
		commonViewFrame.add(logScrollPane);	
			        
		Arrays.stream(loggerList.toArray()).forEach(i ->viewLogger=viewLogger+i+"\r\n" );		
		loggerText.setText(viewLogger);
    	initUI();
    	new WorldDominationView(commonViewFrame);
        
       		
	}
	
	
	public void initUI() 
	{
	
		
		playerScrollPane=createTable();
		playerScrollPane.setBounds(10, 40, commonViewFrame.getSize().width - 40, 35);
		commonViewFrame.add(playerScrollPane);
	
	
	
	}
	
	 public JScrollPane createTable() {
	    	String playerName;
			String[] columnNames = { "", ""};
			
			if(currentPlayer==null)
				playerName="N/A";
			else
				playerName=currentPlayer.getPlayerName()+"-"+currentPlayer.getStrategy().getStrategyName();
			Object[][] data = {
					{"Game Phase",currentPhase.name()},
					{"Current Player",playerName},
				
								
			};
			
			
			JTable table = new JTable(data, columnNames);
			
			//table.setRowHeight(2,100);
			table.setTableHeader(null);
			table.setPreferredScrollableViewportSize(new Dimension(500, 30));
			table.setFillsViewportHeight(true);
			table.setEnabled(false);
			JScrollPane scrollPane = new JScrollPane(table);

			return (scrollPane);
	    	
	    }


	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if(arg.toString().equalsIgnoreCase("player")) {
			try {
				Field playerField = o.getClass().getDeclaredField("currentPlayer");
				playerField.setAccessible(true);
				this.currentPlayer = (Player) (playerField.get(o));
				commonViewFrame.remove(playerScrollPane);
				initUI();
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				
				e.printStackTrace();
			}
		}
		
		
		else if(arg.toString().contains("phase")) {
		try {
			Field playerField = o.getClass().getDeclaredField("currentPlayer");
			playerField.setAccessible(true);
			Field phaseField = o.getClass().getDeclaredField("currentPhase");
			phaseField.setAccessible(true);
			this.currentPlayer = (Player) (playerField.get(o));
			this.currentPhase = ((Phases) (phaseField.get(o)));
			commonViewFrame.remove(playerScrollPane);
			initUI();
			this.loggerText.setText("");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			
			e.printStackTrace();
		}
		}
		else if(arg.toString().contains("logger")) {
			try {
			Field loggerField = o.getClass().getDeclaredField("viewLogger");
			loggerField.setAccessible(true);
			this.loggerList = ((ArrayList<String>) (loggerField.get(o)));
			this.loggerText.setText(this.loggerText.getText()+"\r\n"+this.loggerList.get(loggerList.size()-1));
			}
			catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				
				e.printStackTrace();
			}
			
		}
		
		}
			
	
	}

	

