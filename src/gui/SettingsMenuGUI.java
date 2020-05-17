package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class SettingsMenuGUI {
	
	private GameGUI gameGUI;
	
	public SettingsMenuGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	public void buildSettingsMenu() {
        JButton goToMainMenuButton = new JButton("Main Menu");
        goToMainMenuButton.setPreferredSize(new Dimension(300, 150));
        goToMainMenuButton.setBackground(GUIMarkUp.buttonColor);
        goToMainMenuButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchFromSettingsToMainMenu(e);
          }
        });
        this.gameGUI.settingsmenu.add(goToMainMenuButton);
        
        JButton toggleScreen = new JButton("Toggle fullscreen / windowed");
        toggleScreen.setPreferredSize(new Dimension(300, 150));
        toggleScreen.setBackground(GUIMarkUp.buttonColor);
        toggleScreen.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  toggleScreenSize(e);
          }
        });
        this.gameGUI.settingsmenu.add(goToMainMenuButton);
        
		GridBagConstraints outputPanelConstraints = new GridBagConstraints();
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 0;
		this.gameGUI.settingsmenu.add(goToMainMenuButton, outputPanelConstraints);
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 1;
		this.gameGUI.settingsmenu.add(toggleScreen, outputPanelConstraints); 
        this.gameGUI.settingsmenu.setBackground(this.gameGUI.backgroundColor);
	}
	
	private void SwitchFromSettingsToMainMenu(java.awt.event.ActionEvent evt) {
		this.gameGUI.settingsmenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.mainmenu);
		this.gameGUI.mainmenu.setVisible(true);
	    this.gameGUI.validate();
	}
	
	private void toggleScreenSize(java.awt.event.ActionEvent evt) {  
		this.gameGUI.setUndecorated(false);
		this.gameGUI.setIgnoreRepaint(true);
		this.gameGUI.setResizable(false);
		this.gameGUI.setVisible(true);
		this.gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameGUI.setSize(new Dimension(1000,800));
	}

}
