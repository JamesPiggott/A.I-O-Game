package puzzles;

public class PuzzleSimpleFileOperations implements Puzzle {
	
	private int max_cpu_cycle = 999;

	@Override
	public String getValue(int cycle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		String description = "Obtain a handle to the file using the fetch instruction. Afterwards iterate through the file until you find the keyword"
				+ " morpheus. Change this to hades and release the handle to the file.";
		return description;
	}
	
	public int getMaxCpuCycles() {
		return this.max_cpu_cycle;
	}

	@Override
	public boolean checkResult() {
		// TODO Auto-generated method stub
		return false;
	}

}
