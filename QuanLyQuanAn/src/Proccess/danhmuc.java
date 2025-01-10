package Proccess;

import java.sql.*;
import database.Connect;

public class danhmuc {
    private Connect conn = new Connect();

    // Kết nối SQL
    public void connectSQL() throws SQLException {
        conn.connectSQL();
    }

    // Thêm danh mục
    public void themDanhMuc(String idDanhMuc, String tenDanhMuc) throws SQLException {
        String sql = "INSERT INTO danhmuc (id_danhmuc, tendanhmuc) VALUES (?, ?)";
        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, idDanhMuc);
            ps.setString(2, tenDanhMuc);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi thêm danh mục.");
        }
    }

    // Cập nhật danh mục
    public void suaDanhMuc(String idDanhMuc, String tenDanhMuc) throws SQLException {
        String sql = "UPDATE danhmuc SET tendanhmuc = ? WHERE id_danhmuc = ?";
        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, tenDanhMuc);
            ps.setString(2, idDanhMuc);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi cập nhật danh mục.");
        }
    }

    // Xóa danh mục
    public void xoaDanhMuc(String idDanhMuc) throws SQLException {
        String sql = "DELETE FROM danhmuc WHERE id_danhmuc = ?";
        try (PreparedStatement ps = conn.getConnection().prepareStatement(sql)) {
            ps.setString(1, idDanhMuc);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi xóa danh mục.");
        }
    }

    // Lấy tất cả danh mục
    public ResultSet getAllDanhMuc() throws SQLException {
        String sql = "SELECT * FROM danhmuc";
        Statement stmt = conn.getConnection().createStatement();
        return stmt.executeQuery(sql);
    }
}