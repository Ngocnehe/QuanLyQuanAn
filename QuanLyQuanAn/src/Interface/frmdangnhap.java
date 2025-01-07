package Interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class frmdangnhap extends JFrame implements ActionListener {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin, btnExit;
    private JLabel lblTitle, lblRegister;

    public frmdangnhap() {
        setTitle("Đăng nhập");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildGUI();
    }

    private void buildGUI() {
        setLayout(new BorderLayout());

        lblTitle = new JLabel("ĐĂNG NHẬP", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10)); // Thay đổi số hàng thành 5
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Tên đăng nhập:"));
        tfUsername = new JTextField();
        panel.add(tfUsername);
        panel.add(new JLabel("Mật khẩu:"));
        pfPassword = new JPasswordField();
        panel.add(pfPassword);
        btnLogin = new JButton("Đăng nhập");
        btnLogin.addActionListener(this);
        panel.add(btnLogin);
        btnExit = new JButton("Thoát");
        btnExit.addActionListener(this);
        panel.add(btnExit);

        lblRegister = new JLabel("Bạn muốn đăng ký tài khoản?");
        lblRegister.setForeground(Color.BLUE.darker());
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new frmdangky().setVisible(true);
                dispose();
            }
        });
        panel.add(lblRegister);

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String username = tfUsername.getText();
            String password = new String(pfPassword.getPassword());
            loginUser(username, password);
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    private void loginUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlqa", "root", "");
            String sql = "SELECT * FROM dangnhap WHERE tendangnhap = ? AND matkhau = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("vaitro");
                if ("Quản lý".equals(role)) {
                    JOptionPane.showMessageDialog(this, "Chào mừng, Quản lý!");
                    new frmtrangchu().setVisible(true); // Mở giao diện Quản lý
                    dispose();
                } else if ("Nhân viên".equals(role)) {
                    JOptionPane.showMessageDialog(this, "Chào mừng, Nhân viên!");
                    new frmtrangchu().setVisible(true); // Mở giao diện Nhân viên
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không hợp lệ.");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi đăng nhập tài khoản.");
        }
    }

    public static void main(String[] args) {
        new frmdangnhap().setVisible(true);
    }
}
