/**
 * @author PineappleSnow
 * @version 1.0
 * @date 2019/12/19 23:09
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {
    private int loginUIWidth, loginUIHeight;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Document userDocument;

    LoginUI() {
        loginUIWidth = 300;
        loginUIHeight = 200;
        loginFrame();
    }


    private void loginFrame() {
        Dimension screenSize = getToolkit().getScreenSize();
        this.setTitle("小学信息管理系统--登录界面");
        this.setSize(loginUIWidth, loginUIHeight);
        this.setLocation((screenSize.width - loginUIWidth) / 2, (screenSize.height - loginUIHeight) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setMinimumSize(new Dimension(300, 200));

        userNameLabel = new JLabel("用户名");
        passwordLabel = new JLabel("密码");
        userNameTextField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("登录");

        loginFrameSize();
        loginInteraction();

        this.add(userNameLabel);
        this.add(passwordLabel);
        this.add(userNameTextField);
        this.add(passwordField);
        this.add(loginButton);
        this.setVisible(true);
    }

    private void loginFrameSize() {
        userNameLabel.setBounds(loginUIWidth / 15, 30, loginUIWidth / 5, 20);
        passwordLabel.setBounds(userNameLabel.getX(), userNameLabel.getY() + loginUIHeight / 8, userNameLabel.getWidth(), userNameLabel.getHeight());
        userNameTextField.setBounds(loginUIWidth / 4, userNameLabel.getY(), loginUIWidth / 10 * 6, 20);
        passwordField.setBounds(loginUIWidth / 4, passwordLabel.getY(), userNameTextField.getWidth(), userNameTextField.getHeight());
        loginButton.setBounds((loginUIWidth - 100) / 2, passwordLabel.getY()+ loginUIHeight/4, 100, 30);
    }

    private void loginInteraction() {
        userDocument = userNameTextField.getDocument();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                loginUIWidth = e.getComponent().getWidth();
                loginUIHeight = e.getComponent().getHeight();
                loginFrameSize();
            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkLogin();
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    checkLogin();
                }
            }
        });
        userDocument.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                passwordField.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                passwordField.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                passwordField.setText("");
            }
        });
    }

    private void checkLogin() {
        JDBC jdbc = new JDBC();
        boolean success = jdbc.findUserPassword(userNameTextField.getText().trim(), String.valueOf(passwordField.getPassword()));
        if (success) {
            System.out.println("login success");
            LoginUI.super.dispose();
            new ManageUI();
        } else {
            System.out.println("login failed");
            JOptionPane.showMessageDialog(this, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        jdbc.close();
    }
}

