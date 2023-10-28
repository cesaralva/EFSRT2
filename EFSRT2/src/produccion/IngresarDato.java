package produccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexion.SqlServerConexion;

public class IngresarDato extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// Etiquetas
	private JLabel lblCodigo, lblTipo, lblDescripcion, lblStockInicial, lblUM, lblIngresos, lblSalidas, lblStock,
			lblUMStock;
	// Campos de texto
	private JTextField txtCodigo, txtTipo, txtDescripcion, txtStockInicial, txtUM, txtIngresos, txtSalidas,
			txtStock, txtUMStock;
	// Botones
	private JButton btnAgregar, btnEliminar;
	// Tabla
	private JTable tablaProductos;
	// Modelo de tabla
	private DefaultTableModel modeloTabla;

	// Conexión a la base de datos
	private Connection conexion;
	private Statement estado;

	public IngresarDato() {
		super("Gestión de Productos");
		setTitle("Gestión de Produccion");
		inicializarComponentes();
		conectarBaseDatos();
	}

	private void inicializarComponentes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 500);
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);


		lblCodigo = new JLabel("Código Artículo:");
		lblCodigo.setBounds(10, 40, 100, 20);
		getContentPane().add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(120, 40, 100, 20);
		getContentPane().add(txtCodigo);

		lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(10, 70, 100, 20);
		getContentPane().add(lblTipo);

		txtTipo = new JTextField();
		txtTipo.setBounds(120, 70, 100, 20);
		getContentPane().add(txtTipo);

		lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(10, 100, 100, 20);
		getContentPane().add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(120, 100, 100, 20);
		getContentPane().add(txtDescripcion);

		lblStockInicial = new JLabel("Stock Inicial:");
		lblStockInicial.setBounds(10, 130, 100, 20);
		getContentPane().add(lblStockInicial);

		txtStockInicial = new JTextField();
		txtStockInicial.setBounds(120, 130, 100, 20);
		getContentPane().add(txtStockInicial);

		lblUM = new JLabel("UM:");
		lblUM.setBounds(10, 160, 100, 20);
		getContentPane().add(lblUM);

		txtUM = new JTextField();
		txtUM.setBounds(120, 160, 100, 20);
		getContentPane().add(txtUM);

		lblIngresos = new JLabel("Ingresos:");
		lblIngresos.setBounds(10, 190, 100, 20);
		getContentPane().add(lblIngresos);

		txtIngresos = new JTextField();
		txtIngresos.setBounds(120, 190, 100, 20);
		getContentPane().add(txtIngresos);

		lblSalidas = new JLabel("Salidas:");
		lblSalidas.setBounds(10, 220, 100, 20);
		getContentPane().add(lblSalidas);

		txtSalidas = new JTextField();
		txtSalidas.setBounds(120, 220, 100, 20);
		getContentPane().add(txtSalidas);

		lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 250, 100, 20);
		getContentPane().add(lblStock);

		txtStock = new JTextField();
		txtStock.setBounds(120, 250, 100, 20);
		getContentPane().add(txtStock);

		lblUMStock = new JLabel("UM Stock:");
		lblUMStock.setBounds(10, 280, 100, 20);
		getContentPane().add(lblUMStock);

		txtUMStock = new JTextField();
		txtUMStock.setBounds(120, 280, 100, 20);
		getContentPane().add(txtUMStock);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(10, 310, 100, 30);
		getContentPane().add(btnAgregar);
		btnAgregar.addActionListener(this);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(120, 310, 100, 30);
		getContentPane().add(btnEliminar);
		btnEliminar.addActionListener(this);

		modeloTabla = new DefaultTableModel();
		tablaProductos = new JTable(modeloTabla);
		modeloTabla.addColumn("Código");
		modeloTabla.addColumn("Tipo");
		modeloTabla.addColumn("Descripción");
		modeloTabla.addColumn("Stock Inicial");
		modeloTabla.addColumn("UM");
		modeloTabla.addColumn("Ingresos");
		modeloTabla.addColumn("Salidas");
		modeloTabla.addColumn("Stock");
		modeloTabla.addColumn("UM Stock");

		JScrollPane scrollPane = new JScrollPane(tablaProductos);
		scrollPane.setBounds(230, 10, 550, 400);
		getContentPane().add(scrollPane);
		tablaProductos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaSeleccionada = tablaProductos.getSelectedRow();
				if (filaSeleccionada >= 0) {
					txtCodigo.setText(tablaProductos.getValueAt(filaSeleccionada, 0).toString());
					txtTipo.setText(tablaProductos.getValueAt(filaSeleccionada, 1).toString());
					txtDescripcion.setText(tablaProductos.getValueAt(filaSeleccionada, 2).toString());
					txtStockInicial.setText(tablaProductos.getValueAt(filaSeleccionada, 3).toString());
					txtUM.setText(tablaProductos.getValueAt(filaSeleccionada, 4).toString());
					txtIngresos.setText(tablaProductos.getValueAt(filaSeleccionada, 5).toString());
					txtSalidas.setText(tablaProductos.getValueAt(filaSeleccionada, 6).toString());
					txtStock.setText(tablaProductos.getValueAt(filaSeleccionada, 7).toString());
					txtUMStock.setText(tablaProductos.getValueAt(filaSeleccionada, 8).toString());
				}
			}
		});
	}

	private void conectarBaseDatos() {
		try {
			conexion = SqlServerConexion.getConnection();
			estado = conexion.createStatement();
			System.out.println("Conexión exitosa a la base de datos.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void actualizarTabla() {
		try {
			String query = "SELECT * FROM productos";
			ResultSet resultado = estado.executeQuery(query);
			modeloTabla.setRowCount(0);
			while (resultado.next()) {
				Object[] fila = { resultado.getString("codigo_articulo"), resultado.getString("tipo"),
						resultado.getString("descripcion"), resultado.getInt("stock_inicial"),
						resultado.getString("um"), resultado.getInt("ingresos"), resultado.getInt("salidas"),
						resultado.getInt("stock"), resultado.getString("um_stock") };
				modeloTabla.addRow(fila);
			}
			resultado.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void limpiarCampos() {
		txtCodigo.setText("");
		txtTipo.setText("");
		txtDescripcion.setText("");
		txtStockInicial.setText("");
		txtUM.setText("");
		txtIngresos.setText("");
		txtSalidas.setText("");
		txtStock.setText("");
		txtUMStock.setText("");
	}

	private void agregarProducto() {
		try {
			String codigo = txtCodigo.getText();
			String tipo = txtTipo.getText();
			String descripcion = txtDescripcion.getText();
			int stockInicial = Integer.parseInt(txtStockInicial.getText());
			String um = txtUM.getText();
			int ingresos = Integer.parseInt(txtIngresos.getText());
			int salidas = Integer.parseInt(txtSalidas.getText());
			int stock = Integer.parseInt(txtStock.getText());
			String umStock = txtUMStock.getText();

			String query = "INSERT INTO productos (codigo_articulo, tipo, descripcion, stock_inicial, um, ingresos, salidas, stock, um_stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement declaracion = conexion.prepareStatement(query);
			declaracion.setString(1, codigo);
			declaracion.setString(2, tipo);
			declaracion.setString(3, descripcion);
			declaracion.setInt(4, stockInicial);
			declaracion.setString(5, um);
			declaracion.setInt(6, ingresos);
			declaracion.setInt(7, salidas);
			declaracion.setInt(8, stock);
			declaracion.setString(9, umStock);

			declaracion.executeUpdate();
				declaracion.close();
				limpiarCampos();
				actualizarTabla();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		private void eliminarProducto() {
			try {
				String codigo = txtCodigo.getText();
				String query = "DELETE FROM productos WHERE codigo_articulo = ?";
				PreparedStatement declaracion = conexion.prepareStatement(query);
				declaracion.setString(1, codigo);

				declaracion.executeUpdate();
				declaracion.close();
				limpiarCampos();
				actualizarTabla();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAgregar) {
				agregarProducto();
			} else if (e.getSource() == btnEliminar) {
				eliminarProducto();
			}
		}

		public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception e) {
						e.printStackTrace();
					}
					new IngresarDato().setVisible(true);
				}
			});
		}
	}
