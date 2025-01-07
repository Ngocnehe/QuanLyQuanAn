package Interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class frmdangky extends JFrame implements ActionListener {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JComboBox<String> cbRole;
    private JButton btnRegister;
    private JLabel lblAlreadyHaveAccount;

    public frmdangky() {
        setTitle("Đăng ký");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildGUI();
    }

    private void buildGUI() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("ĐĂNG KÝ", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Tên đăng nhập:"));
        tfUsername = new JTextField();
        panel.add(tfUsername);
        panel.add(new JLabel("Mật khẩu:"));
        pfPassword = new JPasswordField();
        panel.add(pfPassword);
        panel.add(new JLabel("Vai trò:"));
        cbRole = new JComboBox<>(new String[]{"Quản lý", "Nhân viên"});
        panel.add(cbRole);
        btnRegister = new JButton("Đăng ký");
        btnRegister.addActionListener(this);
        panel.add(btnRegister);

        lblAlreadyHaveAccount = new JLabel("Bạn đã có tài khoản?");
        lblAlreadyHaveAccount.setForeground(Color.BLUE.darker());
        lblAlreadyHaveAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblAlreadyHaveAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new frmdangnhap().setVisible(true);
                dispose();
            }
        });
        panel.add(lblAlreadyHaveAccount);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            String username = tfUsername.getText();
            String password = new String(pfPassword.getPassword());
            String role = (String) cbRole.getSelectedItem();
            if (validateInput(username, password)) {
                registerUser(username, password, role);
            }
        }
    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống.");
            return false;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống.");
            return false;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự.");
            return false;
        }
        return true;
    }

    private void registerUser(String username, String password, String role) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqa", "root", "");
            String sql = "INSERT INTO dangnhap (tendangnhap, matkhau, vaitro) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            conn.close();

            new frmdangnhap().setVisible(true);
            dispose();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi đăng ký tài khoản.");
        }
    }

    public static void main(String[] args) {
        new frmdangky().setVisible(true);
    }
}
