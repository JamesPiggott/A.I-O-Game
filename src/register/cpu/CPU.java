package register.cpu;

public interface CPU {
	
	public void performAddition(String[] code_line);
	
	public void performSubtraction(String[] code_line);
	
	public void performMultiplication(String[] code_line);
	
	public void performRemainder(String[] code_line);
	
	public void performDivision(String[] code_line);
	
	public void performSwap(String[] code_line);
		
	public String getValueRegisters();
	

}
