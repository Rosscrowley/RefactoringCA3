/*
 *
 * This is the dialog for Employee search by ID
 *
 * */

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class SearchByIdDialog extends SearchDialog  {

    public SearchByIdDialog(EmployeeDetails parent) {
        super(parent, "Search by ID", "ID");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if option search, search for Employee
        if (e.getSource() == search) {
            // try get correct valus from text field
            try {
                Double.parseDouble(searchField.getText());
                this.parent.searchByIdField.setText(searchField.getText());
                // search Employee by ID
                this.parent.searchEmployeeById();
                dispose();// dispose dialog
            }// end try
            catch (NumberFormatException num) {
                // display message and set colour to text field if entry is wrong
                searchField.setBackground(new Color(255, 150, 150));
                JOptionPane.showMessageDialog(null, "Wrong ID format!");
            }// end catch
        }// end if
        // else dispose dialog
        else if (e.getSource() == cancel)
            dispose();
    }// end actionPerformed

}// end class searchByIdDialog