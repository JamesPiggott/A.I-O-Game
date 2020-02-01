package assets.register.instruction;

public class MarkRegister implements Register {
	
	public String value;
	public String register_name;
	
	/**
	 * This register stores the location of all MARK points. As it holds the name and the line number the corresponding JMP command can be leveraged to jump the program to a specific line.
	 * It is intended that all MARK locations be resolved before the program starts, that is the list of all MARKs is know before the program starts iterating.
	 * @param register_name
	 */
	public MarkRegister(String jump_name, int line_number) {
		this.register_name = jump_name;
		this.value = "" + line_number;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String getRegisterName() {
		return this.register_name;
	}

	@Override
	public int getValueInt() {
		return Integer.parseInt(this.value);
	}

	@Override
	public void setValue(boolean value) {
		// TODO Auto-generated method stub
		
	}

}
