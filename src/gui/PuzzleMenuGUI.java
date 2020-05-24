package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;

import puzzles.PuzzleSimpleFileOperations;
import puzzles.PuzzleSimpleOscillatingValue;

public class PuzzleMenuGUI {
	
	private GameGUI gameGUI;
	private String[] puzzleNames = { "One", "Two", "Three", "Four", "Five" };
	private String[] puzzleResults = new String[5];
	
	public PuzzleMenuGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	public void buildPuzzleMenu() {
		
		getPuzzleResults();
		
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
        
        JComboBox<?> puzzles = new JComboBox<Object>(puzzleResults);
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
	    
	    if (name.contains("One")) {
	    	name = puzzleNames[0];
	    	this.gameGUI.setPuzzle(new PuzzleSimpleOscillatingValue());
	    } else if(name.contains("Two")) {
	    	name = puzzleNames[1];
	    	this.gameGUI.setPuzzle(new PuzzleSimpleFileOperations());
	    } else if(name.contains("Three")) {
	    	name = puzzleNames[2];
	    } else if(name.contains("Four")) {
	    	name = puzzleNames[3];
	    } else if(name.contains("Five")) {
	    	name = puzzleNames[4];
	    } else {
	    	name = "";
	    }    
	    
	    this.gameGUI.puzzleName = name;
	}
	
	private void getPuzzleResults() {
        if (Files.exists(Paths.get("results/results.txt"))) {
        	try {
				List<String> results = Files.readAllLines(Paths.get("results/results.txt"), StandardCharsets.UTF_8);	
				int index = 0;
				for (String result : results) {
					if (result.contains("Unsolved")) {
						puzzleResults[index] = puzzleNames[index] +  ": Unsolved";
					} else if (result.contains("Solved")) {
						puzzleResults[index] = puzzleNames[index] +  ": Solved";
					} else {
						puzzleResults[index] = puzzleNames[index] +  ": No result";
					}
					index++;
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}  	
        } 
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
