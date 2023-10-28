package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ayuda extends JInternalFrame {
	private JSeparator separator;
	private JLabel lblAlvaValdiviaCesar;
	private JButton btnCerrar;
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ayuda frame = new ayuda();
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
	public ayuda() {
		setClosable(true);
		setResizable(true);
		setEnabled(false);
		setTitle("About Sistema de Produccion");
		setBounds(10, 10, 630, 362);
		getContentPane().setLayout(null);
		
		separator = new JSeparator();
		separator.setBounds(75, 203, 505, 2);
		getContentPane().add(separator);
		
		lblAlvaValdiviaCesar = new JLabel("Alvac IDE para Desarrolladores de Java (incluye componentes en fase de incubación)");
		lblAlvaValdiviaCesar.setHorizontalAlignment(SwingConstants.LEFT);
		lblAlvaValdiviaCesar.setFont(new Font("Rachana", Font.PLAIN, 14));
		lblAlvaValdiviaCesar.setBounds(40, 12, 635, 32);
		getContentPane().add(lblAlvaValdiviaCesar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Roboto", Font.PLAIN, 14));
		btnCerrar.addActionListener((ActionListener) this);
		btnCerrar.setBounds(270, 240, 102, 32);
		getContentPane().add(btnCerrar);
		
		JLabel lblVersin = new JLabel("Versión: 2023-06 (1.0.0)");
		lblVersin.setHorizontalAlignment(SwingConstants.LEFT);
		lblVersin.setFont(new Font("Rachana", Font.PLAIN, 14));
		lblVersin.setBounds(40, 36, 635, 32);
		getContentPane().add(lblVersin);
		
		JLabel lblDerechosDe = new JLabel("© Derechos de autor de César Alva y otros 2000, 2023. Todos los derechos reservados.");
		lblDerechosDe.setHorizontalAlignment(SwingConstants.LEFT);
		lblDerechosDe.setFont(new Font("Rachana", Font.PLAIN, 14));
		lblDerechosDe.setBounds(40, 57, 635, 32);
		getContentPane().add(lblDerechosDe);
		
		JLabel lblAlvacYEl = new JLabel("Este producto incluye software desarrollado por otros proyectos de código abierto, incluida ");
		lblAlvacYEl.setHorizontalAlignment(SwingConstants.LEFT);
		lblAlvacYEl.setFont(new Font("Rachana", Font.PLAIN, 14));
		lblAlvacYEl.setBounds(40, 80, 635, 32);
		getContentPane().add(lblAlvacYEl);
		
		JLabel lblLaFundacinApache = new JLabel("la Fundación Apache Software, https://www.apache.org/.");
		lblLaFundacinApache.setHorizontalAlignment(SwingConstants.LEFT);
		lblLaFundacinApache.setFont(new Font("Rachana", Font.PLAIN, 14));
		lblLaFundacinApache.setBounds(40, 101, 635, 32);
		getContentPane().add(lblLaFundacinApache);
		
		JLabel lblAcercaDeSistema = new JLabel("Acerca de: Sistema de Producción desarrollado por César Alva. Todos los derechos reservados");
		lblAcercaDeSistema.setHorizontalAlignment(SwingConstants.LEFT);
		lblAcercaDeSistema.setFont(new Font("Rachana", Font.PLAIN, 14));
		lblAcercaDeSistema.setBounds(40, 124, 635, 32);
		getContentPane().add(lblAcercaDeSistema);
		
		JLabel lblLinkedin = new JLabel("LinkedIn:");
		lblLinkedin.setHorizontalAlignment(SwingConstants.LEFT);
		lblLinkedin.setFont(new Font("Rachana", Font.PLAIN, 14));
		lblLinkedin.setBounds(40, 145, 635, 32);
		getContentPane().add(lblLinkedin);
		


	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCerrar) {
			actionPerformedBtnCerrar(e);
		}
	}
	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}

}
