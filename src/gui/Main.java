package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main extends JPanel {

JProgressBar pbar;

static int min = 0;
static int max = 100;

	public Main() {
	
		pbar = new JProgressBar();
		pbar.setMinimum(min);
		pbar.setMaximum(max);
		pbar.setStringPainted(true);
		add(pbar);
		
		JButton resetButton = new JButton("Reset");  
		
		resetButton.addActionListener(new ActionListener() {
	        	
			public void actionPerformed(ActionEvent e) {
				
				new Thread(new Runnable() {
					public void run() {
						startProgression();
					}
				}).start();
			}
		});
		        
		add(resetButton);
		
		JFrame frame = new JFrame("Progress Bar Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);
	}
  
	public void startProgression() {
		for (int i = min; i <= max; i++) {
			final int percent = i;
			try {
//				SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
					updateBar(percent);
//				}
//				});
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

  public void updateBar(int newValue) {
    pbar.setValue(newValue);
  }

  public static void main(String args[]) {
    new Main();
  }
}