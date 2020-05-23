package puzzles;

public class PuzzleSimpleOscillatingValue implements Puzzle {
	
	public static int puzzleNumber = 1;
	
	private int min_cpu_cycle = 99;
	private int max_cpu_cycle = 999;

	public PuzzleSimpleOscillatingValue() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getValue(int cycle) {
		int value = cycle % 2;
		return "" + value;
	}
	
	public String getDescription() {
		String description = "This is a simple introductory puzzle. Use the programming language to create an application that places values in the 'x' register"
							+ " that oscilliate between 0 and 1. You have a 100 cpu cycle grace period. Afterward, up to and including cycle 999 must conform to specs";
		return description;
	}
	
	public int getMinCpuCycles() {
		return this.min_cpu_cycle;
	}
	
	public int getMaxCpuCycles() {
		return this.max_cpu_cycle;
	}
}