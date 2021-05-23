package puzzles;

import register.files.FileOperations;

public class PuzzleSimpleFileOperations implements Puzzle {
	
	private int max_cpu_cycle = 999;
	private FileOperations file;
	
	public PuzzleSimpleFileOperations() {
		this.file = new FileOperations("garbage");
		this.file.insertValue("Dade");
		this.file.insertValue("Kate");
		this.file.insertValue("Joey");
		this.file.insertValue("Emmanuel");
		this.file.insertValue("Paul");
	}
	

	@Override
	public String getValue(int cycle) {
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
		return false;
	}

	@Override
	public FileOperations getFile() {
		return this.file;
	}

}
