package register.instruction;

public class GeneralPurposeRegister implements Register {
	
	public int value;
	public String register_name;
	
	public GeneralPurposeRegister(String register_name) {
		this.value = 0;
		this.register_name = register_name;
	}
	
	public void setValue(String value) {
		this.value = Integer.parseInt(value);
	}
	
	public String getValue() {
		return "" + this.value;
	}
	
	public int getValueInt() {
		return this.value;
	}

	@Override
	public String getRegisterName() {
		return this.register_name;
	}

	@Override
	public void setValue(boolean value) {
		// TODO Auto-generated method stub
		
	}

}
