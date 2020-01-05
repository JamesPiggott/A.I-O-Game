package main;

import assets.register.cpu.CPU_X86;
import assets.register.instruction.Register;
import gui.GameGUI;
import puzzles.PuzzleSimpleOscillatingValue;

import java.util.ArrayList;

public class Computer {
	
	public GameGUI gui;
	public boolean gameRunning;
	public CPU_X86 cpu_one;
	public PuzzleSimpleOscillatingValue puzzle;
	public int cpu_cycle;
	public boolean runFast;
	
	public int currentLine;
	public int endOfLoopLine;
	public String[] instruction_lines;
	
	public Computer() {
		this.gameRunning = true;
		this.cpu_one = new CPU_X86();
		this.puzzle = new PuzzleSimpleOscillatingValue();
		this.cpu_cycle = 1;
		this.currentLine = 0;
	}
	
	public void runOneGameCycle() {
		boolean guirunning = false;
		
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

		if (singleLine == true) {
//			System.out.println("Run single Line: " + this.cpu_cycle);
			runContinousProgramLoop(instruction_lines, gameGUI, singleLine);
			this.cpu_cycle++;
		} else {
//			System.out.println("Run fast");
			while (this.cpu_cycle <= 100) {
				runContinousProgramLoop(instruction_lines, gameGUI, singleLine);
				this.cpu_cycle++;
			}
		}
	}
	
	public void runContinousProgramLoop(String[] instruction_lines, GameGUI gameGUI, boolean singleLine) {
//		this.currentLine = 0;
		if (singleLine != true) {
			while (currentLine < instruction_lines.length) {
				
				if (this.runFast == false) {
//					try {
////						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
				
				String singleLinetoExecute = instruction_lines[currentLine];
				evaluateInstruction(singleLinetoExecute);
				currentLine++;
							
			}
		} else {
//			System.out.println(this.currentLine);
			String singleLinetoExecute = instruction_lines[currentLine];
			evaluateInstruction(singleLinetoExecute);
			currentLine++;
			if (this.currentLine == instruction_lines.length) {
				this.currentLine = 0;
			}
		}
		
		this.gui.displayOutputValue();
		this.gui.displayValueCpuRegisters();
		compareResults();
	}
	
	public void evaluateInstruction(String codeline) {
		String[] instruction_elements = codeline.split("\\s+");
		
		switch (instruction_elements[0]) {
		case "MOV":
			this.cpu_one.performMove(instruction_elements);
			break;
		case "NOOP":
			break;
		case "MARK":
			this.cpu_one.recordMark(instruction_elements);
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
		case "PWR":
			this.cpu_one.performPower(instruction_elements);
			break;
		case "REM":
			this.cpu_one.performRemainder(instruction_elements);
			break;
		case "DIV":
			this.cpu_one.performDivision(instruction_elements);
			break;
		case "SWP":
			this.cpu_one.performSwap(instruction_elements);
			break;
			
			
		case "FCH":
			System.out.println("FCH");
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
		}
	}
	
	public void performBreak(String[] instruction_elements) {
		
		if (instruction_elements.length == 1 && instruction_elements[0].contains("BREAK") && this.getCPU().getBooleanRegister().getValueInt() == 1) {
			this.currentLine = this.endOfLoopLine;
			System.out.println("Perform BREAK");
		}
		 this.getCPU().getBooleanRegister().setValue("false");	
		 this.forbidden = true;
	}
	
	public boolean forbidden = false;
	
	public void performJump(String[] instruction_elements) {
		System.out.println("Perform JUMP");
		if (this.forbidden == true) {
			this.endOfLoopLine = this.currentLine;
			this.endOfLoopLine++;
			int count = 0;
			if (instruction_elements.length == 2) {
				for (String mark : this.instruction_lines) {
					if (mark.contains(instruction_elements[1]) && mark.contains("MARK")) {
						this.currentLine = count;
						this.forbidden = false;
					} else {
						count++;
					}
				}
			}	
			this.forbidden = false;
		}
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
//			System.out.println("Values match");
		} else {
//			System.out.println("Cycle: " + this.cpu_cycle + " "  + this.puzzle.getValue(this.cpu_cycle) + " " + this.cpu_one.getValueRegisters());
		}
	}
	
	public CPU_X86 getCPU() {
		return this.cpu_one;
	}
	
	public void resetComputer() {
		this.cpu_one = new CPU_X86();
		this.puzzle = new PuzzleSimpleOscillatingValue();
		this.cpu_cycle = 1;
		this.currentLine = 0;
	}
	
	public int getCurrentLine() {
		return this.currentLine;
	}
}
