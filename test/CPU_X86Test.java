

import org.junit.Test;
import assets.register.cpu.CPU_X86;

public class CPU_X86Test {

	@Test
	// Test if global variables contains 2 register and no loop locations
	public void test() {
		
		CPU_X86 x86 = new CPU_X86();
		
		assert(x86.mark_list.isEmpty());
		assert(x86.getRegisters().size() == 2);
		assert(x86.getRegisters().get(0).getRegisterName().contentEquals("x"));
		assert(x86.getRegisters().get(1).getRegisterName().contentEquals("y"));

	}

}
