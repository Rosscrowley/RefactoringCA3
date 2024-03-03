import java.io.File;

public class NavigateRecords {
    private RandomFile application;
    private File file;
    private long currentByteStart = 0;
    private Employee currentEmployee;
    private EmployeeDetails employeeDetails;

    public NavigateRecords(RandomFile application, File file, EmployeeDetails employeeDetails) {
        this.application = application;
        this.file = file;
        this.employeeDetails = employeeDetails;
    }

    public void firstRecord() {
        if (employeeDetails.isSomeoneToDisplay()) {
            application.openReadFile(file.getAbsolutePath());
            currentByteStart = application.getFirst();
            currentEmployee = application.readRecords(currentByteStart);
            application.closeReadFile();
            if (currentEmployee.getEmployeeId() == 0)
                nextRecord();
            else
                employeeDetails.displayRecords(currentEmployee);
        }
    }

    public void previousRecord() {
        if (employeeDetails.isSomeoneToDisplay()) {
            application.openReadFile(file.getAbsolutePath());
            currentByteStart = application.getPrevious(currentByteStart);
            currentEmployee = application.readRecords(currentByteStart);
            while (currentEmployee.getEmployeeId() == 0) {
                currentByteStart = application.getPrevious(currentByteStart);
                currentEmployee = application.readRecords(currentByteStart);
            }
            application.closeReadFile();
            employeeDetails.displayRecords(currentEmployee);
        }
    }

    public void nextRecord() {
        if (employeeDetails.isSomeoneToDisplay()) {
            application.openReadFile(file.getAbsolutePath());
            currentByteStart = application.getNext(currentByteStart);
            currentEmployee = application.readRecords(currentByteStart);
            while (currentEmployee.getEmployeeId() == 0) {
                currentByteStart = application.getNext(currentByteStart);
                currentEmployee = application.readRecords(currentByteStart);
            }
            application.closeReadFile();
            employeeDetails.displayRecords(currentEmployee);
        }
    }

    public void lastRecord() {
        if (employeeDetails.isSomeoneToDisplay()) {
            application.openReadFile(file.getAbsolutePath());
            currentByteStart = application.getLast();
            currentEmployee = application.readRecords(currentByteStart);
            application.closeReadFile();
            if (currentEmployee.getEmployeeId() == 0)
                previousRecord();
            else
                employeeDetails.displayRecords(currentEmployee);
        }
    }
}