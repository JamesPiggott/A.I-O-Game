package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class SettingsMenuGUI {
	
	private GameGUI gameGUI;
	
	public SettingsMenuGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	public void buildSettingsMenu() {
        JButton goToMainMenuButton = new JButton("Main Menu");
        goToMainMenuButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchFromSettingsToMainMenu(e);
          }
        });
        this.gameGUI.settingsmenu.add(goToMainMenuButton);
        this.gameGUI.settingsmenu.setBackground(this.gameGUI.backgroundColor);
	}
	
	private void SwitchFromSettingsToMainMenu(java.awt.event.ActionEvent evt) {
		this.gameGUI.settingsmenu.setVisible(false);
		this.gameGUI.getContentPane().removeAll(); 
		this.gameGUI.getContentPane().add(this.gameGUI.mainmenu);
		this.gameGUI.mainmenu.setVisible(true);
	    this.gameGUI.validate();
	}

}
