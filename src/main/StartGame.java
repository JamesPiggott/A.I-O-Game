package main;

public class StartGame {
	
	public static void main(String[] args) {
		StartGame game  = new StartGame();
		game.startGame();	
	}
	
	public void startGame() {
		Computer computer = new Computer();
		computer.startGame(computer);
	}
}
