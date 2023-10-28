package produccion;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexion.SqlServerConexion;


public class ModificarDato extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ModificarDato frame = new ModificarDato();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ModificarDato() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 888, 567);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        this.setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Código artículo");
        model.addColumn("Tipo");
        model.addColumn("Descripción");
        model.addColumn("Stock inicial");
        model.addColumn("Um");
        model.addColumn("Ingresos");
        model.addColumn("Salidas");
        model.addColumn("Stock");
        model.addColumn("Um Stock");

        cargarDatosDesdeBaseDeDatos();

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    String codigoArticulo = (String) table.getValueAt(selectedRow, 0);
                    modificarCodigoArticulo(codigoArticulo);
                } else {
                    JOptionPane.showMessageDialog(ModificarDato.this, "Por favor, selecciona un código de artículo.");
                }
            }
        });
        panel.add(selectButton);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(closeButton);
    }

    private void cargarDatosDesdeBaseDeDatos() {
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

    private void modificarCodigoArticulo(String codigoArticulo) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            try {
                Connection connection = SqlServerConexion.getConnection();
                String updateQuery = "UPDATE productos SET codigo_articulo = ?, tipo = ?, descripcion = ?, stock_inicial = ?, um = ?, ingresos = ?, salidas = ?, stock = ?, um_stock = ? WHERE codigo_articulo = ?";
                PreparedStatement statement = connection.prepareStatement(updateQuery);

                // Obtener los nuevos valores de las celdas de la fila seleccionada
                String nuevoCodigo = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese el nuevo código de artículo:");
                String nuevoTipo = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese el nuevo tipo:");
                String nuevaDescripcion = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese la nueva descripción:");
                String nuevoStockInicial = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese el nuevo stock inicial:");
                String nuevaUM = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese la nueva UM:");
                String nuevosIngresos = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese los nuevos ingresos:");
                String nuevasSalidas = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese las nuevas salidas:");
                String nuevoStock = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese el nuevo stock:");
                String nuevaUMStock = JOptionPane.showInputDialog(ModificarDato.this, "Ingrese la nueva UM Stock:");

                // Actualizar los valores en la base de datos
                statement.setString(1, nuevoCodigo);
                statement.setString(2, nuevoTipo);
                statement.setString(3, nuevaDescripcion);
                statement.setString(4, nuevoStockInicial);
                statement.setString(5, nuevaUM);
                statement.setString(6, nuevosIngresos);
                statement.setString(7, nuevasSalidas);
                statement.setString(8, nuevoStock);
                statement.setString(9, nuevaUMStock);
                statement.setString(10, codigoArticulo);
                int rowsAffected = statement.executeUpdate();
                statement.close();
                connection.close();

                if (rowsAffected > 0) {
                    // Actualizar los valores en el modelo de la tabla
                    model.setValueAt(nuevoCodigo, selectedRow, 0);
                    model.setValueAt(nuevoTipo, selectedRow, 1);
                    model.setValueAt(nuevaDescripcion, selectedRow, 2);
                    model.setValueAt(nuevoStockInicial, selectedRow, 3);
                    model.setValueAt(nuevaUM, selectedRow, 4);
                    model.setValueAt(nuevosIngresos, selectedRow, 5);
                    model.setValueAt(nuevasSalidas, selectedRow, 6);
                    model.setValueAt(nuevoStock, selectedRow, 7);
                    model.setValueAt(nuevaUMStock, selectedRow, 8);

                    JOptionPane.showMessageDialog(ModificarDato.this, "Datos modificados correctamente.");
                } else {
                    JOptionPane.showMessageDialog(ModificarDato.this, "No se encontró el código de artículo en la base de datos.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ModificarDato.this, "Error al modificar los datos.");
            }
        } else {
            JOptionPane.showMessageDialog(ModificarDato.this, "No se ha seleccionado ninguna fila.");
        }
    }
}
