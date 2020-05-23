package main;

import assets.register.cpu.CPU_X86;
import assets.register.instruction.MarkRegister;
import assets.register.instruction.Register;
import gui.GameGUI;
import puzzles.PuzzleSimpleOscillatingValue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Computer {
	
	public GameGUI gui;
	public boolean gameRunning;
	public CPU_X86 cpu_one;
	public PuzzleSimpleOscillatingValue puzzle;
	public int cpu_cycle;
	public boolean runFast;
	public boolean interrupted;
	public boolean solutionIncorrect;
	
	public int currentLine;
	public int endOfLoopLine;
	public String[] instruction_lines;
	
	public Thread gameThread;
	
	public Computer() {
		this.gameRunning = true;
		this.interrupted = false;
		this.cpu_one = new CPU_X86();
		this.puzzle = new PuzzleSimpleOscillatingValue();
		this.cpu_cycle = 0;
		this.currentLine = 0;
		this.gameThread = new Thread();
		this.solutionIncorrect = false;
	}
	
	/*
	 * Start the game by activating the GUI and set gameRunning to true.
	 */
	public void startGame(Computer computer) {
		boolean guirunning = false;
		
		// Set up a new graphical user interface for the game
		while (this.gameRunning == true) {
			if (guirunning == false) {
				this.gui = new GameGUI(this);				
				guirunning = true;
			}
		}
	}
	
	public void retrieveUserInstruction(String codetorun, GameGUI gameGUI, boolean singleLine, boolean runFast) {	
		this.instruction_lines = codetorun.split("\n");
		this.runFast = runFast;

		if (singleLine == true && this.cpu_cycle < 1000) {
			runSingleLineOfCode(instruction_lines);
		} else if (this.cpu_cycle < 1000)  {	
			runContinousProgramLoop(instruction_lines, gameGUI, singleLine);
		}
	}
	
	public void runContinousProgramLoop(String[] instruction_lines, GameGUI gameGUI, boolean singleLine) {
				
		while (currentLine <= instruction_lines.length && this.cpu_cycle < 1000 && this.gameRunning == true) {
			
			if (this.interrupted == true) {
				while (true) {
					try {
						Thread.sleep(5);
						if (this.interrupted == false) {
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
						
			runSingleLineOfCode(instruction_lines);
			
			try {
				if (this.runFast) {
					Thread.sleep(5);
				} else {
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public void interruptProgram() {
		this.interrupted = true;
	}
	
	public void resumeProgram() {
		this.interrupted = false;
		this.runFast = false;
	}
	
	public void resumeProgramRunFast() {
		this.interrupted = false;
		this.runFast = true;
	}
	
	public void runSingleLineOfCode(String[] instruction_lines) {
		String singleLinetoExecute = instruction_lines[currentLine];
		evaluateInstruction(singleLinetoExecute);
		
		currentLine++;
		if (this.currentLine == instruction_lines.length) {
			this.currentLine = 0;
		}
		
		this.cpu_cycle++;
		this.gui.displayOutputValue();
		this.gui.displayValueCpuRegisters();
		compareResults();
	}
	
	
	
	public void evaluateInstruction(String codeline) {
		String[] instruction_elements = codeline.split("\\s+");
		
		switch (instruction_elements[0]) {
		
		// Basic operations
		case "MOV":
			this.cpu_one.performMove(instruction_elements);
			break;
		case "NOOP":
			break;
		case "MARK":
			this.cpu_one.recordMark(instruction_elements, this.currentLine);
			break;
		case "JMP":
			this.performJump(instruction_elements);
			break;
		case "BREAK":
			this.performBreak(instruction_elements);
			break;
		case "OUT":
			System.out.println("OUT");
			break;
			
		// Test operation	
		case "TEST":
			this.cpu_one.performTest(instruction_elements);
			break;
		
		// Math operations
		case "ADD":
			this.cpu_one.performAddition(instruction_elements);
			break;
		case "SUB":
			this.cpu_one.performSubtraction(instruction_elements);
			break;
		case "MUL":
			this.cpu_one.performMultiplication(instruction_elements);
			break;
		case "DIV":
			this.cpu_one.performDivision(instruction_elements);
			break;
		case "REM":
			this.cpu_one.performRemainder(instruction_elements);
			break;
		case "SWP":
			this.cpu_one.performSwap(instruction_elements);
			break;	
		
		// File and memory operations
		case "FCH":
			this.performFetch();
			break;
		case "SEEK":
			System.out.println("SEEK");
			break;
		case "VOID":
			System.out.println("VOID");
			break;
		case "DROP":
			System.out.println("DROP");
			break;
		case "WIPE":
			System.out.println("WIPE");
			break;
			
		// Network operations
		case "CONN":
			System.out.println("CONN");
			break;
		case "DCON":
			System.out.println("DCON");
			break;	
		}
	}
	
	public void performBreak(String[] instruction_elements) {
		if (instruction_elements.length == 1 && instruction_elements[0].contains("BREAK"))  {
			if (this.getCPU().getBooleanRegister().getValueBoolean() == true) {
				for (int jump : this.cpu_one.getJumpList()) {
					if (jump > this.currentLine) {
						this.currentLine = jump;
						break;
					}
				}
				 this.getCPU().getBooleanRegister().setValue("false");	
			}
		}		
	}
	

	public void performJump(String[] instruction_elements) {
		if (instruction_elements.length == 2) {
			ArrayList<MarkRegister> mark_registers = this.cpu_one.getMarkList();
			for (Register mark : mark_registers) {
				if (mark.getRegisterName().contentEquals(instruction_elements[1])) {
					this.currentLine = mark.getValueInt()-1;
				}
			}
		}
	}
	
	public void setAllJumpLines(String all_lines) {	
		int count = 0;
		
		ArrayList<Integer> list_of_jump_lines = new ArrayList<Integer>();
	
		String[] lines_to_check = all_lines.split("\n");
		for (String line : lines_to_check) {
			String[] instruction = line.split("\\s+");
			
			if (instruction[0].contentEquals("MARK")) {
				this.cpu_one.recordMark(instruction, count);
			}
			
			if (instruction[0].contentEquals("JMP")) {
				list_of_jump_lines.add(count);
			}
			count++;
		}
		this.cpu_one.setJumpList(list_of_jump_lines);
		
	}
	
	public void performFetch() {
		// Fetch a file
	}

	public void resetComputerState() {
		
	}
	
	public void pauseComputer() {
		
	}
	
	public ArrayList<Register> retrieveCurrentValueCPUs() {
		return this.cpu_one.getRegisters();
	}
	
	public void compareResults() {	
		if (this.puzzle.getValue(this.cpu_cycle).contentEquals(this.cpu_one.getValueRegisters())) {
			if (this.cpu_cycle == 999 && this.solutionIncorrect == false) {
				this.gui.displayPuzzleOutcome("Puzzle solved");
			} 
		} else {
			this.solutionIncorrect = true;
			if (this.cpu_cycle > 99) {
				this.gui.displayPuzzleOutcome("Game Over!");
				this.gameRunning = false;
			} 
		}
	}
	
//	public void updatePuzzleResult() {
//        if (Files.exists(Paths.get("results/results.txt"))) {
//        	try {
//				List<String> results = Files.readAllLines(Paths.get("results/results.txt"), StandardCharsets.UTF_8);	
//				int index = 0;
//				for (String result : results) {
//					if (result.contains("One")) {
//						
//					}
//
//					index++;
//				}
//			
//			} catch (IOException e) {
//				e.printStackTrace();
//			}  	
//        } 
//	}
	
	public CPU_X86 getCPU() {
		return this.cpu_one;
	}
	
	public int getCPUCycleCount() {
		return this.cpu_cycle;
	}
	
	public void resetComputer() {
		this.cpu_one = new CPU_X86();
		this.puzzle = new PuzzleSimpleOscillatingValue();
		this.cpu_cycle = 0;
		this.currentLine = 0;
		this.gameRunning = true;
	}
	
	public int getCurrentLine() {
		return this.currentLine;
	}
}
