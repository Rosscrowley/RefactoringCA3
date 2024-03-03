import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JTextField;

public class SearchEmployee {
    private EmployeeDetails employeeDetails;
    NavigateRecords navigate;
    public SearchEmployee(EmployeeDetails employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

    public void searchEmployeeById() {
        boolean found = false;

        try {
            if (employeeDetails.isSomeoneToDisplay()) {
                navigate.firstRecord();
                int firstId = employeeDetails.currentEmployee.getEmployeeId();

                if (employeeDetails.searchByIdField.getText().trim().equals(employeeDetails.idField.getText().trim())) {
                    found = true;
                } else if (employeeDetails.searchByIdField.getText().trim().equals(Integer.toString(employeeDetails.currentEmployee.getEmployeeId()))) {
                    found = true;
                    employeeDetails.displayRecords(employeeDetails.currentEmployee);
                } else {
                    navigate.nextRecord();
                    while (firstId != employeeDetails.currentEmployee.getEmployeeId()) {
                        if (Integer.parseInt(employeeDetails.searchByIdField.getText().trim()) == employeeDetails.currentEmployee.getEmployeeId()) {
                            found = true;
                            employeeDetails.displayRecords(employeeDetails.currentEmployee);
                            break;
                        } else {
                            navigate.nextRecord();
                        }
                    }
                }

                if (!found)
                    JOptionPane.showMessageDialog(null, "Employee not found!");
            }
        } catch (NumberFormatException e) {
            employeeDetails.searchByIdField.setBackground(new Color(255, 150, 150));
            JOptionPane.showMessageDialog(null, "Wrong ID format!");
        }
        employeeDetails.searchByIdField.setBackground(Color.WHITE);
        employeeDetails.searchByIdField.setText("");
    }

    public void searchEmployeeBySurname(String surname) {
        boolean found = false;

        if (employeeDetails.isSomeoneToDisplay()) {
            navigate.firstRecord();
            String firstSurname = employeeDetails.currentEmployee.getSurname().trim();

           if (surname.trim().equalsIgnoreCase(firstSurname)) {
                found = true;
            } else {
                navigate.nextRecord();
                while (!firstSurname.trim().equalsIgnoreCase(employeeDetails.currentEmployee.getSurname().trim())) {
                    if (surname.trim().equalsIgnoreCase(employeeDetails.currentEmployee.getSurname().trim())) {
                        found = true;
                        employeeDetails.displayRecords(employeeDetails.currentEmployee);
                        break;
                    } else {
                        navigate.nextRecord();
                    }
                }
            }

            if (!found)
                JOptionPane.showMessageDialog(null, "Employee not found!");
        }
    }
}