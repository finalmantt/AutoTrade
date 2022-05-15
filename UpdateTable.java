package autotrade;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import javax.swing.event.*;

public class UpdateTable 
{  
    //TextField
    private JTextField text1, text2, text3, text4;
  
    // JTable Header
    public static final String[] columns = {
        "Name", "Age", "Address"
    };
    // Create the table model
    private DefaultTableModel model = new DefaultTableModel(columns, 0);
    // Create the table
    private JTable table = new JTable(model);
    // Create the main panel
    private JPanel mainPanel = new JPanel(new BorderLayout());

    public UpdateTable() 
    {
        //Add button
        JButton addButton = new JButton("+ Add");
        //Update button
        JButton updateButton = new JButton("Update");
        //Button panel
        JPanel buttonPanel = new JPanel();
        //Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
    
        // This code is called when the Add button is clicked
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //Add form data
              model.addRow(
                   new Object[]{
                         text1.getText(), 
                         text2.getText(),
                         text3.getText()
                   }
              );
        
              //Delete form after adding data
              text1.setText("");
              text2.setText("");
              text3.setText("");
            }
        });

          // Get the selected row from JTable and put the data into JTextfields
          table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){ 
          @Override
          public void valueChanged(ListSelectionEvent e) {
                int i = table.getSelectedRow();
                text1.setText((String)model.getValueAt(i, 0));
                text2.setText((String)model.getValueAt(i, 1));
                text3.setText((String)model.getValueAt(i, 2));
            }
        });
    
        // This code is called when the Update button is clicked.
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //Update the form
               int i = table.getSelectedRow();
               model.setValueAt(text1.getText(), i, 0);
               model.setValueAt(text2.getText(), i, 1);
               model.setValueAt(text3.getText(), i, 2);
            }
        });
    
        //Create the JTextFields panel
        JPanel textPanel = new JPanel(new BorderLayout());
        text1 = new JTextField();
        text2 = new JTextField();
        text3 = new JTextField();
        //Add the JTextFields to the panel
        textPanel.add(text1, BorderLayout.NORTH);
        textPanel.add(text2, BorderLayout.CENTER);
        textPanel.add(text3, BorderLayout.SOUTH);
  
        //Add the panels and the table to the main panel
        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    //Get the main panel
    public JComponent getComponent() {
        return mainPanel;
    }
    // start the application in thread-safe
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Update a Row in JTable");
                f.getContentPane().add(new UpdateTable().getComponent());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(340,250);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }
}