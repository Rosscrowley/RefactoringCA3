/*
 *
 * This is a dialog for adding new Employees and saving records to file
 *
 * */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Predicate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddRecordDialog extends JDialog implements ActionListener {

    private static final int DIALOG_WIDTH = 500;
    private static final int DIALOG_HEIGHT = 370;
    private static final int DIALOG_X_LOCATION = 350;
    private static final int DIALOG_Y_LOCATION = 250;
    JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    JButton save, cancel;
    EmployeeDetails parent;

    Color red = new Color(255,150,150);

    public AddRecordDialog(EmployeeDetails parent){
        this.parent = parent;
        initializeDialog();
        initializeUIComponents();
        finalizeDialog();
    }

    private void initializeDialog() {
        setTitle("Add Record");
        setModal(true);
        this.parent.setEnabled(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUIComponents() {
        JScrollPane scrollPane = new JScrollPane(AddRecordDialogPane());
        setContentPane(scrollPane);
    }

    private void finalizeDialog() {
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setLocation(DIALOG_X_LOCATION, DIALOG_Y_LOCATION);
        setVisible(true);
        getRootPane().setDefaultButton(save);
    }
    public Container AddRecordDialogPane() {
        JPanel empDetails = new JPanel(new MigLayout());
        empDetails.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        addLabelAndInputComponent(empDetails, "IDDD:", idField = createTextField(false));
        addLabelAndInputComponent(empDetails, "PPS Number:", ppsField = createTextField( true));
        addLabelAndInputComponent(empDetails, "Surname:", surnameField = createTextField( true));
        addLabelAndInputComponent(empDetails, "First Name:", firstNameField = createTextField( true));
        addLabelAndInputComponent(empDetails, "Gender:", genderCombo = createComboBox(this.parent.gender));
        addLabelAndInputComponent(empDetails, "Department:", departmentCombo = createComboBox(this.parent.department));
        addLabelAndInputComponent(empDetails, "Salary:", salaryField = createTextField( true));
        addLabelAndInputComponent(empDetails, "Full Time:", fullTimeCombo = createComboBox(this.parent.fullTime));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(save = createButton("Save"));
        buttonPanel.add(cancel = createButton("Cancel"));
        empDetails.add(buttonPanel, "span 2,growx, pushx, wrap");

        idField.setText(Integer.toString(this.parent.getNextFreeId()));
        return empDetails;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(this.parent.font1);
        button.addActionListener(this);
        return button;
    }

    private JTextField createTextField( boolean editable) {
        JTextField textField = new JTextField(20);
        textField.setDocument(new JTextFieldLimit(20));
        textField.setEditable(editable);
        return textField;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }

    private void addLabelAndInputComponent(JPanel panel, String labelText, Component inputComponent) {
        JLabel label = new JLabel(labelText);
        label.setFont(this.parent.font1);
        panel.add(label, "growx, pushx");
        inputComponent.setFont(this.parent.font1);
        if (inputComponent instanceof JComboBox) {
            (inputComponent).setBackground(Color.WHITE);
        }
        panel.add(inputComponent, "growx, pushx, wrap");
    }
    // add record to file
    public void addRecord() {
        boolean fullTime = false;
        Employee theEmployee;

        if (((String) fullTimeCombo.getSelectedItem()).equalsIgnoreCase("Yes"))
            fullTime = true;
        // create new Employee record with details from text fields
        theEmployee = new Employee(Integer.parseInt(idField.getText()), ppsField.getText().toUpperCase(), surnameField.getText().toUpperCase(),
                firstNameField.getText().toUpperCase(), genderCombo.getSelectedItem().toString().charAt(0),
                departmentCombo.getSelectedItem().toString(), Double.parseDouble(salaryField.getText()), fullTime);
        this.parent.currentEmployee = theEmployee;
        this.parent.addRecord(theEmployee);
        this.parent.displayRecords(theEmployee);
    }

 public boolean checkInput() {
     boolean valid = true;

     // Validate TextFields
     valid &= validateTextField(ppsField, text -> !text.isEmpty() && !this.parent.correctPps(text, -1));
     valid &= validateTextField(surnameField, text -> !text.isEmpty());
     valid &= validateTextField(firstNameField, text -> !text.isEmpty());

     // Validate Combo Boxes
     valid &= validateComboBox(genderCombo);
     valid &= validateComboBox(departmentCombo);
     valid &= validateComboBox(fullTimeCombo);

     // Validate Salary Field
     valid &= validateTextField(salaryField, text -> {
         try {
             double salary = Double.parseDouble(text);
             return salary >= 0;
         } catch (NumberFormatException e) {
             return false;
         }
     });

     return valid;
 }
 private boolean validateTextField(JTextField field, Predicate<String> validationPredicate) {
     boolean isValid = validationPredicate.test(field.getText().trim());
     if (!isValid) {
         field.setBackground(red);
     } else {
         field.setBackground(Color.WHITE);
     }
     return isValid;
 }
    private boolean validateComboBox(JComboBox<?> comboBox) {
        boolean isValid = comboBox.getSelectedIndex() != 0;
        if (!isValid) {
            comboBox.setBackground(red);
        } else {
            comboBox.setBackground(Color.WHITE);
        }
        return isValid;
    }

    // set text field to white colour
    public void setToWhite() {
        ppsField.setBackground(Color.WHITE);
        surnameField.setBackground(Color.WHITE);
        firstNameField.setBackground(Color.WHITE);
        salaryField.setBackground(Color.WHITE);
        genderCombo.setBackground(Color.WHITE);
        departmentCombo.setBackground(Color.WHITE);
        fullTimeCombo.setBackground(Color.WHITE);
    }// end setToWhite

    // action performed
    public void actionPerformed(ActionEvent e) {
        // if chosen option save, save record to file
        if (e.getSource() == save) {
            // if inputs correct, save record
            if (checkInput()) {
                addRecord();// add record to file
                dispose();// dispose dialog
                this.parent.changesMade = true;
            }// end if
            // else display message and set text fields to white colour
            else {
                JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");
                setToWhite();
            }// end else
        }// end if
        else if (e.getSource() == cancel)
            dispose();// dispose dialog
    }// end actionPerformed
}// end class AddRecordDialog