package register.instruction;

public interface Register {

	void setValue(String string);

	String getValue();
	
	String getRegisterName();

	int getValueInt();

	void setValue(boolean value);

}
