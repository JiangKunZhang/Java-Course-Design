/**
 * @author PineappleSnow
 * @version 1.0
 * @date 2019/12/19 23:07
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InsertAndUpdateUI extends JFrame {
    private int UIWidth, UIHeight;
    private SchoolInfo info;
    private boolean isInsert;
    private JLabel[] labels;
    private JTextField[] fields;
    private JButton executeButton;
    private ManageUI parentUI;

    public InsertAndUpdateUI(SchoolInfo info, boolean isInsert, ManageUI parentUI) {
        UIWidth = 350;
        UIHeight = 500;
        this.info = info;
        this.isInsert = isInsert;
        this.parentUI = parentUI;
        InsertAndUpdateFrame();
    }


    private void InsertAndUpdateFrame() {
        Dimension screenSize = getToolkit().getScreenSize();
        this.setSize(UIWidth, UIHeight);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocation((screenSize.width - UIWidth) / 2, (screenSize.height - UIHeight) / 2);

        String[] labelsName = {"name", "address", "phone", "postcode", "principalname"};
        labels = new JLabel[5];
        fields = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            labels[i] = new JLabel(labelsName[i]);
            fields[i] = new JTextField();
        }
        executeButton = new JButton();

        if (isInsert) {
            this.setTitle("增加信息");
            executeButton.setText("增加");
        } else {
            this.setTitle("修改信息");
            executeButton.setText("修改");
            fields[0].setText(info.getName());
            fields[1].setText(info.getAddress());
            fields[2].setText(info.getPhone());
            fields[3].setText(info.getPostcode());
            fields[4].setText(info.getPrincipalname());
        }

        UISize();
        interaction();

        for (int i = 0; i < 5; i++) {
            this.add(labels[i]);
            this.add(fields[i]);
        }
        this.add(executeButton);
        this.setVisible(true);
    }

    private void UISize() {
        for (int i = 0; i < 5; i++) {
            labels[i].setBounds(10, 70 * i + 30, 100, 30);
            fields[i].setBounds(120, 70 * i + 30, 200, 30);
        }
        executeButton.setBounds(125, 380, 100, 60);
    }

    private void interaction() {
        executeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < 5; i++) {
                    if (fields[i].getText().trim().equals("")) {
                        JOptionPane.showMessageDialog(InsertAndUpdateUI.this, "内容不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                JDBC jdbc = new JDBC();
                SchoolInfo newInfo = new SchoolInfo(0, fields[0].getText().trim(), fields[1].getText().trim(), fields[2].getText().trim(), fields[3].getText().trim(), fields[4].getText().trim());
                if (isInsert) {
                    jdbc.insertSchoolInfo(newInfo);
                } else {
                    newInfo.setId(info.getId());
                    jdbc.updateSchoolInfo(newInfo);
                }
                InsertAndUpdateUI.this.dispose();
                parentUI.loadInformation(true, "", "");
            }
        });
    }
}
