package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InvokeLaterExample extends JPanel {
    private JLabel statusArea;
    private DefaultListModel listModel;

    public InvokeLaterExample() {
        JButton button = new JButton(new LongRunningModelFillAction());
        add(button);

        JList list = new JList();
        this.listModel = new DefaultListModel();
        this.listModel.addElement("An Empty List Model");
        list.setModel(listModel);
        add(new JScrollPane(list));

        add(new JLabel("Status:"));
        this.statusArea = new JLabel();
        add(this.statusArea);
    }

    private class LongRunningModelFillAction extends AbstractAction {
        public LongRunningModelFillAction() {
            super("Fill Model");
        }

        public void actionPerformed(ActionEvent e) {
            InvokeLaterExample.this.listModel.removeAllElements();
            InvokeLaterExample.this.listModel.addElement("Calculating...");
            PopulationRunnable populationRunnable = new PopulationRunnable();
            Thread populationThread = new Thread(populationRunnable);
            populationThread.start();
        }
    }

    private class PopulationRunnable implements Runnable {
        public void run() {
            final Object[] values = new Object[100];
            for (int i = 1; i <= 100; i++) {
                values[i - 1] = "Value" + i;


                if ((i % 10) == 0) {
                    final int progress = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            InvokeLaterExample.this.statusArea.setText("Calculated " + progress);
                        }
                    });
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    InvokeLaterExample.this.statusArea.setText("");
                    InvokeLaterExample.this.listModel.removeAllElements();
                    for (int i = 0; i < values.length; i++) {
                        InvokeLaterExample.this.listModel.addElement(values[i]);
                    }
                }
            });
        }
    }
    public static void main(String[] a){
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(1);
      f.add(new InvokeLaterExample());
      f.pack();
      f.setVisible(true);
    } 
    
}
