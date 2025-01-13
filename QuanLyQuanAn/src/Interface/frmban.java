package Interface;

import java.awt.Font;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class frmban extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtTenBan;
    private JTextField txtId;
    private JTable tableThongTin;
    private DefaultTableModel tableModel;

    private Connection conn;
    private Statement stmt;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                frmban frame = new frmban();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public frmban() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("THÔNG TIN BÀN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblTenBan = new JLabel("Tên Bàn:");
        JLabel lblSoCho = new JLabel("Số Chỗ Ngồi:");

        txtTenBan = new JTextField();
        txtTenBan.setColumns(10);

        JSpinner spnSoCho = new JSpinner(new SpinnerNumberModel(1, 1, 21, 1));

        JButton btnThem = new JButton("Thêm");
        JButton btnXoa = new JButton("Xóa");

        btnThem.addActionListener(e -> {
            String tenBan = txtTenBan.getText().trim();
            int soCho = (int) spnSoCho.getValue();

            if (soCho > 21) {
                spnSoCho.setValue(21);
                JOptionPane.showMessageDialog(this, "Số chỗ ngồi không được vượt quá 21!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else if (tenBan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                // Insert new data into the database
            	try {
            	    String query = "INSERT INTO banan (tenban, sochongoi) VALUES (?, ?)";
            	    PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            	    ps.setString(1, tenBan);
            	    ps.setInt(2, soCho);
            	    int result = ps.executeUpdate();

            	    if (result > 0) {
            	        ResultSet rs = ps.getGeneratedKeys();
            	        if (rs.next()) {
            	            int newId = rs.getInt(1);
            	            JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!\nID: " + newId +
            	                    "\nTên bàn: " + tenBan + "\nSố chỗ ngồi: " + soCho, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            	        }
            	        loadDataFromDatabase();
            	    } else {
            	        JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            	    }
            	} catch (SQLException ex) {
            	    ex.printStackTrace();
            	    JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            	}

            }
        });

        btnXoa.addActionListener(e -> {
            txtTenBan.setText("");
            spnSoCho.setValue(1);
        });

        JLabel lblBananId = new JLabel("Id:");

        txtId = new JTextField();
        txtId.setColumns(10);
        
        //nút sửa
        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> {
            String id = txtId.getText().trim();
            String tenBan = txtTenBan.getText().trim();
            int soCho = (int) spnSoCho.getValue();

            // Kiểm tra giá trị nhập vào
            if (id.isEmpty() || tenBan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                int idBan = Integer.parseInt(id); // Kiểm tra ID có phải số nguyên
                if (soCho < 1 || soCho > 21) {
                    JOptionPane.showMessageDialog(this, "Số chỗ ngồi phải nằm trong khoảng từ 1 đến 21!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Xác nhận sửa
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật bàn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // Cập nhật dữ liệu vào cơ sở dữ liệu
                try {
                    String query = "UPDATE banan SET tenban = ?, sochongoi = ? WHERE id_ban = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, tenBan);
                    ps.setInt(2, soCho);
                    ps.setInt(3, idBan);

                    int result = ps.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Thông tin đã được cập nhật thành công!\nID: " + id +
                                "\nTên bàn: " + tenBan + "\nSố chỗ ngồi: " + soCho, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        loadDataFromDatabase(); // Cập nhật lại bảng
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy bàn với ID: " + id, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!\nChi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        //nút xóa
        btnXoa.addActionListener(e -> {
            int selectedRow = tableThongTin.getSelectedRow(); // Lấy hàng được chọn
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bàn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            try {
                // Lấy ID của hàng được chọn
                int id = (int) tableModel.getValueAt(selectedRow, 0);

                // Xóa bản ghi trong cơ sở dữ liệu
                String query = "DELETE FROM banan WHERE id_ban = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                int result = ps.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Đã xóa bàn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase(); // Cập nhật lại bảng
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa bàn này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!\nChi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Initialize table and its model
        tableThongTin = new JTable();
        tableModel = new DefaultTableModel(new String[] { "ID", "Tên Bàn", "Số Chỗ Ngồi" }, 0);
        tableThongTin.setModel(tableModel);

        // Customize table header
        tableThongTin.getTableHeader().setReorderingAllowed(false);
        tableThongTin.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap(182, Short.MAX_VALUE)
                    .addComponent(lblTitle)
                    .addGap(112))
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(30)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblSoCho, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(spnSoCho, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblBananId, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTenBan, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
                            .addGap(37)
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                .addComponent(txtTenBan, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addComponent(txtId))))
                    .addContainerGap(157, Short.MAX_VALUE))
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(57)
                    .addComponent(btnThem, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnSua, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnXoa, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(97, Short.MAX_VALUE))
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tableThongTin, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(20)
                    .addComponent(lblTitle)
                    .addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBananId))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(txtTenBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTenBan))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(spnSoCho, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoCho))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnSua)
                        .addComponent(btnXoa)
                        .addComponent(btnThoat))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(tableThongTin, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);

        connectDatabase();
        loadDataFromDatabase();
    }

    private void connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/quanlyquanan";
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromDatabase() {
        try {
            String query = "SELECT * FROM banan";
            ResultSet rs = stmt.executeQuery(query);

            tableModel.setRowCount(0);

            while (rs.next()) {
                int id = rs.getInt("id_ban");
                String tenBan = rs.getString("tenban");
                int soCho = rs.getInt("sochongoi");
                tableModel.addRow(new Object[]{id, tenBan, soCho});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
