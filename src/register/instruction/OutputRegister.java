package register.instruction;

public class OutputRegister implements Register {

	public String value;
	public String register_name;
	
	/**
	 * This is not really Register, but it instead simulates standard output. During a compute cycle a value can be written to output which is afterwards displayed by the Computer. At the end of the cycle Output is cleared. 
	 * @param register_name
	 */
	public OutputRegister(String register_name) {
		this.value = "";
		this.register_name = register_name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return "" + this.value;
	}

	@Override
	public String getRegisterName() {
		return this.register_name;
	}

	@Override
	public int getValueInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setValue(boolean value) {
		// TODO Auto-generated method stub
		
	}

	
}
