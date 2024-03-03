/*
 *
 * This is a dialog for searching Employees by their surname.
 *
 * */

import java.awt.event.ActionEvent;

public class SearchBySurnameDialog extends SearchDialog {

    private SearchEmployee searchEmployee;
    public SearchBySurnameDialog(EmployeeDetails parent) {
        super(parent, "Search by Surname", "Surname");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if option search, search for Employee
        if(e.getSource() == search){
            // Use the SearchEmployee instance to perform the search
            this.searchEmployee.searchEmployeeBySurname(searchField.getText());
            dispose(); // dispose dialog
        }// end if
        // else dispose dialog
        else if(e.getSource() == cancel){
            dispose(); // dispose dialog
        }
    }// end actionPerformed
}// end class SearchBySurnameDialog

