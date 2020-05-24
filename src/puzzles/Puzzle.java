package puzzles;

public interface Puzzle {
	
	public String getValue(int cycle);
	
	public String getDescription();
	
	public boolean checkResult();

}
