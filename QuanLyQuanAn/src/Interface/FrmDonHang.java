package Interface;

import java.sql.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;


public class FrmDonHang extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JSpinner spnNgayTao;
    private JSpinner spnSoLuong;
    private JTextField txtTongTien;
    private JTable table;
    private JComboBox<String> comboBox_ThongTinNhanVien;
    private JComboBox<String> comboBox_MaBan;
    private JList<String> list_MonAn;
    private Connection connection;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FrmDonHang frame = new FrmDonHang();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmDonHang() {
        setTitle("Quản Lý Đơn Hàng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("THÔNG TIN ĐƠN HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblNhanVien = new JLabel("Nhân Viên Thanh Toán:");
        JLabel lblNgayTao = new JLabel("Ngày Tạo:");
        JLabel lblMonAn = new JLabel("Món Ăn:");
        JLabel lblSoLuong = new JLabel("Số Lượng:");
        JLabel lblTongTien = new JLabel("Tổng Tiền:");
        JLabel lblMaBan = new JLabel("Mã Bàn:");

        spnNgayTao = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        spnNgayTao.setEditor(new JSpinner.DateEditor(spnNgayTao, "yyyy-MM-dd HH:mm:ss"));
        spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        txtTongTien.setColumns(10);

        comboBox_ThongTinNhanVien = new JComboBox<>();
        comboBox_MaBan = new JComboBox<>();
        list_MonAn = new JList<>();
        list_MonAn.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPaneMonAn = new JScrollPane(list_MonAn);

        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnThoat = new JButton("Thoát");

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        btnThoat.addActionListener(e -> System.exit(0));
        btnThem.addActionListener(e -> addOrder());
        btnSua.addActionListener(e -> updateOrder());
        btnXoa.addActionListener(e -> deleteOrder());

        spnSoLuong.addChangeListener(e -> updateTongTien());
        list_MonAn.addListSelectionListener(e -> updateTongTien());

        connectDatabase();
        loadNhanVien();
        loadMonAn();
        loadMaBan();
        loadTableData();

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNhanVien)
                        .addComponent(lblMonAn)
                        .addComponent(lblSoLuong)
                        .addComponent(lblNgayTao)
                        .addComponent(lblTongTien)
                        .addComponent(lblMaBan))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(comboBox_ThongTinNhanVien, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrollPaneMonAn)
                        .addComponent(spnSoLuong, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(spnNgayTao)
                        .addComponent(txtTongTien)
                        .addComponent(comboBox_MaBan, 0, 150, Short.MAX_VALUE))
                    .addGap(18)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(150)
                    .addComponent(btnThem)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnSua)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnXoa)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnThoat)
                    .addContainerGap(380, Short.MAX_VALUE))
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(200)
                    .addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(200))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblTitle)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNhanVien)
                        .addComponent(comboBox_ThongTinNhanVien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblMaBan)
                        .addComponent(comboBox_MaBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblMonAn)
                        .addComponent(scrollPaneMonAn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblSoLuong)
                        .addComponent(spnSoLuong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNgayTao)
                        .addComponent(spnNgayTao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblTongTien)
                        .addComponent(txtTongTien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnSua)
                        .addComponent(btnXoa)
                        .addComponent(btnThoat))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlyquanan", "root", "");
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadNhanVien() {
        try {
            String query = "SELECT tennv FROM nhanvien";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                comboBox_ThongTinNhanVien.addItem(rs.getString("tennv"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMaBan() {
        try {
            String query = "SELECT id_ban FROM banan";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                comboBox_MaBan.addItem(rs.getString("id_ban"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMonAn() {
        try {
            String query = "SELECT tenmon FROM monan";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            DefaultListModel<String> listModel = new DefaultListModel<>();
            while (rs.next()) {
                listModel.addElement(rs.getString("tenmon"));
            }
            list_MonAn.setModel(listModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        try {
            String query = "SELECT donhang.id_donhang, donhang.id_ban, nhanvien.tennv, donhang.ngaytao, donhang.tongtien\r\n"
            		+ "FROM donhang\r\n"
            		+ "INNER JOIN nhanvien ON donhang.id_nhanvien = nhanvien.id_nhanvien;\r\n"
            		+ "";
            Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query);
            
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã Đơn Hàng");
            model.addColumn("Mã Bàn");
            model.addColumn("Nhân Viên");
            model.addColumn("Ngày Tạo");
            model.addColumn("Tổng Tiền");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_donhang"),
                    rs.getInt("id_ban"),
                    rs.getString("tennv"),
                    rs.getString("ngaytao"),
                    rs.getDouble("tongtien")
                });
            }
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTongTien() {
        try {
            List<String> selectedMonAn = list_MonAn.getSelectedValuesList();
            int soLuong = (Integer) spnSoLuong.getValue();
            double tongTien = 0;

            for (String monAn : selectedMonAn) {
                String query = "SELECT gia FROM monan WHERE tenmon = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, monAn);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    tongTien += rs.getDouble("gia") * soLuong;
                }
            }

            txtTongTien.setText(String.valueOf(tongTien));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addOrder() {
        try {
            String tenNhanVien = comboBox_ThongTinNhanVien.getSelectedItem().toString();
            List<String> selectedMonAn = list_MonAn.getSelectedValuesList();
            String ngayTao = ((JSpinner.DateEditor) spnNgayTao.getEditor()).getFormat().format(spnNgayTao.getValue());
            double tongTien = Double.parseDouble(txtTongTien.getText());
            int idBanan = Integer.parseInt(comboBox_MaBan.getSelectedItem().toString());

            String getNhanVienQuery = "SELECT id_nhanvien FROM nhanvien WHERE tennv = ?";
            PreparedStatement getNhanVienStmt = connection.prepareStatement(getNhanVienQuery);
            getNhanVienStmt.setString(1, tenNhanVien);
            ResultSet rsNhanVien = getNhanVienStmt.executeQuery();

            if (!rsNhanVien.next()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                return;
            }

            int idNhanVien = rsNhanVien.getInt("id_nhanvien");

            String insertQuery = "INSERT INTO donhang (id_ban, id_nhanvien, ngaytao, tongtien) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insertQuery);
            ps.setInt(1, idBanan);
            ps.setInt(2, idNhanVien);
            ps.setString(3, ngayTao);
            ps.setDouble(4, tongTien);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Thêm đơn hàng thành công!");
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOrder() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần sửa!");
                return;
            }

            int idDonHang = (Integer) table.getValueAt(selectedRow, 0);
            String tenNhanVien = comboBox_ThongTinNhanVien.getSelectedItem().toString();
            List<String> selectedMonAn = list_MonAn.getSelectedValuesList();
            String ngayTao = ((JSpinner.DateEditor) spnNgayTao.getEditor()).getFormat().format(spnNgayTao.getValue());
            double tongTien = Double.parseDouble(txtTongTien.getText());
            int idBanan = Integer.parseInt(comboBox_MaBan.getSelectedItem().toString());

            String getNhanVienQuery = "SELECT id_nhanvien FROM nhanvien WHERE tennv = ?";
            PreparedStatement getNhanVienStmt = connection.prepareStatement(getNhanVienQuery);
            getNhanVienStmt.setString(1, tenNhanVien);
            ResultSet rsNhanVien = getNhanVienStmt.executeQuery();

            if (!rsNhanVien.next()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                return;
            }

            int idNhanVien = rsNhanVien.getInt("id_nhanvien");

            String updateQuery = "UPDATE donhang SET id_ban = ?, id_nhanvien = ?, ngaytao = ?, tongtien = ? WHERE id_donhang = ?";
            PreparedStatement ps = connection.prepareStatement(updateQuery);
            ps.setInt(1, idBanan);
            ps.setInt(2, idNhanVien);
            ps.setString(3, ngayTao);
            ps.setDouble(4, tongTien);
            ps.setInt(5, idDonHang);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cập nhật đơn hàng thành công!");
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần xóa!");
                return;
            }

            int idDonHang = (Integer) table.getValueAt(selectedRow, 0);

            String deleteQuery = "DELETE FROM donhang WHERE id_donhang = ?";
            PreparedStatement ps = connection.prepareStatement(deleteQuery);
            ps.setInt(1, idDonHang);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Xóa đơn hàng thành công!");
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

