package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtf_user;
	private JTextField txtf_pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_centro = new JPanel();
		contentPane.add(panel_centro, BorderLayout.CENTER);
		panel_centro.setLayout(new GridLayout(3, 2, 0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_centro.add(panel_6);
		
		JLabel lblNewLabel = new JLabel("usuario : ");
		panel_6.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_centro.add(panel_1);
		
		txtf_user = new JTextField();
		panel_1.add(txtf_user);
		txtf_user.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_centro.add(panel_4);
		
		JLabel lblNewLabel_1 = new JLabel("Puerto : ");
		panel_4.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_centro.add(panel_2);
		
		txtf_pass = new JTextField();
		panel_2.add(txtf_pass);
		txtf_pass.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_centro.add(panel_5);
		
		JLabel lblNewLabel_2 = new JLabel("");
		panel_5.add(lblNewLabel_2);
		
		JPanel panel_3 = new JPanel();
		panel_centro.add(panel_3);
		
		JButton btn_inicio = new JButton("Iniciar sesión");
		panel_3.add(btn_inicio);
		
		JPanel panel_norte = new JPanel();
		contentPane.add(panel_norte, BorderLayout.NORTH);
		
		JLabel lbl_title = new JLabel("Bienvenido/a!");
		panel_norte.add(lbl_title);
	}

}
