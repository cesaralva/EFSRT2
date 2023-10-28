package menu;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import gui.ayuda;
import login.RolesUsuarios;
import produccion.IngresarDato;
import produccion.InsertarDato;
import produccion.MainFrame;
import produccion.ModificarDato;

public class MenuPrincipal extends JFrame implements ActionListener {
	private JMenuBar menuBar;
	private JMenu btnArchivo;
	private JMenu btnMantenimiento;
	private JMenuItem btnConsultar;
	private JMenuItem btnModificar;
	private JMenuItem btnListar;
	private JMenu btnConfiguracion;
	private JMenu btnAlmacen;
	private JMenuItem btnConfiObsequio;
	private JMenuItem btnVender;
	private JMenuItem btnGenerarReportes;
	private JMenu btnAyuda;
	private JMenuItem btnAcercaTienda;
	public static ayuda formAyuda;

	public static InsertarDato formInsertarDato;
	public static MainFrame formMainFrame;

	private JDesktopPane desktopPane;
	private JMenuItem btnExit;
	private JLabel lblNewLabel;
	private JMenuItem btnRolesUsuario;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);

					// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/img/entrega.png")));
		setBounds(100, 100, 928, 583);
		this.setLocationRelativeTo(null);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		btnArchivo = new JMenu("Archivo");
		btnArchivo.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/img/icons8-página-principal-16.png")));
		menuBar.add(btnArchivo);

		btnExit = new JMenuItem("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedBtnExit(e);
			}
		});
		btnArchivo.add(btnExit);

		JMenu btnProduccion = new JMenu("Produccion");
		btnProduccion.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/img/produccion.png")));
		menuBar.add(btnProduccion);

		JMenuItem btnModificarStock = new JMenuItem("Modificar datos");
		btnModificarStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedBtnModificarStock(e);
			}
		});

		JMenuItem btnIngresarStock = new JMenuItem("Ingresar datos");
		btnIngresarStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedBtnIngresarStock(e);
			}
		});
		btnProduccion.add(btnIngresarStock);
		btnProduccion.add(btnModificarStock);

		JMenuItem btnListarStock = new JMenuItem("Listar de Stock");
		btnListarStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedBtnListarStock(e);
			}
		});
		btnProduccion.add(btnListarStock);

		btnMantenimiento = new JMenu("Mantenimiento");
		btnMantenimiento.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/img/mantenimiento.png")));
		menuBar.add(btnMantenimiento);

		btnConsultar = new JMenuItem("Consultar ");
		btnConsultar.addActionListener(this);

		btnMantenimiento.add(btnConsultar);

		btnModificar = new JMenuItem("Modificar ");
		btnModificar.addActionListener(this);
		btnMantenimiento.add(btnModificar);

		btnListar = new JMenuItem("Listar ");
		btnListar.addActionListener(this);

		btnMantenimiento.add(btnListar);

		btnAlmacen = new JMenu("Armacen");
		btnAlmacen.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/img/venta.png")));
		menuBar.add(btnAlmacen);

		btnVender = new JMenuItem("Kardex");
		btnVender.addActionListener(this);
		btnAlmacen.add(btnVender);

		btnGenerarReportes = new JMenuItem("Generar Reportes");
		btnGenerarReportes.addActionListener(this);
		btnAlmacen.add(btnGenerarReportes);

		btnConfiguracion = new JMenu("Configuración");
		btnConfiguracion.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/img/confguracion.png")));
		menuBar.add(btnConfiguracion);

		btnConfiObsequio = new JMenuItem("Configurar accesos");
		btnConfiObsequio.addActionListener(this);

		btnRolesUsuario = new JMenuItem("Configurar Roles de usuario");
		btnRolesUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedBtnRolesUsuario(e);
			}
		});
		btnConfiguracion.add(btnRolesUsuario);
		btnConfiguracion.add(btnConfiObsequio);

		btnAyuda = new JMenu("Ayuda");
		btnAyuda.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/img/Help.png")));
		menuBar.add(btnAyuda);

		btnAcercaTienda = new JMenuItem("Acerca del Producto");
		btnAcercaTienda.addActionListener(this);
		btnAyuda.add(btnAcercaTienda);

		desktopPane = new JDesktopPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(desktopPane,
				GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(desktopPane,
				GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE));
		desktopPane.setLayout(new GridLayout(0, 1, 0, 0));

		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/img/My project-1 (2).png")));
		desktopPane.add(lblNewLabel);
		getContentPane().setLayout(groupLayout);

	}

	protected void actionPerformedBtnModificarStock(ActionEvent e) {
		ModificarDato modificarDatoFrame = new ModificarDato();
		modificarDatoFrame.setVisible(true);
	}

	protected void actionPerformedBtnExit(ActionEvent e) {
		int resp = javax.swing.JOptionPane.showConfirmDialog(null, "¿Desea salir, seguro que guardaste?", null,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (resp == 0) {

			System.exit(0);

		}
	}

	protected void actionPerformedBtnListarStock(ActionEvent e) {
		MainFrame enter = new MainFrame();
		enter.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
	}

	protected void actionPerformedBtnIngresarStock(ActionEvent e) {
		IngresarDato insertarDatoFrame = new IngresarDato();
		insertarDatoFrame.setVisible(true);

	}

	protected void actionPerformedBtnRolesUsuario(ActionEvent e) {
		RolesUsuarios roles = new RolesUsuarios();
		roles.setVisible(true);
	}
}