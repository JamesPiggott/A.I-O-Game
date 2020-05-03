package main;

//import javax.swing.SwingUtilities;

import gui.GameGUI;

public class StartGame {
	
	public static void main(String[] args) {
		StartGame game  = new StartGame();
		game.startGame();	
	}
	
	public void startGame() {
//       SwingUtilities.invokeLater(new Runnable() {
//        public void run() {
    		Computer computer = new Computer();
    		computer.startGame(computer);
//        }
//    });
		
	}
}
