package Interface;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

public class FrmDonHang extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNhanVien;
    private JSpinner spnNgayTao;
    private JTextField txtMonAn;
    private JSpinner spnSoLuong;
    private JTextField txtTien;
    private JTextField txtTongTien;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 752, 529);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("THÔNG TIN ĐƠN HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblNhanVien = new JLabel("Nhân Viên Thanh Toán:");
        JLabel lblNgayTao = new JLabel("Ngày Tạo:");
        JLabel lblMonAn = new JLabel("Món Ăn:");
        JLabel lblSoLuong = new JLabel("Số Lượng:");
        JLabel lblTien = new JLabel("Tiền:");
        JLabel lblTongTien = new JLabel("Tổng Tiền:");

        txtNhanVien = new JTextField();
        txtNhanVien.setColumns(10);

        // Tạo SpinnerDateModel với giá trị hiện tại
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        spnNgayTao = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgayTao, "yyyy-MM-dd HH:mm:ss");
        spnNgayTao.setEditor(dateEditor);

        txtMonAn = new JTextField();
        txtMonAn.setColumns(10);

        spnSoLuong = new JSpinner();
        spnSoLuong.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        txtTien = new JTextField();
        txtTien.setColumns(10);

        txtTongTien = new JTextField();
        txtTongTien.setColumns(10);
        
        JButton btnLuu = new JButton("Tạo");
        
        JButton btnThoat = new JButton("Thoát");

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(185)
        			.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
        			.addGap(237))
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(30)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblMonAn, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNhanVien, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblTongTien, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(txtTongTien)
        					.addComponent(txtNhanVien, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
        					.addComponent(txtMonAn, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
        				.addComponent(btnLuu, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(lblSoLuong, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblNgayTao, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(spnSoLuong, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addComponent(spnNgayTao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(lblTien, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(txtTien, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
        				.addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(306, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(20)
        			.addComponent(lblTitle)
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNhanVien)
        				.addComponent(txtNhanVien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNgayTao)
        				.addComponent(spnNgayTao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblMonAn)
        				.addComponent(txtMonAn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblSoLuong)
        				.addComponent(spnSoLuong, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTien)
        				.addComponent(txtTien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(41)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTongTien)
        				.addComponent(txtTongTien, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(56)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(btnThoat, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        				.addComponent(btnLuu, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        			.addGap(183))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
