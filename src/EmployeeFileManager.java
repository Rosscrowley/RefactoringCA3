import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;

public class EmployeeFileManager {
    private EmployeeDetails employeeDetails;
    private RandomFile application;   private File file;
    private boolean change;

    private NavigateRecords records;
    private FileNameExtensionFilter datfilter = new FileNameExtensionFilter("dat files (*.dat)", "dat");
    private String generatedFileName;

    public EmployeeFileManager(EmployeeDetails employeeDetails, RandomFile application) {
        this.employeeDetails = employeeDetails;
        this.application = application;
    }

    public void openFile() {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open");
        fc.setFileFilter(datfilter);
        File newFile;

        if (file.length() != 0 || change) {
            int returnVal = JOptionPane.showOptionDialog(null, "Do you want to save changes?", "Save",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (returnVal == JOptionPane.YES_OPTION) {
                saveFile();
            }
        }

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFile = fc.getSelectedFile();
            if (file.getName().equals(generatedFileName))
                file.delete();
            file = newFile;
            application.openReadFile(file.getAbsolutePath());
            records.firstRecord();
            employeeDetails.displayRecords(employeeDetails.getCurrentEmployee());
            application.closeReadFile();
        }
    }

    public void saveFile() {
        if (file.getName().equals(generatedFileName))
            saveFileAs();
        else {
            if (change) {
                int returnVal = JOptionPane.showOptionDialog(null, "Do you want to save changes?", "Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (returnVal == JOptionPane.YES_OPTION) {
                    if (!employeeDetails.getIdField().getText().equals("")) {
                        application.openWriteFile(file.getAbsolutePath());
                        application.changeRecords(employeeDetails.getChangedDetails(), employeeDetails.getCurrentByteStart());
                        application.closeWriteFile();
                    }
                }
            }
            employeeDetails.displayRecords(employeeDetails.getCurrentEmployee());
            employeeDetails.setEnabled(false);
        }
    }

    public void saveFileAs() {
        final JFileChooser fc = new JFileChooser();
        File newFile;
        String defaultFileName = "new_Employee.dat";
        fc.setDialogTitle("Save As");
        fc.setFileFilter(datfilter);
        fc.setApproveButtonText("Save");
        fc.setSelectedFile(new File(defaultFileName));

        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            newFile = fc.getSelectedFile();
            if (!checkFileName(newFile)) {
                newFile = new File(newFile.getAbsolutePath() + ".dat");
                application.createFile(newFile.getAbsolutePath());
            } else {
                application.createFile(newFile.getAbsolutePath());
            }

            try {
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                if (file.getName().equals(generatedFileName))
                    file.delete();
                file = newFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        change = false;
    }

    public void exitApp() {
        if (file.length() != 0 && change) {
            int returnVal = JOptionPane.showOptionDialog(null, "Do you want to save changes?", "Save",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (returnVal == JOptionPane.YES_OPTION) {
                saveFile();
                if (file.getName().equals(generatedFileName))
                    file.delete();
                System.exit(0);
            } else if (returnVal == JOptionPane.NO_OPTION) {
                if (file.getName().equals(generatedFileName))
                    file.delete();
                System.exit(0);
            }
        } else {
            if (file.getName().equals(generatedFileName))
                file.delete();
            System.exit(0);
        }
    }

    private boolean checkFileName(File fileName) {
        String name = fileName.getName();
        return name.length() > 4 && name.substring(name.length() - 4).equals(".dat");
    }
}