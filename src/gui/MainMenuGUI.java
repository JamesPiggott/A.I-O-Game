package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
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
        goToGameButton.setPreferredSize(new Dimension(300, 150));
        goToGameButton.setBackground(GUIMarkUp.buttonColor);
        goToGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToPuzzleMenu(e);
          }
        });

        
        JButton goToSettingButton = new JButton("Settings");
        goToSettingButton.setPreferredSize(new Dimension(300, 150));
        goToSettingButton.setBackground(GUIMarkUp.buttonColor);
        goToSettingButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToSettingsMenu(e);
          }
        });
        
        JButton quitGameButton = new JButton("Quit Game");
        quitGameButton.setPreferredSize(new Dimension(300, 150));
        quitGameButton.setBackground(GUIMarkUp.buttonColor);
        quitGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  quitGame();
          }
        });
        
		GridBagConstraints outputPanelConstraints = new GridBagConstraints();
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 0;
		this.gameGUI.mainmenu.add(goToGameButton, outputPanelConstraints);
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 1;
		this.gameGUI.mainmenu.add(goToSettingButton, outputPanelConstraints);
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 2;
		this.gameGUI.mainmenu.add(quitGameButton, outputPanelConstraints);

        this.gameGUI.mainmenu.setBackground(this.gameGUI.backgroundColor);
	}
	
	private void SwitchToPuzzleMenu(java.awt.event.ActionEvent evt) {
		this.gameGUI.mainmenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.puzzlemenu);
		this.gameGUI.puzzlemenu.setVisible(true);
	    this.gameGUI.validate();
	}
	
	private void SwitchToSettingsMenu(java.awt.event.ActionEvent evt) {
		this.gameGUI.mainmenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.settingsmenu);
		this.gameGUI.settingsmenu.setVisible(true);
		this.gameGUI.validate(); 
	}
	
	private void quitGame() {
		System.exit(0);
	}

}
