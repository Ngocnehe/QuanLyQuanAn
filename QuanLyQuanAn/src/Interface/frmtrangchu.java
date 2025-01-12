package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Form Trang Chủ
public class frmtrangchu extends JFrame {
	// Các biến thành phần của giao diện
	private JPanel mainPanel;
	private JPanel menuPanel;
	private JPanel contentPanel;
	private JButton btnDangNhap, btnDangKy, btnNhanVien, btnMonAn, btnDanhMuc, btnBanAn, btnDonDatBan, btnDonHang,
			btnThongKe;
	private JLabel lblTenQuan, lblLogo;

	public frmtrangchu() {
		// Cấu hình frame
		setTitle("Trang Chủ Quản Lý Quán Ăn");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Panel chính chứa menu và nội dung
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		// Panel menu bên trái chứa các nút
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(10, 1, 5, 5)); // 10 dòng, 1 cột cho các nút
		menuPanel.setBackground(new Color(255, 235, 205)); // Màu nền cho menu

		// Tạo các nút
		btnDangNhap = new JButton("Đăng Nhập");
		btnDangKy = new JButton("Đăng Ký");
		btnNhanVien = new JButton("Nhân Viên");
		btnMonAn = new JButton("Món Ăn");
		btnDanhMuc = new JButton("Danh Mục");
		btnBanAn = new JButton("Bàn Ăn");
		btnDonDatBan = new JButton("Đơn Đặt Bàn");
		btnDonHang = new JButton("Đơn Hàng");
		btnThongKe = new JButton("Thống Kê");

		// Cài đặt màu sắc cho các nút
		btnDangNhap.setBackground(new Color(70, 130, 180)); // Màu xanh dương đậm
		btnDangNhap.setForeground(Color.WHITE);
		btnDangKy.setBackground(new Color(70, 130, 180));
		btnDangKy.setForeground(Color.WHITE);
		btnNhanVien.setBackground(new Color(70, 130, 180));
		btnNhanVien.setForeground(Color.WHITE);
		btnMonAn.setBackground(new Color(70, 130, 180));
		btnMonAn.setForeground(Color.WHITE);
		btnDanhMuc.setBackground(new Color(70, 130, 180));
		btnDanhMuc.setForeground(Color.WHITE);
		btnBanAn.setBackground(new Color(70, 130, 180));
		btnBanAn.setForeground(Color.WHITE);
		btnDonDatBan.setBackground(new Color(70, 130, 180));
		btnDonDatBan.setForeground(Color.WHITE);
		btnDonHang.setBackground(new Color(70, 130, 180));
		btnDonHang.setForeground(Color.WHITE);
		btnThongKe.setBackground(new Color(70, 130, 180));
		btnThongKe.setForeground(Color.WHITE);

		// Thêm các nút vào menuPanel
		menuPanel.add(btnDangNhap);
		menuPanel.add(btnDangKy);
		menuPanel.add(btnNhanVien);
		menuPanel.add(btnMonAn);
		menuPanel.add(btnDanhMuc);
		menuPanel.add(btnBanAn);
		menuPanel.add(btnDonDatBan);
		menuPanel.add(btnDonHang);
		menuPanel.add(btnThongKe);

		// Panel nội dung
		contentPanel = new JPanel();
		CardLayout cardLayout = new CardLayout();
		contentPanel.setLayout(cardLayout);

		// Các form thật sẽ được hiển thị khi nhấn nút
		JPanel panelDangNhap = (JPanel) new frmdangnhap().getContentPane();
		JPanel panelDangKy = (JPanel) new frmdangky().getContentPane();
		JPanel panelNhanVien = (JPanel) new frmNhanVien().getContentPane();
		JPanel panelMonAn = (JPanel) new frmMonAn().getContentPane();
		JPanel panelDanhMuc = (JPanel) new frmDanhmuc().getContentPane();
		JPanel panelThongKe = (JPanel) new ThongKeDoanhThu().getContentPane();

		// Thêm các panel vào contentPanel sử dụng CardLayout
		contentPanel.add(panelDangNhap, "Đăng Nhập");
		contentPanel.add(panelDangKy, "Đăng Ký");
		contentPanel.add(panelNhanVien, "Nhân Viên");
		contentPanel.add(panelMonAn, "Món Ăn");
		contentPanel.add(panelDanhMuc, "Danh Mục");
		contentPanel.add(panelThongKe, "Thống Kê");

		// Tạo panel cho phần tiêu đề (Logo và tên quán)
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
		lblLogo = new JLabel(new ImageIcon("path/to/logo.png")); // Đường dẫn đến logo
		lblTenQuan = new JLabel("Tên Quán Ăn");
		lblTenQuan.setFont(new Font("Arial", Font.BOLD, 24));
		lblTenQuan.setForeground(new Color(139, 69, 19)); // Màu nâu cho tên quán
		headerPanel.add(lblLogo);
		headerPanel.add(lblTenQuan);

		// Thêm headerPanel, menuPanel, contentPanel vào mainPanel
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(menuPanel, BorderLayout.WEST);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// Thêm panel chính vào frame
		add(mainPanel);

		// Lắng nghe sự kiện của các nút bấm
		btnDangNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Đăng Nhập");
			}
		});

		btnDangKy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Đăng Ký");
			}
		});

		btnNhanVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Nhân Viên");
			}
		});

		btnMonAn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Món Ăn");
			}
		});

		btnDanhMuc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Danh Mục");
			}
		});

		btnBanAn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Bàn Ăn");
			}
		});

		btnDonDatBan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Đơn Đặt Bàn");
			}
		});

		btnDonHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Đơn Hàng");
			}
		});

		btnThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Thống Kê");
			}
		});
	}

	public static void main(String[] args) {
		new frmtrangchu().setVisible(true);
	}
}
