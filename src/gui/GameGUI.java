package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import register.files.FileOperations;
import register.instruction.Register;
import main.Computer;
import puzzles.Puzzle;

public class GameGUI extends JFrame {
	
	public Computer computer;
    private JTextArea codeBox;
	private JTextArea values;
    private JTextArea valuesFile;
	private ArrayList<JTextArea> textAreaRegisters;
    private JTextArea textAreaCpyCycles;
	private JTextArea description_box;
	
	public JPanel gamegui;
	public JPanel puzzlemenu;
	public JPanel mainmenu;
	public JPanel settingsmenu;
	public String puzzleName;
	public Color backgroundColor;
	public Color backgroundPanelColor;
	public Puzzle puzzle;
	public JPanel filePanel;

	private boolean started;
	
	
	// List of Panels, TextAreas and Buttons that should be easily reachable
	private JButton stepButton;
	private JButton runButton;
	private JButton runFastButton;

	public GameGUI(Computer computer) {
		
		super("Game GUI");
		this.backgroundColor = Color.DARK_GRAY;
		this.backgroundPanelColor = Color.LIGHT_GRAY;
		this.computer = computer;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setVisible(true);
		this.setResizable(true);
		
		this.gamegui = new JPanel();
		this.puzzlemenu = new JPanel(new GridBagLayout());
		this.mainmenu = new JPanel(new GridBagLayout());
		this.settingsmenu = new JPanel(new GridBagLayout());
		buildGUI();

		MainMenuGUI menu = new MainMenuGUI(this);
		menu.buildMainMenu();

		PuzzleMenuGUI puzzle = new PuzzleMenuGUI(this);
		puzzle.buildPuzzleMenu();

		SettingsMenuGUI settings = new SettingsMenuGUI(this);
		settings.buildSettingsMenu();
		
		this.add(mainmenu);
		this.puzzleName = "";
        setVisible(true);
        started = false;
        SwingUtilities.isEventDispatchThread();
	}
	
	public void buildGUI() {
		       
        // Code Panel
        JPanel codePanel = new JPanel(new GridBagLayout());
		codePanel.setBackground(this.backgroundPanelColor);
        JLabel codeBoxInformation = new JLabel("Enter code:");
     	codeBox = new JTextArea("", 15, 40);
        codeBox.setEditable(true);
//		codeBox.setBackground(this.backgroundPanelColor);
		JScrollPane pane1 = new JScrollPane(codeBox);
        GridBagConstraints codePanelConstraints = new GridBagConstraints();
        codePanelConstraints.gridx = 0;
        codePanelConstraints.gridy = 0;
        codePanel.add(codeBoxInformation, codePanelConstraints);
        codePanelConstraints.gridx = 0;
        codePanelConstraints.gridy = 1;
        codePanel.add(pane1, codePanelConstraints);
        codePanelConstraints.gridx = 1;
        codePanelConstraints.gridy = 1;
        codePanel.add(createRegisterPanel(), codePanelConstraints);
        
        // Output panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(this.backgroundPanelColor);
		JLabel value_message = new JLabel("Values:");
		this.values = new JTextArea("", 5, 40);
		this.values.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(this.values);
		GridBagConstraints outputPanelConstraints = new GridBagConstraints();
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 0;
		panel.add(value_message, outputPanelConstraints);
		outputPanelConstraints.gridx = 0;
		outputPanelConstraints.gridy = 1;
		panel.add(scrollPane, outputPanelConstraints);
		panel.setSize(300, 300);
		
        // File output panel
		this.filePanel = new JPanel(new GridBagLayout());
		filePanel.setBackground(this.backgroundPanelColor);
		JLabel file_name = new JLabel("File name:");
		file_name.setBackground(Color.WHITE);
		this.valuesFile = new JTextArea("", 5, 40);
		this.valuesFile.setEditable(false);
		JScrollPane fileScrollPane = new JScrollPane(this.valuesFile);
		GridBagConstraints filePanelConstraints = new GridBagConstraints();
		filePanelConstraints.gridx = 0;
		filePanelConstraints.gridy = 0;
		filePanel.add(file_name, filePanelConstraints);
		filePanelConstraints.gridx = 0;
		filePanelConstraints.gridy = 1;
		filePanel.add(fileScrollPane, filePanelConstraints);
		filePanel.setSize(300, 300);

		// Add each panel to the JFrame with the 'right' GridBagConstraints
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setName("Main");
		GridBagConstraints mainPanelConstraints = new GridBagConstraints();
		mainPanelConstraints.gridx = 0;
		mainPanelConstraints.gridy = 1;
		mainPanel.add(codePanel, mainPanelConstraints);
		mainPanelConstraints.gridx = 0;
		mainPanelConstraints.gridy = 2;
		mainPanel.add(panel, mainPanelConstraints);
		mainPanelConstraints.gridx = 0;
		mainPanelConstraints.gridy = 3;
		mainPanel.add(filePanel, mainPanelConstraints);
		mainPanel.setBackground(this.backgroundColor);
		
		// gameworld
		JTextArea gameWorld = new JTextArea("", 50, 100);
        gameWorld.setEditable(false);
		gameWorld.setBackground(this.backgroundPanelColor);
        JPanel gameworld_panel = new JPanel();
        gameworld_panel.add(gameWorld);
		gameworld_panel.setBackground(this.backgroundColor);
		
		// description box
        JPanel description_panel = new JPanel(new GridBagLayout());
		JLabel description = new JLabel("Description");
		this.description_box = new JTextArea("", 5, 100);
		this.description_box.setEditable(false);
		this.description_box.setLineWrap(true);
		this.description_box.setBackground(this.backgroundPanelColor);
		GridBagConstraints descriptionPanelConstraints = new GridBagConstraints();
		descriptionPanelConstraints.gridx = 0;
		descriptionPanelConstraints.gridy = 0;
		description_panel.add(description, descriptionPanelConstraints);
		descriptionPanelConstraints.gridx = 0;
		descriptionPanelConstraints.gridy = 1;
		description_panel.add(this.description_box, descriptionPanelConstraints);
		description_panel.setSize(300, 300);
		
		// Set layout of the right side
		mainPanelConstraints.gridx = 1;
		mainPanelConstraints.gridy = 1;
		mainPanel.add(gameworld_panel, mainPanelConstraints);
		mainPanelConstraints.gridx = 1;
		mainPanelConstraints.gridy = 2;
		mainPanel.add(createButtonPanel(), mainPanelConstraints);
		mainPanelConstraints.gridx = 1;
		mainPanelConstraints.gridy = 3;
		mainPanel.add(description_panel, mainPanelConstraints);
		mainPanel.setVisible(true);
		
		this.gamegui.add(mainPanel);
		this.gamegui.setBackground(this.backgroundColor);
    }
	
	public JPanel createRegisterPanel() {
		this.textAreaRegisters = new ArrayList<>();
		JPanel register_panel = new JPanel(new GridBagLayout());
		ArrayList<Register> registers = this.computer.getCPU().getRegisters();
		
		int count = 1;
		GridBagConstraints registerPanelConstraints = new GridBagConstraints();	
		for(Register register : registers) {			
			registerPanelConstraints.gridx = 0;
			registerPanelConstraints.gridy = count;
			JTextArea register_value_box = new JTextArea(" " + register.getRegisterName() + " : " + register.getValue(), 1, 5);
			this.textAreaRegisters.add(register_value_box);
			register_value_box.setEditable(false);
			register_value_box.setVisible(true);
			register_panel.add(register_value_box, registerPanelConstraints);
			count++;
		}
		
		return register_panel;
	}
	
	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
    	this.computer.setPuzzle(puzzle);
    	this.description_box.setText(this.computer.puzzle.getDescription());
	}
	
	public JPanel createButtonPanel() {
		
		// Return back to main menu
        JButton menuButton = new JButton("Menu");
        menuButton.setBackground(GUIMarkUp.buttonColor);
        menuButton.addActionListener(e -> {
			SwitchToMainMenu();
			started = false;
		});
        
        // Reset button stops a game and returns all start values
        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(GUIMarkUp.buttonColor);
        resetButton.addActionListener(e -> {
			setStepToEnabled();
			setRunToEnabled();
			resetComputer();
			started = false;
		});
        
        // Pause button interrupts a running game
        JButton pauseButton = new JButton("Pause"); 
        pauseButton.setBackground(GUIMarkUp.buttonColor);
        pauseButton.addActionListener(e -> {
			setStepToEnabled();
			setRunToEnabled();
			new Thread(this::interruptProgram).start();
		});
        
        // Advance one cycle (perform one line of code)
        stepButton = new JButton("Step");
        stepButton.setBackground(GUIMarkUp.buttonColor);
        stepButton.addActionListener(e -> {
			setRunToEnabled();
			setRunFastToEnabled();
			if (!started) {
				setAllMarkPoints(codeBox.getText());
				started = true;
			}
			sendCodetoGame(codeBox.getText(), false);
		});
        
        // Run code indefinitely, but slow enough to observe
		this.runButton = new JButton("Run");
        runButton.setBackground(GUIMarkUp.buttonColor);
        runButton.addActionListener(e -> {
			setStepToEnabled();
			setRunToNotEnabled();
			setRunFastToEnabled();
			resumeProgram(false);
			started = true;
			new Thread(() -> {
				setAllMarkPoints(codeBox.getText());
				sendCodetoGame(codeBox.getText(), true);
			}).start();
		});
        
        // Run code indefinitely, but at a much faster rate to quickly pass all tests.
		this.runFastButton = new JButton("Run Fast");
        runFastButton.setBackground(GUIMarkUp.buttonColor);
        runFastButton.addActionListener(e -> {
			setStepToEnabled();
			setRunFastToEnabled();
			setRunFastToNotEnabled();
			resumeProgram(true);
			started = true;
			new Thread(() -> {
				setAllMarkPoints(codeBox.getText());
				sendCodetoGame(codeBox.getText(), true);
			}).start();
		});
        
        // Control panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setName("buttonPanel");
        buttonPanel.add(menuButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(runButton);
        buttonPanel.add(runFastButton);
        buttonPanel.add(createResourceWidgets());
        buttonPanel.setBackground(this.backgroundColor);     
        
		return buttonPanel;
	}
	
	public JPanel createResourceWidgets() {
		JPanel resource_panel = new JPanel();
		this.textAreaCpyCycles = new JTextArea(" " + this.computer.getCPUCycleCount(), 1, 5);
		
		// CPU cycle counter
		this.textAreaCpyCycles.setEditable(false);
		this.textAreaCpyCycles.setVisible(true);
		JLabel cpu_cycle_message = new JLabel("CPU cycles:");
		resource_panel.add(cpu_cycle_message);
		resource_panel.add(this.textAreaCpyCycles);		
		return resource_panel;
	}
	
	public void setAllMarkPoints(String code) {
		this.computer.setAllJumpLines(code);
	}
	
	public void sendCodetoGame(String code, boolean runFast) {
		this.computer.retrieveUserInstruction(code, runFast);
	}

	public void displayOutputValue() {	
		highlightCodeLine(this.codeBox, this.computer);
		
		Register register = this.computer.retrieveCurrentValueCPUs().get(0);
		if (register != null) {
			String CPUValue = register.getValue();
			String output = this.values.getText() + " " + CPUValue;

			if (this.values.getFont() != null) {
				this.values.setText(output);
				this.values.updateUI();
			}
		}
		
		FileOperations fileOutput = this.computer.retrieveCurrentFileOperations();
		if (fileOutput != null) {
			JLabel file_name = (JLabel) this.filePanel.getComponent(0);
			file_name.setText("File name: " + fileOutput.getName());
			this.valuesFile.selectAll();
			this.valuesFile.replaceSelection("");

			HashMap<Integer, String> file_values = fileOutput.getValues();

			StringBuilder output = new StringBuilder();

			for(String value : file_values.values()) {
				output.append(value).append(", ");
			}

			this.valuesFile.setText(output.toString());
			this.valuesFile.updateUI();
		}
	}
	
	public void displayPuzzleOutcome(String message) {
		this.values.setText(message);
		this.values.updateUI();
	}
	
	public void interruptProgram() {
		this.computer.interruptProgram();
	}
	
	public boolean getInterrupted() {
		return this.computer.interrupted;
	}
	
	public void resumeProgram(boolean runfast) {
		if (runfast) {
			this.computer.resumeProgramRunFast();
		} else {
			this.computer.resumeProgram();
		}
	}
	
	private void setStepToNotEnabled() {
		this.stepButton.setEnabled(false);
	}
	
	private void setStepToEnabled() {                                
		this.stepButton.setEnabled(true);
	}

	private void setRunToNotEnabled() {
		this.runButton.setEnabled(false);
	}

	private void setRunToEnabled() {
		this.runButton.setEnabled(true);
	}

	private void setRunFastToNotEnabled() {
		this.runFastButton.setEnabled(false);
	}

	private void setRunFastToEnabled() {
		this.runFastButton.setEnabled(true);
	}
		
	public void highlightCodeLine( JTextArea   codeBox, Computer   computer) {
		codeBox.getHighlighter().removeAllHighlights();
		DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
		try {
            int start = codeBox.getLineStartOffset(computer.getCurrentLine());
            int end = codeBox.getLineEndOffset(computer.getCurrentLine());
			codeBox.getHighlighter().addHighlight(start, end, painter);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void displayValueCpuRegisters() {
		ArrayList<Register> registers = this.computer.retrieveCurrentValueCPUs();
		int index = 0;
		for(Register register: registers) {
			this.textAreaRegisters.get(index).setText("" + register.getValueInt());
			index++;
		}
		this.textAreaCpyCycles.setText("" + this.computer.getCPUCycleCount());
	}
	
	public void resetComputer() {
		this.computer.resetComputer(this.puzzle);
		this.values.setText("");
		this.valuesFile.setText("");
		
		ArrayList<Register> registers = this.computer.retrieveCurrentValueCPUs();
		
		int index = 0;
		for(Register register: registers) {
			this.textAreaRegisters.get(index).setText("");
			register.setValue("0");
			index++;
		}
		this.textAreaCpyCycles.setText("" + 0);
		highlightCodeLine(this.codeBox, this.computer);
	}
	
	
	private void saveGame() throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter("solutions/" + this.puzzleName + ".txt")) {
		    out.println(codeBox.getText());
		}
	}
	
	public void setCodeBox() {
        if (Files.exists(Paths.get("solutions/" + this.puzzleName + ".txt"))) {
            StringBuilder contentBuilder = new StringBuilder();
	        try (Stream<String> stream = Files.lines(Paths.get("solutions/" + this.puzzleName + ".txt"), StandardCharsets.UTF_8)) {
	            stream.forEach(s -> contentBuilder.append(s).append("\n"));  
	        }        
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        contentBuilder.delete(contentBuilder.lastIndexOf("\n"), contentBuilder.lastIndexOf("\n")+1);
	        this.codeBox.setText(contentBuilder.toString());
	        highlightCodeLine(this.codeBox, this.computer);
	        this.codeBox.setCaretPosition(0);
        } 
	}
	
	private void SwitchToMainMenu() {
		this.interruptProgram();
		this.setStepToEnabled();
		this.setRunToEnabled();
		try {
			saveGame();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.gamegui.setVisible(false);
	    getContentPane().removeAll(); 
	    getContentPane().add(this.mainmenu); 
	    this.mainmenu.setVisible(true);
	    validate(); 
	} 

}
