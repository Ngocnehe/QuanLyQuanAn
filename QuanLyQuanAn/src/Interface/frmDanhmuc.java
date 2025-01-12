package Interface;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import Proccess.danhmuc;

public class frmDanhmuc extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIdDanhMuc;
	private JTextField txtTenDanhMuc;
	private JTable table;
	private danhmuc danhMuc = new danhmuc(); // Khởi tạo đối tượng DanhMuc

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmDanhmuc frame = new frmDanhmuc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public frmDanhmuc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450); // Tăng kích thước cửa sổ
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		JLabel lblDanhMuc = new JLabel("Danh mục");
		lblDanhMuc.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblDanhMuc);

		JPanel panelInput = new JPanel();
		contentPane.add(panelInput);
		panelInput.setLayout(new GridLayout(2, 2));

		JLabel lblIdDanhMuc = new JLabel("ID Danh Mục:");
		panelInput.add(lblIdDanhMuc);

		txtIdDanhMuc = new JTextField();
		panelInput.add(txtIdDanhMuc);
		txtIdDanhMuc.setColumns(10);

		JLabel lblTenDanhMuc = new JLabel("Tên Danh Mục:");
		panelInput.add(lblTenDanhMuc);

		txtTenDanhMuc = new JTextField();
		panelInput.add(txtTenDanhMuc);
		txtTenDanhMuc.setColumns(10);

		JPanel panelButtons = new JPanel();
		contentPane.add(panelButtons);

		JButton btnThem = new JButton("Thêm");
		panelButtons.add(btnThem);

		JButton btnSua = new JButton("Sửa");
		panelButtons.add(btnSua);

		JButton btnXoa = new JButton("Xóa");
		panelButtons.add(btnXoa);

		JButton btnLuu = new JButton("Lưu");
		panelButtons.add(btnLuu);

		JButton btnThoat = new JButton("Thoát");
		panelButtons.add(btnThoat);

		JPanel panelTable = new JPanel();
		contentPane.add(panelTable);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		panelTable.add(scrollPane);

		// Sự kiện Button Thêm
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idDanhMuc = txtIdDanhMuc.getText();
				String tenDanhMuc = txtTenDanhMuc.getText();
				if (!idDanhMuc.isEmpty() && !tenDanhMuc.isEmpty()) {
					try {
						danhMuc.themDanhMuc(idDanhMuc, tenDanhMuc); // Thêm danh mục vào cơ sở dữ liệu
						loadData(); // Tải lại dữ liệu vào bảng
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "ID danh mục và tên danh mục không được để trống.");
				}
			}
		});

		// Sự kiện Button Sửa
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idDanhMuc = txtIdDanhMuc.getText();
				String tenDanhMuc = txtTenDanhMuc.getText();
				if (!idDanhMuc.isEmpty() && !tenDanhMuc.isEmpty()) {
					try {
						danhMuc.suaDanhMuc(idDanhMuc, tenDanhMuc); // Sửa danh mục
						loadData(); // Tải lại dữ liệu vào bảng sau khi sửa
					} catch (SQLException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật danh mục.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn danh mục để sửa.");
				}
			}
		});

		// Sự kiện Button Xóa
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idDanhMuc = txtIdDanhMuc.getText();
				if (!idDanhMuc.isEmpty()) {
					try {
						danhMuc.xoaDanhMuc(idDanhMuc); // Xóa danh mục
						loadData(); // Tải lại dữ liệu vào bảng sau khi xóa
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn danh mục để xóa.");
				}
			}
		});

		// Sự kiện Button Lưu
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Chức năng lưu có thể được thêm vào đây nếu cần, ví dụ lưu vào file hoặc cơ sở
				// dữ liệu
				JOptionPane.showMessageDialog(null, "Dữ liệu đã được lưu.");
			}
		});

		// Sự kiện Button Thoát
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // Đóng ứng dụng khi nhấn "Thoát"
			}
		});

		// Tải dữ liệu từ cơ sở dữ liệu
		loadData();

		// Sự kiện chọn dòng trong bảng
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					// Lấy dữ liệu từ dòng được chọn
					String idDanhMuc = table.getValueAt(row, 0).toString(); // Cột 0 là ID danh mục
					String tenDanhMuc = table.getValueAt(row, 1).toString(); // Cột 1 là Tên danh mục

					// Điền dữ liệu vào các text field
					txtIdDanhMuc.setText(idDanhMuc);
					txtTenDanhMuc.setText(tenDanhMuc);
				}
			}
		});
	}

	private void loadData() {
		try {
			ResultSet rs = danhMuc.getAllDanhMuc(); // Lấy tất cả danh mục từ cơ sở dữ liệu
			table.setModel(buildTableModel(rs)); // Hiển thị dữ liệu vào bảng
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Phương thức xây dựng mô hình bảng từ ResultSet
	public static javax.swing.table.DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		java.sql.ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		java.util.Vector<String> columnNames = new java.util.Vector<String>();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		java.util.Vector<java.util.Vector<Object>> data = new java.util.Vector<java.util.Vector<Object>>();
		while (rs.next()) {
			java.util.Vector<Object> row = new java.util.Vector<Object>();
			for (int column = 1; column <= columnCount; column++) {
				row.add(rs.getObject(column));
			}
			data.add(row);
		}
		return new javax.swing.table.DefaultTableModel(data, columnNames);
	}
}
