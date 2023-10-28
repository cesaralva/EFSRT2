package produccion;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import Conexion.SqlServerConexion;


public class MainFrame extends JFrame implements ActionListener {
	private JTable table;
	private JButton insertButton;
	private JButton deleteButton;

	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/img/OIP.jpg")));
		initialize();
		loadTableData();
	}

	private void initialize() {
		setTitle("Datos de Productos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 784, 445);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane);

		insertButton = new JButton("Insertar Datos");
		insertButton.setBounds(456, 457, 226, 38);
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertData();
			}
		});
		getContentPane().add(insertButton);

		deleteButton = new JButton("Eliminar");
		deleteButton.setBounds(54, 457, 226, 38);
		getContentPane().add(deleteButton);

		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteData();
			}
		});

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadTableData() {

		DefaultTableModel model = new DefaultTableModel();

		// Nombres de columnas
		String[] columnNames = { "Código artículo", "Tipo", "Descripción", "Stock inicial", "Um", "Ingresos", "Salidas",
				"Stock", "Um Stock" };
		model.setColumnIdentifiers(columnNames);

		try {
			String selectQuery = "SELECT * FROM productos";
			Connection connection = SqlServerConexion.getConnection();
			PreparedStatement statement = connection.prepareStatement(selectQuery);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				// Ejemplo de datos
				Object[] rowData1 = new Object[9];

				rowData1[0] = resultSet.getString("codigo_articulo");
				rowData1[1] = resultSet.getString("tipo");
				rowData1[2] = resultSet.getString("descripcion");
				rowData1[3] = resultSet.getString("stock_inicial");
				rowData1[4] = resultSet.getString("um");
				rowData1[5] = resultSet.getString("ingresos");
				rowData1[6] = resultSet.getString("salidas");
				rowData1[7] = resultSet.getString("stock");
				rowData1[8] = resultSet.getString("um_stock");

				model.addRow(rowData1);
			}

			resultSet.close();
			statement.close();
			connection.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al obtener los datos de la base de datos.");
		}

		table.setModel(model);
	}

	private void insertData() {
		InsertarDato insertarDatoFrame = new InsertarDato();
		insertarDatoFrame.setVisible(true);

	}

	private void deleteData() {
	    int selectedRow = table.getSelectedRow();

	    if (selectedRow != -1) {
	        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este dato?",
	                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
	        if (confirm == JOptionPane.YES_OPTION) {
	            try {
	                // Obtener el valor del campo en la fila seleccionada
	                String selectedValue = (String) table.getValueAt(selectedRow, 0);

	                // Eliminación en la base de datos
	                String deleteQuery = "DELETE FROM productos WHERE codigo_articulo = ?";
	                Connection connection = SqlServerConexion.getConnection();
	                PreparedStatement statement = connection.prepareStatement(deleteQuery);
	                statement.setString(1, selectedValue);
	                statement.executeUpdate();
	                statement.close();
	                connection.close();

	                DefaultTableModel model = (DefaultTableModel) table.getModel();
	                model.removeRow(selectedRow);

	                JOptionPane.showMessageDialog(this, "Dato eliminado correctamente.");

	            } catch (SQLException ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(this, "Error al eliminar el dato.");
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna fila.");
	    }
	}


	// Método para calcular el resultado a partir de las observaciones
	private int calcularResultado(String observaciones) {
		// Remover espacios en blanco y dividir en diferentes operaciones
		String[] operaciones = observaciones.split("\\+");

		int resultado = 0;

		for (String operacion : operaciones) {
			operacion = operacion.trim();

			if (operacion.contains("*")) {
				String[] partes = operacion.split("\\*");
				int cantidad = Integer.parseInt(partes[0].trim());
				int valor = Integer.parseInt(partes[1].trim());
				resultado += cantidad * valor;
			} else {
				resultado += Integer.parseInt(operacion);
			}
		}

		return resultado;
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void actionPerformed(ActionEvent e) {
	}
}
