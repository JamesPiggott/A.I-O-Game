package puzzles;

import register.files.FileOperations;

public interface Puzzle {
	
	public String getValue(int cycle);
	
	public FileOperations getFile();
	
	public String getDescription();
	
	public boolean checkResult();

}
