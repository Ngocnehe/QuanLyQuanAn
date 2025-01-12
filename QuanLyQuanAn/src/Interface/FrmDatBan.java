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

public class FrmDatBan extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtTenBan;
    private JTextField txtSdt;
    private JTextField txtGhiChu;
    private JSpinner spnNgayDat;
    private JTextField txtIdDatBan;
    private JTextField txtIdNhanVien;
    private JTextField txtIdBan;
    private JTable tableThongTin;
    private DefaultTableModel tableModel;

    private Connection conn;
    private Statement stmt;

    private JComboBox<String> comboBox_ThongTinNhanVien;
    private JComboBox<String> comboBox_ThongTinBan;

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
        JLabel lblNhanVien = new JLabel("Nhân Viên Thanh Toán:");
        JLabel lblTenBan_1 = new JLabel("Tên Bàn:");
        JLabel lblDatBan_id = new JLabel("Id đặt bàn:");

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

        txtIdDatBan = new JTextField();
        txtIdDatBan.setColumns(10);

        txtIdNhanVien = new JTextField();
        txtIdNhanVien.setColumns(10);
        txtIdNhanVien.setEditable(false);

        txtIdBan = new JTextField();
        txtIdBan.setColumns(10);
        txtIdBan.setEditable(false);

        comboBox_ThongTinNhanVien = new JComboBox<>();
        comboBox_ThongTinBan = new JComboBox<>();

        comboBox_ThongTinNhanVien.addActionListener(e -> {
            String selectedItem = (String) comboBox_ThongTinNhanVien.getSelectedItem();
            if (selectedItem != null) {
                String[] parts = selectedItem.split(" - ");
                if (parts.length == 2) {
                    txtIdNhanVien.setText(parts[1]);
                }
            }
        });

        comboBox_ThongTinBan.addActionListener(e -> {
            String selectedItem = (String) comboBox_ThongTinBan.getSelectedItem();
            if (selectedItem != null) {
                String[] parts = selectedItem.split(" - ");
                if (parts.length == 2) {
                    txtIdBan.setText(parts[1]);
                }
            }
        });

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

        JButton btnThoat = new JButton("Thoát");
        btnThoat.addActionListener(e -> System.exit(0));

        String[] columnNames = {"ID Đặt Bàn", "Tên Khách Hàng", "Số Điện Thoại", "Ngày Đặt", "Ghi Chú"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableThongTin = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableThongTin);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
        		.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblTenBan)
        				.addComponent(lblSdt)
        				.addComponent(lblNgayDat)
        				.addComponent(lblGhiChu)
        				.addComponent(lblNhanVien)
        				.addComponent(lblTenBan_1)
        				.addComponent(lblDatBan_id))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(btnThem)
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(btnThoat))
        				.addComponent(spnNgayDat, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(comboBox_ThongTinNhanVien, Alignment.LEADING, 0, 190, Short.MAX_VALUE)
        				.addComponent(comboBox_ThongTinBan, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(txtIdDatBan, Alignment.LEADING)
        				.addComponent(txtTenBan, Alignment.LEADING)
        				.addComponent(txtSdt, Alignment.LEADING)
        				.addComponent(txtGhiChu, Alignment.LEADING))
        			.addContainerGap(341, Short.MAX_VALUE))
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
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTenBan_1)
        				.addComponent(comboBox_ThongTinBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNhanVien)
        				.addComponent(comboBox_ThongTinNhanVien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTenBan)
        				.addComponent(txtTenBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblSdt)
        				.addComponent(txtSdt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNgayDat)
        				.addComponent(spnNgayDat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblGhiChu)
        				.addComponent(txtGhiChu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnThoat)
        				.addComponent(btnThem))
        			.addGap(18)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
        );
        contentPane.setLayout(gl_contentPane);

        connectDatabase();
        connectDatabase();
        loadNhanVienToComboBox();
        loadBanToComboBox();
    }
        private void connectDatabase() {
            try {
                String url = "jdbc:mysql://localhost:3306/quanlyquanan"; // Cập nhật tên cơ sở dữ liệu
                String user = "root"; // Thay bằng tên người dùng cơ sở dữ liệu của bạn
                String password = ""; // Thay bằng mật khẩu cơ sở dữ liệu của bạn

                conn = DriverManager.getConnection(url, user, password);
                stmt = conn.createStatement();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Kết nối cơ sở dữ liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

        private void loadNhanVienToComboBox() {
            try {
                String query = "SELECT id_nhanvien, tennv FROM nhanvien";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String idNhanVien = rs.getString("id_nhanvien");
                    String tenNhanVien = rs.getString("tennv");
                    comboBox_ThongTinNhanVien.addItem(tenNhanVien);
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void loadBanToComboBox() {
            try {
                String query = "SELECT id_ban, tenban FROM banan";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String idBan = rs.getString("id_ban");
                    String tenBan = rs.getString("tenban");
                    comboBox_ThongTinBan.addItem(tenBan);
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}