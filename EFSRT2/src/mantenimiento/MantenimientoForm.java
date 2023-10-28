package mantenimiento;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Conexion.SqlServerConexion;

public class MantenimientoForm extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private Connection conexion;
    private Statement estado;

    public MantenimientoForm() {
        setTitle("Tabla de Mantenimiento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        model = new DefaultTableModel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{790, 0};
        gridBagLayout.rowHeights = new int[]{570, 0};
        gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        getContentPane().add(scrollPane, gbc_scrollPane);

        pack();
        setLocationRelativeTo(null);

        conectarBaseDatos();
        cargarDatos();
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

    private void cargarDatos() {
        String selectQuery = "SELECT * FROM Mantenimiento";

        try {
            Statement selectStatement = conexion.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectQuery);

            List<Object[]> data = new ArrayList<>();
            int columnCount = resultSet.getMetaData().getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(resultSet.getMetaData().getColumnName(i));
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                data.add(rowData);
            }

            resultSet.close();
            selectStatement.close();
            conexion.close();

            for (Object[] rowData : data) {
                model.addRow(rowData);
            }

            // Ajustar el ancho de las columnas al contenido
            for (int i = 0; i < columnCount; i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                int maxWidth = 0;

                for (int row = 0; row < data.size(); row++) {
                    Object value = table.getValueAt(row, i);
                    if (value != null) {
                        int width = table.getFontMetrics(table.getFont()).stringWidth(value.toString());
                        maxWidth = Math.max(maxWidth, width);
                    }
                }

                column.setPreferredWidth(maxWidth);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MantenimientoForm mantenimientoTabla = new MantenimientoForm();
        mantenimientoTabla.setVisible(true);
    }
}
