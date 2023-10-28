package produccion;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import Conexion.SqlServerConexion;

public class InsertarDato extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertarDato frame = new InsertarDato();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InsertarDato() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 888, 351);
		contentPane = new JPanel();
		contentPane.setToolTipText("Insertar");
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

		// Agregar fila vacía al modelo
		model.addRow(new Object[model.getColumnCount()]);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 878, 138);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
				JPanel panel = new JPanel();
				panel.setBounds(0, 209, 878, 35);
				contentPane.add(panel);
				
						JButton insertButton = new JButton("Insertar Datos");
						panel.add(insertButton);
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = table.getRowCount();
				String insertQuery = "INSERT INTO productos (codigo_articulo, tipo, descripcion, stock_inicial, um, ingresos, salidas, stock, um_stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

				try {
					Connection connection = SqlServerConexion.getConnection();
					PreparedStatement statement = connection.prepareStatement(insertQuery);

					for (int row = 0; row < rowCount; row++) {
						Object codigo = table.getValueAt(row, 0);
						Object tipo = table.getValueAt(row, 1);
						Object descripcion = table.getValueAt(row, 2);
						Object stockInicial = table.getValueAt(row, 3);
						Object um = table.getValueAt(row, 4);
						Object ingresos = table.getValueAt(row, 5);
						Object salidas = table.getValueAt(row, 6);
						Object umStock = table.getValueAt(row, 8);

						statement.setObject(1, codigo);
						statement.setObject(2, tipo);
						statement.setObject(3, descripcion);
						statement.setObject(4, stockInicial);
						statement.setObject(5, um);
						statement.setObject(6, ingresos);
						statement.setObject(7, salidas);
						statement.setObject(8, stockInicial);
						statement.setObject(9, umStock);

						statement.executeUpdate();
					}

					statement.close();
					connection.close();
					model.addRow(new Object[model.getColumnCount()]);
					JOptionPane.showMessageDialog(InsertarDato.this,
							"Datos insertados correctamente en la base de datos.");

				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(InsertarDato.this,
							"Error al insertar los datos en la base de datos.");
				}
			}
		});
	}

	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}
}
