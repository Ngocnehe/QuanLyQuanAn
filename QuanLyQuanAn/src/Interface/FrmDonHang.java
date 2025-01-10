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
import java.awt.event.ActionListener;

public class FrmDonHang extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JSpinner spnNgayTao;
    private JSpinner spnSoLuong;
    private JTextField txtTongTien;
    private JTable table;
    private JComboBox<String> comboBox_ThongTinNhanVien;
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
        setBounds(100, 100, 853, 750);
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

        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        spnNgayTao = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgayTao, "yyyy-MM-dd HH:mm:ss");
        spnNgayTao.setEditor(dateEditor);

        spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        txtTongTien.setColumns(10);

        comboBox_ThongTinNhanVien = new JComboBox<>();

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
        loadTableData();

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(185)
        			.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
        			.addGap(237))
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(30)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNhanVien, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblSoLuong, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblMonAn, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNgayTao, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblTongTien, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(comboBox_ThongTinNhanVien, 0, 270, Short.MAX_VALUE)
        				.addComponent(scrollPaneMonAn, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
        				.addComponent(spnSoLuong, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(txtTongTien, Alignment.LEADING)
        					.addComponent(spnNgayTao, Alignment.LEADING)))
        			.addContainerGap(531, Short.MAX_VALUE))
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(146)
        			.addComponent(btnThem)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnSua)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnXoa)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnThoat)
        			.addContainerGap(595, Short.MAX_VALUE))
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(420, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(20)
        			.addComponent(lblTitle)
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNhanVien)
        				.addComponent(comboBox_ThongTinNhanVien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnThem)
        				.addComponent(btnSua)
        				.addComponent(btnXoa)
        				.addComponent(btnThoat))
        			.addGap(18)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(270, Short.MAX_VALUE))
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

    private void updateTongTien() {
        try {
            List<String> selectedMonAn = list_MonAn.getSelectedValuesList();
            double tongTien = 0;
            int soLuong = (int) spnSoLuong.getValue();  

            for (String monAn : selectedMonAn) {
                String query = "SELECT gia FROM monan WHERE tenmon = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, monAn);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    double gia = rs.getDouble("gia");
                    tongTien += gia * soLuong;  
                }
            }
            txtTongTien.setText(String.valueOf(tongTien));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        try {
            String query = "SELECT donhang.id_donhang, donhang.id_banan, nhanvien.tennv, donhang.ngaytao, donhang.tongtien "
                         + "FROM donhang "
                         + "JOIN nhanvien ON donhang.id_nhanvien = nhanvien.id_nhanvien";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Mã Đơn Hàng");
            model.addColumn("Mã Bàn");
            model.addColumn("Tên Nhân Viên");  
            model.addColumn("Ngày Tạo");
            model.addColumn("Tổng Tiền");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_donhang"),    
                    rs.getInt("id_banan"),      
                    rs.getString("tennv"),      
                    rs.getString("ngaytao"),    
                    rs.getDouble("tongtien")   
                });
            }

            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu lên bảng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void addOrder() {
        try {
            String tenNhanVien = comboBox_ThongTinNhanVien.getSelectedItem().toString();
            List<String> selectedMonAn = list_MonAn.getSelectedValuesList();
            String ngayTao = ((JSpinner.DateEditor) spnNgayTao.getEditor()).getFormat().format(spnNgayTao.getValue());
            String tongTienText = txtTongTien.getText().trim(); 
            double tongTien;

            if (tongTienText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tổng tiền không được để trống!");
                return;
            }

            try {
                tongTien = Double.parseDouble(tongTienText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tổng tiền phải là một số hợp lệ!");
                return;
            }

            if (selectedMonAn.isEmpty() || tongTien == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn và đảm bảo tổng tiền không bằng 0!");
                return;
            }

            String getNhanVienQuery = "SELECT id_nhanvien FROM nhanvien WHERE tennv = ?";
            PreparedStatement getNhanVienStmt = connection.prepareStatement(getNhanVienQuery);
            getNhanVienStmt.setString(1, tenNhanVien);
            ResultSet rsNhanVien = getNhanVienStmt.executeQuery();

            if (!rsNhanVien.next()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                return;
            }

            int idNhanVien = rsNhanVien.getInt("id_nhanvien");

            int idBanan = 1;

            String insertQuery = "INSERT INTO donhang (id_banan, id_nhanvien, ngaytao, tongtien) VALUES (?, ?, ?, ?)";
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

            int maDonHang = (int) table.getValueAt(selectedRow, 0);
            String tenNhanVien = comboBox_ThongTinNhanVien.getSelectedItem().toString();
            String ngayTao = ((JSpinner.DateEditor) spnNgayTao.getEditor()).getFormat().format(spnNgayTao.getValue());
            double tongTien = Double.parseDouble(txtTongTien.getText());

            String getNhanVienQuery = "SELECT id_nhanvien FROM nhanvien WHERE tennv = ?";
            PreparedStatement getNhanVienStmt = connection.prepareStatement(getNhanVienQuery);
            getNhanVienStmt.setString(1, tenNhanVien);
            ResultSet rsNhanVien = getNhanVienStmt.executeQuery();

            if (!rsNhanVien.next()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
                return;
            }

            int idNhanVien = rsNhanVien.getInt("id_nhanvien");

            String query = "UPDATE donhang SET id_nhanvien = ?, ngaytao = ?, tongtien = ? WHERE id_donhang = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, idNhanVien);
            pstmt.setString(2, ngayTao);
            pstmt.setDouble(3, tongTien);
            pstmt.setInt(4, maDonHang);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Sửa đơn hàng thành công!");
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đơn hàng cần xóa!");
                return;
            }

            int maDonHang = (int) table.getValueAt(selectedRow, 0);

            String query = "DELETE FROM donhang WHERE id_donhang = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, maDonHang);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa đơn hàng thành công!");
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa đơn hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}