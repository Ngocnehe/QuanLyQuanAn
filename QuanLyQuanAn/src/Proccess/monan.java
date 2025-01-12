package Proccess;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;

public class monan {
	private Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/quanlyquanan";
		String user = "root";
		String password = "";
		return DriverManager.getConnection(url, user, password);
	}

	public void loadData(JTable table) {
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT m.id_monan, d.tendanhmuc, m.tenmon, m.gia, m.mota "
						+ "FROM monan m " + "JOIN danhmuc d ON m.id_danhmuc = d.id_danhmuc");
				ResultSet rs = ps.executeQuery()) {
			table.setModel(buildTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadDanhMuc(JComboBox<String> comboBox, HashMap<String, String> danhMucMap) {
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT id_danhmuc, tendanhmuc FROM danhmuc");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				String idDanhMuc = rs.getString("id_danhmuc");
				String tenDanhMuc = rs.getString("tendanhmuc");
				comboBox.addItem(tenDanhMuc);
				danhMucMap.put(tenDanhMuc, idDanhMuc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addMonAn(String id, String ten, String gia, String moTa, String idDanhMuc) {
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO monan (id_monan, id_danhmuc, tenmon, gia, mota) VALUES (?, ?, ?, ?, ?)")) {
			ps.setString(1, id);
			ps.setString(2, idDanhMuc);
			ps.setString(3, ten);
			ps.setString(4, gia);
			ps.setString(5, moTa);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateMonAn(String id, String ten, String gia, String moTa, String idDanhMuc) {
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"UPDATE monan SET id_danhmuc = ?, tenmon = ?, gia = ?, mota = ? WHERE id_monan = ?")) {
			ps.setString(1, idDanhMuc);
			ps.setString(2, ten);
			ps.setString(3, gia);
			ps.setString(4, moTa);
			ps.setString(5, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMonAn(String id) {
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement("DELETE FROM monan WHERE id_monan = ?")) {
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String[] getRowData(JTable table, int row) {
		return new String[] { table.getValueAt(row, 0).toString(), table.getValueAt(row, 1).toString(),
				table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(),
				table.getValueAt(row, 4).toString() };
	}

	private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		String[] columnNames = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			columnNames[i] = metaData.getColumnName(i + 1);
		}

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		while (rs.next()) {
			Object[] row = new Object[columnCount];
			for (int i = 0; i < columnCount; i++) {
				row[i] = rs.getObject(i + 1);
			}
			model.addRow(row);
		}

		return model;
	}
}
