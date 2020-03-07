package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import assets.register.instruction.Register;
import main.Computer;

@SuppressWarnings("serial")
public class GameGUI extends JFrame {
	
	public Computer   computer;
    private JTextArea   codeBox;
    private JScrollPane pane1;
    private JTextArea values;
    private String output;
    public Thread queryThread;
    private ArrayList<JTextArea> textAreaRegisters;
	private JTextArea gameWorld;
	private JTextArea description_box;
	
	private JPanel gamegui;
	private JPanel puzzlemenu;
	private JPanel mainmenu;
	private JPanel settingsmenu;
	private String puzzleName;
	private Color backgroundColor;
	
	private boolean started;
	
	public GameGUI(Computer computer) {
		super("Game GUI");
		this.backgroundColor = Color.DARK_GRAY;
		this.computer = computer;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setVisible(true);
		this.setResizable(true);
		
		this.gamegui = new JPanel();
		this.puzzlemenu = new JPanel();
		this.mainmenu = new JPanel();
		this.settingsmenu = new JPanel();
		buildGUI();
		buildPuzzleMenu();
		buildMainMenu();
		buildSettingsMenu();
		this.add(mainmenu);
		this.puzzleName = "";
        setVisible(true);
        started = false;
	}
	
	public void buildGUI() {
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  resetComputer();
        	  started = false;
          }
        });
        
        JButton pauseButton = new JButton("Pause"); 
        pauseButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToMainMenu(e);
        	  System.out.println("You pressed pause");  
          }
        });
        
        // Advance one cycle (perform one line of code)
        JButton stepButton = new JButton("Step");   
        stepButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  if (started == false) {
        		  setAllMarkPoints(codeBox.getText());
        		  started = true;
        	  }
        	  sendCodetoGame(codeBox.getText(), true, false);
          }
        });
        
        // Run code indefinitely, but slow enough to observe
        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  sendCodetoGame(codeBox.getText(), false, false);
          }
        });
        
        // Run code indefinitely, but at a much faster rate to quickly pass all tests.
        JButton runFastButton = new JButton("Run Fast");
        runFastButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  sendCodetoGame(codeBox.getText(), false, true);
          }
        });
        
        // Control panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stepButton);
        buttonPanel.add(runButton);
//        buttonPanel.add(runFastButton);
        buttonPanel.setBackground(this.backgroundColor);
        
        
        // Code Panel
        JPanel codePanel = new JPanel(new GridBagLayout());
        JLabel codeBoxInformation = new JLabel("Enter code:");
     	codeBox = new JTextArea("", 15, 40);
        codeBox.setEditable(true);
        this.pane1 = new JScrollPane(codeBox);
        GridBagConstraints codePanelConstraints = new GridBagConstraints();
        codePanelConstraints.gridx = 0;
        codePanelConstraints.gridy = 0;
        codePanel.add(codeBoxInformation, codePanelConstraints);
        codePanelConstraints.gridx = 0;
        codePanelConstraints.gridy = 1;
        codePanel.add(this.pane1, codePanelConstraints);
        
        // Output panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(this.backgroundColor);
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
		
		
		// Register values
		this.textAreaRegisters = new ArrayList<JTextArea>();
		JPanel register_panel = new JPanel();
		register_panel.setBackground(this.backgroundColor);
		JLabel register_message = new JLabel("Registers:");
		register_panel.add(register_message);
		ArrayList<Register> registers = this.computer.getCPU().getRegisters();
		
		int count = 0;
		GridBagConstraints registerPanelConstraints = new GridBagConstraints();
		registerPanelConstraints.gridx = 0;
		registerPanelConstraints.gridy = count;
		registerPanelConstraints.gridwidth = 1;
		
		for(Register register : registers) {
			JTextArea register_value_box = new JTextArea(" " + register.getRegisterName() + " : " + register.getValue(), 1, 5);
			this.textAreaRegisters.add(register_value_box);
			register_value_box.setEditable(false);
			register_value_box.setVisible(true);
			register_panel.add(register_value_box, registerPanelConstraints);
			count++;
		}

		
		// Add each panel to the JFrame with the 'right' GridBagConstraints
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints mainPanelConstraints = new GridBagConstraints();
		mainPanelConstraints.gridx = 0;
		mainPanelConstraints.gridy = 0;
		mainPanel.add(buttonPanel, mainPanelConstraints);
		mainPanelConstraints.gridx = 0;
		mainPanelConstraints.gridy = 1;
		mainPanel.add(codePanel, mainPanelConstraints);
		mainPanelConstraints.gridx = 0;
		mainPanelConstraints.gridy = 3;
		mainPanel.add(panel, mainPanelConstraints);
		mainPanelConstraints.gridx = 0;
		mainPanelConstraints.gridy = 2;
		mainPanel.add(register_panel, mainPanelConstraints);
		mainPanel.setBackground(this.backgroundColor);
		
		// gameworld
        this.gameWorld = new JTextArea("", 15, 25);
        this.gameWorld.setEditable(true);
        JPanel gameworld_panel = new JPanel();
        gameworld_panel.add(gameWorld);
		mainPanelConstraints.gridx = 1;
		mainPanelConstraints.gridy = 1;
		mainPanel.add(gameworld_panel, mainPanelConstraints);
		
		// description box
        JPanel description_panel = new JPanel(new GridBagLayout());
		JLabel description = new JLabel("Description");
		this.description_box = new JTextArea("", 5, 40);
		this.description_box.setEditable(false);
		JScrollPane scrollPaneDescription = new JScrollPane(this.description_box);
		GridBagConstraints descriptionPanelConstraints = new GridBagConstraints();
		descriptionPanelConstraints.gridx = 0;
		descriptionPanelConstraints.gridy = 0;
		description_panel.add(description, descriptionPanelConstraints);
		descriptionPanelConstraints.gridx = 0;
		descriptionPanelConstraints.gridy = 1;
		description_panel.add(scrollPaneDescription, descriptionPanelConstraints);
		description_panel.setSize(300, 300);
		
		mainPanelConstraints.gridx = 1;
		mainPanelConstraints.gridy = 3;
		mainPanel.add(description_panel, mainPanelConstraints);
		mainPanel.setVisible(true);
		this.gamegui.add(mainPanel);
		this.gamegui.setBackground(this.backgroundColor);
		
    }
	
	public void buildPuzzleMenu() {
        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchFromPuzzleToMainMenu(e);
          }
        });
        this.puzzlemenu.add(goBackButton);
		
        JButton goToGameButton = new JButton("Select Puzzle");
        goToGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToGameGUI(e);
          }
        });
        this.puzzlemenu.add(goToGameButton);
        
        String[] puzzleNames = { "One", "Two", "Three", "Four", "Five" };
        JComboBox<?> puzzles = new JComboBox<Object>(puzzleNames);

        puzzles.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  selectPuzzle(e);
          }
        });
        this.puzzlemenu.add(puzzles);
        this.puzzlemenu.setBackground(this.backgroundColor);
	}
	
	public void buildMainMenu() {
        JButton goToGameButton = new JButton("Start Game");
        goToGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToPuzzleMenu(e);
          }
        });
        this.mainmenu.add(goToGameButton);
        
        JButton goToSettingButton = new JButton("Settings");
        goToSettingButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchToSettingsMenu(e);
          }
        });
        this.mainmenu.add(goToSettingButton);
        
        JButton quitGameButton = new JButton("Quit Game");
        quitGameButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  quitGame();
          }
        });
        this.mainmenu.add(quitGameButton);
        this.mainmenu.setBackground(this.backgroundColor);
	}
	
	public void buildSettingsMenu() {
        JButton goToMainMenuButton = new JButton("Main Menu");
        goToMainMenuButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	  SwitchFromSettingsToMainMenu(e);
          }
        });
        this.settingsmenu.add(goToMainMenuButton);
        this.settingsmenu.setBackground(this.backgroundColor);
	}
	
	public void setAllMarkPoints(String code) {
		this.computer.setAllJumpLines(code);
	}
	
	public void sendCodetoGame(String code, boolean executeSingleLine, boolean runFast) {
		this.computer.retrieveUserInstruction(code, this, executeSingleLine, runFast);
	}

	public void displayOutputValue() {	
		highlightCodeLine();
		this.output = this.values.getText() + " " + this.computer.retrieveCurrentValueCPUs().get(0).getValue();
		this.values.setText(this.output);
		this.values.updateUI();
	}
	
	public void highlightCodeLine() {
		this.codeBox.getHighlighter().removeAllHighlights();
		DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
		try {
            int start = this.codeBox.getLineStartOffset(this.computer.getCurrentLine());
            int end = this.codeBox.getLineEndOffset(this.computer.getCurrentLine());
			this.codeBox.getHighlighter().addHighlight(start, end, painter);		

		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}
	
	public void displayValueCpuRegisters() {
		ArrayList<Register> registers = this.computer.retrieveCurrentValueCPUs();
		int index = 0;
		for(Register register: registers) {
			this.textAreaRegisters.get(index).setText("" + register.getValueInt());;
			index++;
		}
	}
	
	public void resetComputer() {
		this.computer.resetComputer();
		this.values.setText("");
		
		ArrayList<Register> registers = this.computer.retrieveCurrentValueCPUs();
		
		int index = 0;
		for(Register register: registers) {
			this.textAreaRegisters.get(index).setText("");;
			register.setValue("0");
			index++;
		}
		highlightCodeLine();
	}
	
	private void selectPuzzle(java.awt.event.ActionEvent evt) {
	       JComboBox<?> cb = (JComboBox<?>) evt.getSource();
	       String name = (String)cb.getSelectedItem();
	       this.puzzleName = name;
	}
	private void saveGame() throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter("solutions/" + this.puzzleName + ".txt")) {
		    out.println(codeBox.getText());
		}
	}
	
	private void setCodeBox() {
        if (Files.exists(Paths.get("solutions/" + this.puzzleName + ".txt"))) {
            StringBuilder contentBuilder = new StringBuilder();
	        try (Stream<String> stream = Files.lines( Paths.get("solutions/" + this.puzzleName + ".txt"), StandardCharsets.UTF_8)) 
	        {
	            stream.forEach(s -> contentBuilder.append(s).append("\n"));
	        }
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        this.codeBox.setText(contentBuilder.toString());
	        highlightCodeLine();
	        this.codeBox.setCaretPosition(0);
        } 
	}
	
	private void SwitchToMainMenu(java.awt.event.ActionEvent evt) {
		try {
			saveGame();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		this.gamegui.setVisible(false);
	    getContentPane().removeAll(); //gets rid of first panel
	    getContentPane().add(this.mainmenu); //adds desired panel to frame
	    this.mainmenu.setVisible(true);
	    validate(); //updates frame with new panel
	} 
	
	private void SwitchToGameGUI(java.awt.event.ActionEvent evt) {
		setCodeBox();
		this.puzzlemenu.setVisible(false);
	    getContentPane().removeAll(); 
	    getContentPane().add(this.gamegui); //adds desired panel to frame
	    this.gamegui.setVisible(true);
	    validate(); //updates frame with new panel
	} 
	
	private void SwitchFromPuzzleToMainMenu(java.awt.event.ActionEvent evt) {
		this.puzzlemenu.setVisible(false);
	    getContentPane().removeAll(); 
	    getContentPane().add(this.mainmenu); //adds desired panel to frame
	    this.mainmenu.setVisible(true);
	    validate(); //updates frame with new panel
	} 
	
	private void SwitchToPuzzleMenu(java.awt.event.ActionEvent evt) {
		this.mainmenu.setVisible(false);
	    getContentPane().removeAll(); 
	    getContentPane().add(this.puzzlemenu); //adds desired panel to frame
	    this.puzzlemenu.setVisible(true);
	    validate(); //updates frame with new panel
	}
	
	private void SwitchToSettingsMenu(java.awt.event.ActionEvent evt) {
		this.mainmenu.setVisible(false);
	    getContentPane().removeAll(); 
	    getContentPane().add(this.settingsmenu); //adds desired panel to frame
	    this.settingsmenu.setVisible(true);
	    validate(); //updates frame with new panel
	}
	
	private void SwitchFromSettingsToMainMenu(java.awt.event.ActionEvent evt) {
		this.settingsmenu.setVisible(false);
	    getContentPane().removeAll(); 
	    getContentPane().add(this.mainmenu); //adds desired panel to frame
	    this.mainmenu.setVisible(true);
	    validate(); //updates frame with new panel
	}
	
	private void quitGame() {
		System.exit(0);
	}
}
