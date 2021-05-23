package main;

import register.cpu.CPU;

/**
 * Class ComputerArchitecture defines the general characteristics of the computer architecture: CPU type, amount of RAM etc.
 * Declares whether it has 1 or more CPUs, can run lines or scripts simultaneously.
 */

public class ComputerArchitecture {
	
    private CPU processor;
    private int ram_amount;
    private boolean simaltaneous_instructions;
    private boolean simaltaneous_scripts;

    public ComputerArchitecture() {

    }

    public CPU getProcessor() {
        return processor;
    }

    public void setProcessor(CPU processor) {
        this.processor = processor;
    }

    public int getRam_amount() {
        return ram_amount;
    }

    public void setRam_amount(int ram_amount) {
        this.ram_amount = ram_amount;
    }

    public boolean isSimaltaneous_instructions() {
        return simaltaneous_instructions;
    }

    public void setSimaltaneous_instructions(boolean simaltaneous_instructions) {
        this.simaltaneous_instructions = simaltaneous_instructions;
    }

    public boolean isSimaltaneous_scripts() {
        return simaltaneous_scripts;
    }

    public void setSimaltaneous_scripts(boolean simaltaneous_scripts) {
        this.simaltaneous_scripts = simaltaneous_scripts;
    }

}
