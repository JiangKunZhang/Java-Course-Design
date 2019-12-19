/**
 * @author PineappleSnow
 * @version 1.0
 * @date 2019/12/19 23:10
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ManageUI extends JFrame {
    private int manageUIWidth, manageUIHeight;
    private JTable infoTable;
    private JScrollPane jScrollPane;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton selectButton;
    private String[] columnName = {"id", "name", "address", "phone", "postcode", "principalname"};

    public ManageUI() {
        manageUIWidth = 600;
        manageUIHeight = 500;
        manageFrame();
    }


    private void manageFrame() {
        Dimension screenSize = getToolkit().getScreenSize();
        this.setTitle("小学信息管理系统");
        this.setSize(manageUIWidth, manageUIHeight);
        this.setLocation((screenSize.width - manageUIWidth) / 2, (screenSize.height - manageUIHeight) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setMinimumSize(new Dimension(600, 500));

        infoTable = new JTable();
        jScrollPane = new JScrollPane(infoTable);
        deleteButton = new JButton("delete");
        insertButton = new JButton("insert");
        selectButton = new JButton("select");
        updateButton = new JButton("update");
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        manageFrameSize();
        manageInteraction();
        loadInformation(true, "", "");

        this.add(jScrollPane);
        this.add(deleteButton);
        this.add(insertButton);
        this.add(updateButton);
        this.add(selectButton);
        this.setVisible(true);
    }

    public void loadInformation(boolean isAll, String condition, String content) {
        JDBC jdbc = new JDBC();
        ArrayList<SchoolInfo> infos = jdbc.selectSchoolInfo(isAll, condition, content);
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnName);
        for (SchoolInfo info : infos) {
            tableModel.addRow(new Object[]{info.getId(), info.getName(), info.getAddress(), info.getPhone(), info.getPostcode(), info.getPrincipalname()});
        }
        infoTable.setModel(tableModel);
        infoTable.setRowHeight(25);
        jdbc.close();
    }

    private void manageFrameSize() {
        jScrollPane.setBounds(10, 100, manageUIWidth - 35, manageUIHeight - 150);
        selectButton.setBounds(10, 25, 80, 50);
        deleteButton.setBounds(90 + (jScrollPane.getWidth() - 320) / 3, 25, 80, 50);
        updateButton.setBounds(170 + (jScrollPane.getWidth() - 320) / 3 * 2, 25, 80, 50);
        insertButton.setBounds(10 + jScrollPane.getWidth() - 80, 25, 80, 50);
    }

    private void manageInteraction() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                manageUIWidth = e.getComponent().getWidth();
                manageUIHeight = e.getComponent().getHeight();
                manageFrameSize();
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (infoTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(ManageUI.this, "未选中行", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    SchoolInfo info = new SchoolInfo((int) infoTable.getValueAt(infoTable.getSelectedRow(), 0), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 1), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 2), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 3), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 4), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 5));
                    JDBC jdbc = new JDBC();
                    jdbc.deleteSchoolInfo(info);
                    jdbc.close();
                    loadInformation(true, "", "");
                }
            }
        });
        insertButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new InsertAndUpdateUI(null, true, ManageUI.this);
            }
        });
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (infoTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(ManageUI.this, "未选中行", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    SchoolInfo info = new SchoolInfo((int) infoTable.getValueAt(infoTable.getSelectedRow(), 0), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 1), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 2), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 3), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 4), (String) infoTable.getValueAt(infoTable.getSelectedRow(), 5));
                    new InsertAndUpdateUI(info, false, ManageUI.this);
                }
            }
        });
        selectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectDialog();
            }
        });
    }

    private void selectDialog() {
        JDialog dialog = new JDialog(this, "ssss");
        dialog.setLayout(null);
        Dimension screenSize = getToolkit().getScreenSize();
        dialog.setBounds((screenSize.width - 300) / 2, (screenSize.height - 100) / 2, 300, 100);
        JComboBox<String> ways = new JComboBox<>();
        for (int i = 0; i < 6; i++) {
            ways.addItem(columnName[i]);
        }
        ways.setBounds(5, 13, 70, 30);
        JTextField inputField = new JTextField();
        inputField.setBounds(80, 13, 115, 30);
        JButton searchButton = new JButton("search");
        searchButton.setBounds(200, 13, 80, 30);

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (inputField.getText().trim().equals("")) {
                    loadInformation(true, "", "");
                } else {
                    loadInformation(false, (String) ways.getSelectedItem(), inputField.getText().trim());
                }
                dialog.dispose();
            }
        });

        dialog.add(ways);
        dialog.add(inputField);
        dialog.add(searchButton);
        dialog.setVisible(true);
    }
}
