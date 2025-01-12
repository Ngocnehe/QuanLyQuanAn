package Proccess;

import java.sql.*;

public class nhanvien {
	private static final String URL = "jdbc:mysql://localhost:3306/quanlyquanan";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	// Kết nối cơ sở dữ liệu
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	// Thêm nhân viên
	public void themNhanVien(String id, String name, String phone, String email, String password, String cccd,
			String role) throws SQLException {
		String query = "INSERT INTO nhanvien (id_nhanvien, tennv, sodienthoai, email, matkhau, cccd, vaitro) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, phone);
			ps.setString(4, email);
			ps.setString(5, password);
			ps.setString(6, cccd);
			ps.setString(7, role);
			ps.executeUpdate();
		}
	}

	// Sửa nhân viên
	public void suaNhanVien(String id, String name, String phone, String email, String password, String cccd,
			String role) throws SQLException {
		String query = "UPDATE nhanvien SET tennv = ?, sodienthoai = ?, email = ?, matkhau = ?, cccd = ?, vaitro = ? WHERE id_nhanvien = ?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, name);
			ps.setString(2, phone);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setString(5, cccd);
			ps.setString(6, role);
			ps.setString(7, id);
			ps.executeUpdate();
		}
	}

	// Xóa nhân viên
	public void xoaNhanVien(String id) throws SQLException {
		String query = "DELETE FROM nhanvien WHERE id_nhanvien = ?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id);
			ps.executeUpdate();
		}
	}

	// Lấy dữ liệu nhân viên
	public ResultSet getAllNhanVien() throws SQLException {
		String query = "SELECT * FROM nhanvien";
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		return ps.executeQuery(); // Trả về ResultSet để xử lý ở giao diện
	}

	// Kiểm tra nhân viên có tồn tại hay không
	public boolean nhanVienTonTai(String id) throws SQLException {
		String query = "SELECT COUNT(*) FROM nhanvien WHERE id_nhanvien = ?";
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		}
		return false;
	}
}
