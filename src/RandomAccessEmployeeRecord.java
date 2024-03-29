/*
 *
 * This is a Random Access Employee record definition
 *
 * */

import java.io.RandomAccessFile;
import java.io.IOException;

public class RandomAccessEmployeeRecord extends Employee {
    public static final int SIZE = 175; // Size of each RandomAccessEmployeeRecord object
    private static final int NAME_LENGTH = 20;
    // Create empty record
    public RandomAccessEmployeeRecord() {
        this(0, "","","",'\0', "", 0.0, false);
    }

    // Initialize record with details
    public RandomAccessEmployeeRecord(int employeeId, String pps, String surname, String firstName, char gender,
                                      String department, double salary, boolean fullTime) {
        super(employeeId, pps, surname, firstName, gender, department, salary, fullTime);
    }

    // Read a record from specified RandomAccessFile
    public void read(RandomAccessFile file) throws IOException {
        setEmployeeId(file.readInt());
        setPps(readName(file));
        setSurname(readName(file));
        setFirstName(readName(file));
        setGender(file.readChar());
        setDepartment(readName(file));
        setSalary(file.readDouble());
        setFullTime(file.readBoolean());
    }

    // Ensure that string is correct length
    private String readName(RandomAccessFile file) throws IOException {
        char[] name = new char[NAME_LENGTH];
        for (int i = 0; i < name.length; i++) {
            name[i] = file.readChar();
        }
        return new String(name).replace('\0', ' ');
    }
    // Write a record to specified RandomAccessFile
    public void write(RandomAccessFile file) throws IOException {
        file.writeInt(getEmployeeId());
        writeName(file, getPps().toUpperCase());
        writeName(file, getSurname().toUpperCase());
        writeName(file, getFirstName().toUpperCase());
        file.writeChar(getGender());
        writeName(file, getDepartment());
        file.writeDouble(getSalary());
        file.writeBoolean(getFullTime());
    }
    // Ensure that string is correct length
    private void writeName(RandomAccessFile file, String name) throws IOException {
        StringBuffer buffer = new StringBuffer(name != null ? name : "");
        buffer.setLength(NAME_LENGTH);
        for (int i = 0; i < NAME_LENGTH; i++) {
            char c = i < buffer.length() ? buffer.charAt(i) : '\0';
            file.writeChar(c);
        }
    }
}