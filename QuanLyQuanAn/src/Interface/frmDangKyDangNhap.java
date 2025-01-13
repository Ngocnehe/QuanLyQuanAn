package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Form Đăng Nhập và Đăng Ký
public class frmDangKyDangNhap extends JFrame {
    // Các biến thành phần của giao diện
    private JPanel mainPanel;
    private JButton btnDangNhap, btnDangKy;
    private JLabel lblTitle, lblLogo;

    public frmDangKyDangNhap() {
        // Cấu hình frame
        setTitle("Đăng Nhập hoặc Đăng Ký");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel chính chứa các nút
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Màu nền cho form chính

        // Tạo panel chứa tiêu đề và logo
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(255, 228, 225)); // Màu nền cho header
        lblLogo = new JLabel(new ImageIcon("path/to/logo.png")); // Đường dẫn đến logo
        lblTitle = new JLabel("Chào Mừng Đến Với Quán Ăn");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(139, 69, 19)); // Màu nâu cho tiêu đề
        headerPanel.add(lblLogo);
        headerPanel.add(lblTitle);

        // Tạo panel chứa các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 dòng, 1 cột với khoảng cách 10 pixel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Thêm khoảng cách xung quanh

        // Tạo các nút
        btnDangNhap = new JButton("Đăng Nhập");
        btnDangKy = new JButton("Đăng Ký");

        // Cài đặt phông chữ và màu sắc cho các nút
        btnDangNhap.setFont(new Font("Arial", Font.PLAIN, 18));
        btnDangNhap.setBackground(new Color(70, 130, 180)); // Màu xanh dương đậm
        btnDangNhap.setForeground(Color.WHITE);
        btnDangKy.setFont(new Font("Arial", Font.PLAIN, 18));
        btnDangKy.setBackground(new Color(70, 130, 180));
        btnDangKy.setForeground(Color.WHITE);

        // Thêm các nút vào buttonPanel
        buttonPanel.add(btnDangNhap);
        buttonPanel.add(btnDangKy);

        // Thêm headerPanel và buttonPanel vào mainPanel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Thêm panel chính vào frame
        add(mainPanel);

        // Lắng nghe sự kiện của các nút bấm
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mở form Đăng Nhập
                new frmDangNhap().setVisible(true);
                dispose(); // Đóng form hiện tại
            }
        });

        btnDangKy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mở form Đăng Ký
                new frmDangKy().setVisible(true);
                dispose(); // Đóng form hiện tại
            }
        });
    }

    public static void main(String[] args) {
        new frmDangKyDangNhap().setVisible(true);
    }
}
