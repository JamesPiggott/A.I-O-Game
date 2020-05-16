package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainMenuGUI {
	
	private GameGUI gameGUI;
	
	public MainMenuGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	public void buildMainMenu() {
        JButton goToGameButton = new JButton("Start Game");
        goToGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToPuzzleMenu(e);
          }
        });
        this.gameGUI.mainmenu.add(goToGameButton);
        
        JButton goToSettingButton = new JButton("Settings");
        goToSettingButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToSettingsMenu(e);
          }
        });
        this.gameGUI.mainmenu.add(goToSettingButton);
        
        JButton quitGameButton = new JButton("Quit Game");
        quitGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  quitGame();
          }
        });
        this.gameGUI.mainmenu.add(quitGameButton);
        this.gameGUI.mainmenu.setBackground(this.gameGUI.backgroundColor);
	}
	
	private void SwitchToPuzzleMenu(java.awt.event.ActionEvent evt) {
		this.gameGUI.mainmenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.puzzlemenu); //adds desired panel to frame
		this.gameGUI.puzzlemenu.setVisible(true);
	    this.gameGUI.validate(); //updates frame with new panel
	}
	
	private void SwitchToSettingsMenu(java.awt.event.ActionEvent evt) {
		this.gameGUI.mainmenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.settingsmenu); //adds desired panel to frame
		this.gameGUI.settingsmenu.setVisible(true);
		this.gameGUI.validate(); //updates frame with new panel
	}
	
	private void quitGame() {
		System.exit(0);
	}

}
