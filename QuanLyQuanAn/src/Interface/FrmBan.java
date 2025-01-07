package Interface;

import java.awt.Font;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class FrmBan extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtTenBan;
    private JTextField txtSdt;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FrmBan frame = new FrmBan();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FrmBan() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("THÔNG TIN ĐẶT BÀN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblTenBan = new JLabel("Tên Bàn:");
        JLabel lblSoCho = new JLabel("Số Chỗ Ngồi:");
        JLabel lblSdt = new JLabel("Số Điện Thoại:");

        txtTenBan = new JTextField();
        txtTenBan.setColumns(10);

        txtSdt = new JTextField();
        txtSdt.setColumns(10);

        JSpinner spnSoCho = new JSpinner(new SpinnerNumberModel(1, 1, 21, 1)); 

        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> {
            String tenBan = txtTenBan.getText().trim();
            String soDienThoai = txtSdt.getText().trim();
            int soCho = (int) spnSoCho.getValue();

            if (soCho > 21) {
                spnSoCho.setValue(21); 
                JOptionPane.showMessageDialog(this, "Số chỗ ngồi không được vượt quá 21!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else if (tenBan.isEmpty() || soDienThoai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else if (!soDienThoai.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thông tin thành công!\nTên bàn: " + tenBan +
                        "\nSố chỗ ngồi: " + soCho + "\nSố điện thoại: " + soDienThoai, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnHuy.addActionListener(e -> {
            txtTenBan.setText("");
            txtSdt.setText("");
            spnSoCho.setValue(1);
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGap(30)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblTenBan, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblSdt, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblSoCho, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(txtSdt)
        						.addComponent(spnSoCho, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addComponent(txtTenBan, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGap(150)
        					.addComponent(btnLuu)
        					.addGap(18)
        					.addComponent(btnHuy)))
        			.addContainerGap(124, Short.MAX_VALUE))
        		.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
        			.addContainerGap(122, Short.MAX_VALUE)
        			.addComponent(lblTitle)
        			.addGap(112))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGap(20)
        			.addComponent(lblTitle)
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblTenBan)
        				.addComponent(txtTenBan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblSdt)
        				.addComponent(txtSdt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblSoCho)
        				.addComponent(spnSoCho, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnLuu)
        				.addComponent(btnHuy))
        			.addContainerGap(85, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
