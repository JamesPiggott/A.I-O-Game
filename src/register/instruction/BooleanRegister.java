package register.instruction;


/**
 * BooleanRegister represents the boolean variable for a CPU. It can contain only the value true or false.
 * Alternatively this may be depicted as the value 0 and 1 in a GeneralPurposeRegister.
 * @author Kane
 *
 */
public class BooleanRegister implements Register {
	
	private boolean value;
	
	public BooleanRegister() {
		this.value = false;
	}

	@Override
	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return "" + this.value;
	}
	
	public Boolean getValueBoolean() {
		return this.value;
	}

	@Override
	public String getRegisterName() {
		return "Boolean";
	}

	@Override
	public int getValueInt() {
		if (this.value == false) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public void setValue(String string) {
		// TODO Auto-generated method stub
		
	}
}
