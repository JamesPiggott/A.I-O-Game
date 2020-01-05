package puzzles;

public class PuzzleSimpleOscillatingValue implements Puzzle {

	public PuzzleSimpleOscillatingValue() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getValue(int cycle) {
		int value = cycle % 2;
		return "" + value;
	}
}