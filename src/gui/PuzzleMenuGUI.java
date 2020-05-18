package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class PuzzleMenuGUI {
	
	private GameGUI gameGUI;
	private String[] puzzleNames = { "One", "Two", "Three", "Four", "Five" };
	
	public PuzzleMenuGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	public void buildPuzzleMenu() {
		JButton goBackButton = new JButton("Back");
		goBackButton.setPreferredSize(new Dimension(300, 150));
		goBackButton.setBackground(GUIMarkUp.buttonColor);
        goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwitchFromPuzzleToMainMenu(e);
			}
        });
		
        JButton goToGameButton = new JButton("Select Puzzle");
        goToGameButton.setPreferredSize(new Dimension(300, 150));
        goToGameButton.setBackground(GUIMarkUp.buttonColor);
        goToGameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SwitchToGameGUI(e);
        	}
        });
        
        JComboBox<?> puzzles = new JComboBox<Object>(puzzleNames);
        puzzles.setPreferredSize(new Dimension(300, 30));
        puzzles.setBackground(GUIMarkUp.buttonColor);
        puzzles.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selectPuzzle(e);
        	}
        });
        
		GridBagConstraints outputPanelConstraints = new GridBagConstraints();
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 0;
		this.gameGUI.puzzlemenu.add(goBackButton, outputPanelConstraints);
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 1;
		this.gameGUI.puzzlemenu.add(goToGameButton, outputPanelConstraints);
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 2;
		this.gameGUI.puzzlemenu.add(puzzles, outputPanelConstraints);
        

        this.gameGUI.puzzlemenu.setBackground(this.gameGUI.backgroundColor);
	}
	
	private void selectPuzzle(java.awt.event.ActionEvent evt) {
	    JComboBox<?> cb = (JComboBox<?>) evt.getSource();
	    String name = (String)cb.getSelectedItem();
	    this.gameGUI.puzzleName = name;
	}
	
	private void SwitchFromPuzzleToMainMenu(java.awt.event.ActionEvent evt) {
		this.gameGUI.puzzlemenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.mainmenu);
		this.gameGUI.mainmenu.setVisible(true);
		this.gameGUI.validate();
	} 
	
	private void SwitchToGameGUI(java.awt.event.ActionEvent evt) {
		if (this.gameGUI.puzzleName == "") {
			this.gameGUI.puzzleName = puzzleNames[0];
		}
		
		this.gameGUI.setCodeBox();
		this.gameGUI.puzzlemenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.gamegui);
		this.gameGUI.gamegui.setVisible(true);
	    this.gameGUI.validate();
	}
}
