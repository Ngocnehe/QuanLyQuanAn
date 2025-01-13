package Proccess;

import Interface.frmThongKe;
import javax.swing.*;
import java.sql.*;

public class thongke {
    private frmThongKe frmThongKe;

    public thongke(frmThongKe frmThongKe) {
        this.frmThongKe = frmThongKe;
    }

    public void generateReport(String criteria) {
        String query = "";
        if ("Theo ngày".equals(criteria)) {
            query = "SELECT DATE(ngaytao) AS date, SUM(tongtien) AS total_revenue FROM donhang GROUP BY DATE(ngaytao)";
        } else if ("Theo tháng".equals(criteria)) {
            query = "SELECT YEAR(ngaytao) AS year, MONTH(ngaytao) AS month, SUM(tongtien) AS total_revenue FROM donhang GROUP BY YEAR(ngaytao), MONTH(ngaytao)";
        } else if ("Theo nhân viên".equals(criteria)) {
            query = "SELECT nv.tennv AS employee, SUM(dh.tongtien) AS total_revenue FROM donhang dh JOIN nhanvien nv ON dh.id_nhanvien = nv.id_nhanvien GROUP BY nv.tennv";
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlyquanan", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            JTextArea taResult = frmThongKe.getTaResult();
            taResult.setText("");
            while (rs.next()) {
                if ("Theo ngày".equals(criteria)) {
                    taResult.append("Ngày: " + rs.getString("date") + " - Doanh thu: " + rs.getDouble("total_revenue") + "\n");
                } else if ("Theo tháng".equals(criteria)) {
                    taResult.append("Năm: " + rs.getInt("year") + " Tháng: " + rs.getInt("month") + " - Doanh thu: " + rs.getDouble("total_revenue") + "\n");
                } else if ("Theo nhân viên".equals(criteria)) {
                    taResult.append("Nhân viên: " + rs.getString("employee") + " - Doanh thu: " + rs.getDouble("total_revenue") + "\n");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frmThongKe, "Lỗi truy vấn cơ sở dữ liệu.");
        }
    }
}
