package assets.register.cpu;

import java.util.ArrayList;

import assets.register.instruction.BooleanRegister;
import assets.register.instruction.GeneralPurposeRegister;
import assets.register.instruction.Register;

public class CPU_X86 implements CPU {
	
	public ArrayList<Register> register_list;
	public ArrayList<String> mark_list;
	public BooleanRegister boolean_register;
	
	public CPU_X86() {
		register_list = new ArrayList<Register>();
		GeneralPurposeRegister register_one = new GeneralPurposeRegister("x");
		GeneralPurposeRegister register_two = new GeneralPurposeRegister("y");
		this.boolean_register = new BooleanRegister();
		this.register_list.add(register_one);
		this.register_list.add(register_two);
		this.mark_list = new ArrayList<String>();
	}
	
	public void setRegisterValue(int register_value) {
		if (register_value < -99999) {
			this.register_list.get(0).setValue("-99999");
		} else if (register_value > 99999) {
			this.register_list.get(0).setValue("99999");
		} else {
			this.register_list.get(0).setValue("" + register_value);
		}
	}
	
	public void performAddition(String[] code_line) {
		int addition_value = Integer.parseInt(code_line[1]);
		if (addition_value > -99999 && addition_value < 99999) {
			int register_value = Integer.parseInt(register_list.get(0).getValue());
			register_value = register_value + addition_value;
			setRegisterValue(register_value);
		}
	}
	
	public void performSubtraction(String[] code_line) {
		int subtraction_value = Integer.parseInt(code_line[1]);
		if (subtraction_value > -99999 && subtraction_value < 99999) {
			int register_value = Integer.parseInt(register_list.get(0).getValue());
			register_value = register_value - subtraction_value;
			setRegisterValue(register_value);
		}
	}
	
	public void performPower(String[] code_line) {
		int power_value = Integer.parseInt(code_line[1]);
		if (power_value > -99999 && power_value < 99999) {
			int register_value = Integer.parseInt(register_list.get(0).getValue());
			register_value = register_value * power_value;
			setRegisterValue(register_value);
		}
	}
	
	public void performRemainder(String[] code_line) {
		int remainder_value = Integer.parseInt(code_line[1]);
		if (remainder_value > -99999 && remainder_value < 99999) {
			int register_value = Integer.parseInt(register_list.get(0).getValue());
			register_value = register_value % remainder_value;
			setRegisterValue(register_value);
		}		
	}
	
	public void performDivision(String[] code_line) {
		int division_value = Integer.parseInt(code_line[1]);
		if (division_value > -99999 && division_value < 99999) {
			int register_value = Integer.parseInt(register_list.get(0).getValue());
			register_value = register_value / division_value;
			setRegisterValue(register_value);
		}	
	}
	
	public void performSwap(String[] code_line) {
		int firstRegister = 0;
		int secondRegister = 0;
		
		if (code_line[1].contains("x") || code_line[1].contains("y") && code_line[2].contains("x") || code_line[2].contains("y")) {
			if (code_line[1].contains("x")) {
				firstRegister = this.getRegisters().get(0).getValueInt();
			} else {
				secondRegister = this.getRegisters().get(1).getValueInt();
			}
			if (code_line[2].contains("x")) {
				firstRegister = this.getRegisters().get(0).getValueInt();
			} else {
				secondRegister = this.getRegisters().get(1).getValueInt();
			}			
		}
		
		if (firstRegister > -99999 && firstRegister < 99999 && secondRegister > -99999 && secondRegister < 99999) {
			register_list.get(0).setValue("" + secondRegister);
			register_list.get(1).setValue("" + firstRegister);
		}	
	}
	
	/** 
	 * Move value from one register to another
	 * @param code_line
	 */
	public void performMove(String[] code_line) {
		String register_from_name = code_line[1];
		String register_to_name = code_line[2];
		
		boolean register_from_exists = false;
		boolean register_to_exists = false;
		
		Register register_from = null;
		for (Register register : this.register_list) {
			if (register.getRegisterName().equals(register_from_name)) {
				register_from_exists = true;
				register_from = register;
			}
		}
		
		Register register_to = null;
		for (Register register : this.register_list) {
			if (register.getRegisterName().equals(register_to_name)) {
				register_to_exists = true;
				register_to = register;
			}
		}
		
		if (register_from_exists == true && register_to_exists == true) {
			register_to.setValue(register_from.getValue());
		} else if (register_from_exists == false && register_to_exists == true) {
			int move_value = Integer.parseInt(code_line[1]);
			if (move_value > -99999 && move_value < 99999) {
				if (code_line[2].contains("x")) {
					this.getRegisters().get(0).setValue("" + move_value);
				} else {
					this.getRegisters().get(1).setValue("" + move_value);
				}		
			}
		}
	}
	
	/**
	 * performTest will evaluate both the numerical and alphabetical order. 
	 * In case of a reference to a register it is the value stored that will be evaluated.
	 * @param instruction_elements
	 */
	public void performTest(String[] instruction_elements) {
		if (instruction_elements.length == 4 && (instruction_elements[2].contains(">") || instruction_elements[2].contains("<") || instruction_elements[2].contains("="))) {
			// Check if argument 1 and 3 are legitimate values or registers
			String operand = instruction_elements[2];
			
			int one = 0;
			int two;
			
			if (instruction_elements[1].contains("x")) {
				one = this.getRegisters().get(0).getValueInt();
			}
			
			if (instruction_elements[1].contains("y")) {
				one = this.getRegisters().get(1).getValueInt();
			}
			

			
//			if (instruction_elements[1].matches("^[a-zA-Z]*$") && instruction_elements[3].matches("^[a-zA-Z]*$")) {
//				// set register to true of false
//			} else if(instruction_elements[1].matches("^[0-9]*$") && instruction_elements[3].matches("^[0-9]*$")) {
				// set register to true or false
				two = Integer.parseInt(instruction_elements[3]);
				
				if (one >= -9999 && one <= 9999 && two >= -9999 && two <= 9999) {
					if (operand.contentEquals(">")) {
						if (one > two) {
							this.getBooleanRegister().setValue(true);
						} else {
							this.getBooleanRegister().setValue(false);
						}
					} else if (operand.contentEquals("<")) {
						if (one < two) {
							this.getBooleanRegister().setValue(true);
						} else {
							this.getBooleanRegister().setValue(false);
						}		
					} else {
						System.out.println("One: " + one + " two: " + two);
						if (one == two) {
							System.out.println("If they are equal!");
							this.getBooleanRegister().setValue(true);
						} else {
							this.getBooleanRegister().setValue(false);
						}
					}
				}
				
				
//			} else {
//				this.getBooleanRegister().setValue(false);
//			}
		}
		
	}
	
	public void recordMark(String[] code_line) {
		if (code_line.length == 2) {
			this.mark_list.add(code_line[1]);
		}	
	}
	
	public ArrayList<String> getMarkList() {
		return mark_list;
	}

	@Override
	public String getValueRegisters() {
		return register_list.get(0).getValue();
	}
	
	public ArrayList<Register> getRegisters() {
		return this.register_list;	
	}
	
	public BooleanRegister getBooleanRegister() {
		return this.boolean_register;	
	}
}
