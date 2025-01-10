package Interface;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmDatBan extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtTenBan;
    private JTextField txtSdt;
    private JTextField txtGhiChu;
    private JSpinner spnNgayDat;
    private JTextField txtIdDatBan;
    private JTable tableThongTin;
    private DefaultTableModel tableModel;

    private Connection conn;
    private Statement stmt;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FrmDatBan frame = new FrmDatBan();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmDatBan() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("THÔNG TIN ĐẶT BÀN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblTenBan = new JLabel("Tên Khách hàng:");
        JLabel lblSdt = new JLabel("Số Điện Thoại:");
        JLabel lblNgayDat = new JLabel("Ngày Đặt:");
        JLabel lblGhiChu = new JLabel("Ghi Chú:");

        txtTenBan = new JTextField();
        txtTenBan.setColumns(10);

        txtSdt = new JTextField();
        txtSdt.setColumns(10);
        
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spnNgayDat = new JSpinner(dateModel);

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgayDat, "yyyy-MM-dd");
        spnNgayDat.setEditor(dateEditor);

        txtGhiChu = new JTextField();
        txtGhiChu.setColumns(10);

        JLabel lblDatBan_id = new JLabel("Id đặt bàn:");

        txtIdDatBan = new JTextField();
        txtIdDatBan.setColumns(10);

        JButton btnThem = new JButton("Thêm");
        btnThem.addActionListener(e -> {
            String idDatBan = txtIdDatBan.getText().trim();
            String tenKhachHang = txtTenBan.getText().trim();
            String soDienThoai = txtSdt.getText().trim();
            Date ngayDat = (Date) spnNgayDat.getValue();
            String ghiChu = txtGhiChu.getText().trim();

            if (idDatBan.isEmpty() || tenKhachHang.isEmpty() || soDienThoai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!soDienThoai.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ngayDat.before(new Date())) {
                JOptionPane.showMessageDialog(this, "Ngày đặt không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(ngayDat);

            tableModel.addRow(new Object[]{idDatBan, tenKhachHang, soDienThoai, formattedDate, ghiChu});

            JOptionPane.showMessageDialog(this, "Đặt bàn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(e -> {
            int selectedRow = tableThongTin.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        String[] columnNames = {"ID Đặt Bàn", "Tên Khách Hàng", "Số Điện Thoại", "Ngày Đặt", "Ghi Chú"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableThongTin = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tableThongTin);
        
        JButton btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableThongTin.getSelectedRow();
                if (selectedRow != -1) {
                    txtIdDatBan.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtTenBan.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtSdt.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    spnNgayDat.setValue(tableModel.getValueAt(selectedRow, 3));
                    txtGhiChu.setText(tableModel.getValueAt(selectedRow, 4).toString());

                    btnThem.setText("Cập nhật");
                    btnThem.addActionListener(e1 -> {
                        String idDatBan = txtIdDatBan.getText().trim();
                        String tenKhachHang = txtTenBan.getText().trim();
                        String soDienThoai = txtSdt.getText().trim();
                        Date ngayDat = (Date) spnNgayDat.getValue();
                        String ghiChu = txtGhiChu.getText().trim();

                        if (idDatBan.isEmpty() || tenKhachHang.isEmpty() || soDienThoai.isEmpty()) {
                            JOptionPane.showMessageDialog(FrmDatBan.this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (!soDienThoai.matches("\\d+")) {
                            JOptionPane.showMessageDialog(FrmDatBan.this, "Số điện thoại phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (ngayDat.before(new Date())) {
                            JOptionPane.showMessageDialog(FrmDatBan.this, "Ngày đặt không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try {
                            String updateQuery = "UPDATE datban SET tenkhachhang=?, sodienthoai=?, ngaydat=?, ghichu=? WHERE id_datban=?";
                            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                            pstmt.setString(1, tenKhachHang);
                            pstmt.setString(2, soDienThoai);
                            pstmt.setDate(3, new java.sql.Date(ngayDat.getTime()));
                            pstmt.setString(4, ghiChu);
                            pstmt.setString(5, idDatBan);

                            pstmt.executeUpdate();

                            tableModel.setValueAt(tenKhachHang, selectedRow, 1);
                            tableModel.setValueAt(soDienThoai, selectedRow, 2);
                            tableModel.setValueAt(ngayDat, selectedRow, 3);
                            tableModel.setValueAt(ghiChu, selectedRow, 4);

                            JOptionPane.showMessageDialog(FrmDatBan.this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(FrmDatBan.this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(FrmDatBan.this, "Vui lòng chọn một hàng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(30)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblTenBan)
        						.addComponent(lblSdt)
        						.addComponent(lblNgayDat)
        						.addComponent(lblGhiChu)
        						.addComponent(lblDatBan_id))
        					.addGap(18)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(txtTenBan)
        						.addComponent(txtSdt)
        						.addComponent(spnNgayDat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(txtGhiChu)
        						.addComponent(txtIdDatBan, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(btnThem)
        					.addGap(18)
        					.addComponent(btnXoa)
        					.addGap(18)
        					.addComponent(btnSua, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(btnThoat)))
        			.addContainerGap(252, Short.MAX_VALUE))
        		.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblTitle)
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblDatBan_id)
        				.addComponent(txtIdDatBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTenBan)
        				.addComponent(txtTenBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblSdt)
        				.addComponent(txtSdt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNgayDat)
        				.addComponent(spnNgayDat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblGhiChu)
        				.addComponent(txtGhiChu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnThem)
        				.addComponent(btnXoa)
        				.addComponent(btnSua)
        				.addComponent(btnThoat))
        			.addGap(18)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(60, Short.MAX_VALUE))
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
            String query = "SELECT * FROM datban";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String idDatBan = rs.getString("id_datban");
                String tenKhachHang = rs.getString("tenkhachhang");
                String soDienThoai = rs.getString("sodienthoai");
                Date ngayDat = rs.getDate("ngaydat");
                String ghiChu = rs.getString("ghichu");

                tableModel.addRow(new Object[]{idDatBan, tenKhachHang, soDienThoai, ngayDat, ghiChu});
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
