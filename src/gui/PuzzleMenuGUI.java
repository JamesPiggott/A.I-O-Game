package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class PuzzleMenuGUI {
	
	private GameGUI gameGUI;
	
	public PuzzleMenuGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	public void buildPuzzleMenu() {
        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchFromPuzzleToMainMenu(e);
          }
        });
        this.gameGUI.puzzlemenu.add(goBackButton);
		
        JButton goToGameButton = new JButton("Select Puzzle");
        goToGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToGameGUI(e);
          }
        });
        this.gameGUI.puzzlemenu.add(goToGameButton);
        
        String[] puzzleNames = { "One", "Two", "Three", "Four", "Five" };
        JComboBox<?> puzzles = new JComboBox<Object>(puzzleNames);

        puzzles.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  selectPuzzle(e);
          }
        });
        this.gameGUI.puzzlemenu.add(puzzles);
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
		this.gameGUI.getContentPane().add(this.gameGUI.mainmenu); //adds desired panel to frame
		this.gameGUI.mainmenu.setVisible(true);
		this.gameGUI.validate(); //updates frame with new panel
	} 
	
	private void SwitchToGameGUI(java.awt.event.ActionEvent evt) {
		this.gameGUI.setCodeBox();
		this.gameGUI.puzzlemenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.gamegui); //adds desired panel to frame
		this.gameGUI.puzzlemenu.setVisible(true);
	    this.gameGUI.validate(); //updates frame with new panel
	}
}
