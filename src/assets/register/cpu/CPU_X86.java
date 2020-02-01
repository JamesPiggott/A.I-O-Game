package assets.register.cpu;

import java.util.ArrayList;

import assets.register.instruction.BooleanRegister;
import assets.register.instruction.GeneralPurposeRegister;
import assets.register.instruction.MarkRegister;
import assets.register.instruction.Register;

public class CPU_X86 implements CPU {
	
	public ArrayList<Register> register_list;
	public ArrayList<MarkRegister> mark_list;
	public ArrayList<Integer> jump_list;
	public BooleanRegister boolean_register;
	
	public CPU_X86() {
		register_list = new ArrayList<Register>();
		mark_list = new ArrayList<MarkRegister>();
		GeneralPurposeRegister register_one = new GeneralPurposeRegister("x");
		GeneralPurposeRegister register_two = new GeneralPurposeRegister("y");
		this.boolean_register = new BooleanRegister();
		this.register_list.add(register_one);
		this.register_list.add(register_two);
		
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
		Register from = getRegister(code_line[1]);
		int base_value = 0;
		if (from != null) {
			base_value = from.getValueInt();
		} else {
			base_value = Integer.parseInt(code_line[1]);
		}
		
		Register addition = getRegister(code_line[2]);
		int addition_value = 0;
		if (addition != null) {
			addition_value = addition.getValueInt();
		} else {
			addition_value = Integer.parseInt(code_line[2]);
		}
		
		int sum_value = base_value + addition_value;
		
		if (sum_value > -99999 && sum_value < 99999) {
			Register to = getRegister(code_line[3]);
			if (to != null) {
				to.setValue("" + sum_value);
			}
		}
	}
	
	public void performSubtraction(String[] code_line) {
		Register from = getRegister(code_line[1]);
		int base_value = 0;
		if (from != null) {
			base_value = from.getValueInt();
		} else {
			base_value = Integer.parseInt(code_line[1]);
		}
		
		Register subtraction = getRegister(code_line[2]);
		int subtraction_value = 0;
		if (subtraction != null) {
			subtraction_value = subtraction.getValueInt();
		} else {
			subtraction_value = Integer.parseInt(code_line[2]);
		}
		
		int sub_value = base_value + subtraction_value;
		
		if (sub_value > -99999 && sub_value < 99999) {
			Register to = getRegister(code_line[3]);
			if (to != null) {
				to.setValue("" + sub_value);
			}
		}
	}
	
	public void performMultiplication(String[] code_line) {
		Register from = getRegister(code_line[1]);
		int base_value = 0;
		if (from != null) {
			base_value = from.getValueInt();
		} else {
			base_value = Integer.parseInt(code_line[1]);
		}
		
		Register multi = getRegister(code_line[2]);
		int multi_value = 0;
		if (multi != null) {
			multi_value = multi.getValueInt();
		} else {
			multi_value = Integer.parseInt(code_line[2]);
		}
		
		int mul_value = base_value * multi_value;
		
		if (mul_value > -99999 && mul_value < 99999) {
			Register to = getRegister(code_line[3]);
			if (to != null) {
				to.setValue("" + mul_value);
			}
		}
	}
	
	public void performRemainder(String[] code_line) {
		Register from = getRegister(code_line[1]);
		int base_value = 0;
		if (from != null) {
			base_value = from.getValueInt();
		} else {
			base_value = Integer.parseInt(code_line[1]);
		}
		
		Register remainder = getRegister(code_line[2]);
		int remainder_value = 0;
		if (remainder != null) {
			remainder_value = remainder.getValueInt();
		} else {
			remainder_value = Integer.parseInt(code_line[2]);
		}
		
		int rem_value = base_value % remainder_value;
		
		if (rem_value > -99999 && rem_value < 99999) {
			Register to = getRegister(code_line[3]);
			if (to != null) {
				to.setValue("" + rem_value);
			}
		}		
	}
	
	public void performDivision(String[] code_line) {
		Register from = getRegister(code_line[1]);
		int base_value = 0;
		if (from != null) {
			base_value = from.getValueInt();
		} else {
			base_value = Integer.parseInt(code_line[1]);
		}
		
		Register division = getRegister(code_line[2]);
		int division_value = 0;
		if (division != null) {
			division_value = division.getValueInt();
		} else {
			division_value = Integer.parseInt(code_line[2]);
		}
		
		int div_value = base_value / division_value;
		
		if (div_value > -99999 && div_value < 99999) {
			Register to = getRegister(code_line[3]);
			if (to != null) {
				to.setValue("" + div_value);
			}
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
	
	public Register getRegister(String name) {
		for (Register register : this.register_list) {
			if (register.getRegisterName().equals(name)) {
				return register;
			} 
		}
		return null;
	}
	
	/**
	 * performTest will evaluate both the numerical and alphabetical order. 
	 * In case of a reference to a register it is the value stored that will be evaluated.
	 * @param instruction_elements
	 */
	public void performTest(String[] instruction_elements) {
		if (instruction_elements.length == 4 && (instruction_elements[2].contains(">") || instruction_elements[2].contains("<") || instruction_elements[2].contains("="))) {

			String operand = instruction_elements[2];
			
			int one = 0;
			int two;
			
			if (instruction_elements[1].contains("x")) {
				one = this.getRegisters().get(0).getValueInt();
			}
			
			if (instruction_elements[1].contains("y")) {
				one = this.getRegisters().get(1).getValueInt();
			}
			


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
				
			

		}
		
	}
	
	public void recordMark(String[] code_line, int line_number) {
		if (code_line.length == 2) {
			MarkRegister jump = new MarkRegister(code_line[1], line_number);
			this.mark_list.add(jump);
		}	
	}
	
	public void setJumpList(ArrayList<Integer> jump_list) {
		this.jump_list = jump_list;
	}
	
	public ArrayList<Integer> getJumpList() {
		return this.jump_list;
	}
	
	public ArrayList<MarkRegister> getMarkList() {
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
