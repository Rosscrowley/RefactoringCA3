/*
 *
 * This is a dialog for searching Employees by their surname.
 *
 * */

import java.awt.event.ActionEvent;

public class SearchBySurnameDialog extends SearchDialog {
    public SearchBySurnameDialog(EmployeeDetails parent) {
        super(parent, "Search by Surname", "Surname");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if option search, search for Employee
        if(e.getSource() == search){
            this.parent.searchBySurnameField.setText(searchField.getText());
            // search Employee by surname
            this.parent.searchEmployeeBySurname();
            dispose();// dispose dialog
        }// end if
        // else dispose dialog
        else if(e.getSource() == cancel)
            dispose();// dispose dialog
    }// end actionPerformed
}// end class SearchBySurnameDialog

