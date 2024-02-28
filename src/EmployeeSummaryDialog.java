/*
 *
 * This is the summary dialog for displaying all Employee details
 *
 * */

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.miginfocom.swing.MigLayout;

public class EmployeeSummaryDialog extends JDialog implements ActionListener {
    // vector with all Employees details
    Vector<Vector<Object>> allEmployees;
    JButton backButton;

    private static final int WIDTH = 850;
    private static final int HEIGHT = 500;
    private static final int LOCATION_X = 350;
    private static final int LOCATION_Y = 250;

    private static final Dimension TABLE_VIEWPORT_SIZE = new Dimension(800, 300);
    private static final String[] HEADER_NAMES = { "ID", "PPS Number", "Surname", "First Name", "Gender", "Department", "Salary", "Full Time" };
    private static final int[] COLUMN_WIDTHS = { 15, 100, 120, 120, 50, 120, 80, 80 };


    public EmployeeSummaryDialog(Vector<Vector<Object>> allEmployees) {
        this.allEmployees = allEmployees;
        initializeDialog();
    }
    private void initializeDialog() {
        setTitle("Employee Summary");
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addComponentsToPane();
        setSize(WIDTH, HEIGHT);
        setLocation(LOCATION_X, LOCATION_Y);
        setVisible(true);
    }
    private void addComponentsToPane() {
        JScrollPane scrollPane = new JScrollPane(EmployeeSummaryPane());
        setContentPane(scrollPane);
    }
    private Container EmployeeSummaryPane() {
         JPanel summaryDialog = new JPanel(new MigLayout());
         JPanel buttonPanel = setupButtonPanel();

         JTable employeeTable = setupEmployeeTable();
         JScrollPane scrollPane = new JScrollPane(employeeTable);
         scrollPane.setBorder(BorderFactory.createTitledBorder("Employee Details"));
         scrollPane.setPreferredSize(TABLE_VIEWPORT_SIZE);

         summaryDialog.add(buttonPanel, "growx, pushx, wrap");
         summaryDialog.add(scrollPane, "growx, pushx, wrap");

         return summaryDialog;
    }
    private JPanel setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setToolTipText("Return to main screen");
        buttonPanel.add(backButton);
        return buttonPanel;
    }
    private JTable setupEmployeeTable() {
        Vector<String> header = new Vector<>(Arrays.asList(HEADER_NAMES));
        DefaultTableModel tableModel = new CustomTableModel(this.allEmployees, header);
        JTable employeeTable = new JTable(tableModel);
        configureTableColumns(employeeTable);
        employeeTable.setEnabled(false);
        employeeTable.setAutoCreateRowSorter(true);
        return employeeTable;
    }
    private void configureTableColumns(JTable table) {
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setMinWidth(COLUMN_WIDTHS[i]);
            if (i == 0) {
                column.setCellRenderer(leftRenderer);
            } else if (i == 4 || i == 6) {
                column.setCellRenderer(i == 4 ? centerRenderer : new DecimalFormatRenderer());
            }
        }
    }
    private class CustomTableModel extends DefaultTableModel {
        public CustomTableModel(Vector<Vector<Object>> data, Vector<String> header) {
            super(data, header);
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0 -> Integer.class;
                case 4 -> Character.class;
                case 6 -> Double.class;
                case 7 -> Boolean.class;
                default -> String.class;
            };
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton){
            dispose();
        }
    }
    // format for salary column
    static class DecimalFormatRenderer extends DefaultTableCellRenderer {
        private static final DecimalFormat format = new DecimalFormat(
                "€ ###,###,##0.00" );

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            JLabel label = (JLabel) c;
            label.setHorizontalAlignment(JLabel.RIGHT);
            // format salary column
            value = format.format((Number) value);

            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }// end getTableCellRendererComponent
    }// DefaultTableCellRenderer
}// end class EmployeeSummaryDialog
