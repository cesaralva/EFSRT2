package login;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Conexion.SqlServerConexion;

public class RolesUsuarios extends JFrame {
	private JTextField campoNombre;
	private JPasswordField campoContraseña;
	private JComboBox<String> comboBoxRol;
	private JButton botonGuardar;

	public RolesUsuarios() {
		setTitle("Crear Usuario");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(524, 200);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		JPanel panelFormulario = new JPanel();

		JLabel etiquetaNombre = new JLabel("Nombre:");
		etiquetaNombre.setBounds(28, 12, 60, 15);
		campoNombre = new JTextField(15);
		campoNombre.setBounds(158, 10, 169, 19);
		JLabel etiquetaContraseña = new JLabel("Contraseña:");
		etiquetaContraseña.setBounds(27, 43, 88, 15);
		campoContraseña = new JPasswordField(15);
		campoContraseña.setBounds(158, 41, 169, 19);
		JLabel etiquetaRol = new JLabel("Rol:");
		etiquetaRol.setBounds(28, 70, 27, 15);
		comboBoxRol = new JComboBox<>(new String[] { "Administrador", "Editor", "Lectura" });
		comboBoxRol.setBounds(158, 72, 169, 24);
		panelFormulario.setLayout(null);

		panelFormulario.add(etiquetaNombre);
		panelFormulario.add(campoNombre);
		panelFormulario.add(etiquetaContraseña);
		panelFormulario.add(campoContraseña);
		panelFormulario.add(etiquetaRol);
		panelFormulario.add(comboBoxRol);

		JPanel panelBoton = new JPanel();
		panelBoton.setLayout(new FlowLayout(FlowLayout.CENTER));

		botonGuardar = new JButton("Guardar");
		botonGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarUsuario();
			}
		});

		panelBoton.add(botonGuardar);

		panel.add(panelFormulario, BorderLayout.CENTER);
		panel.add(panelBoton, BorderLayout.SOUTH);
	}

	private void guardarUsuario() {
		String nombre = campoNombre.getText();
		String contraseña = new String(campoContraseña.getPassword());
		String rol = (String) comboBoxRol.getSelectedItem();
		if (nombre.isEmpty() || contraseña.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, ingresa un nombre y una contraseña", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			Connection connection = SqlServerConexion.getConnection();
			String query = "INSERT INTO usuarios (nombre, contraseña, rol) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nombre);
			statement.setString(2, contraseña);
			statement.setString(3, rol);
			statement.executeUpdate();

			JOptionPane.showMessageDialog(null, "Usuario creado exitosamente", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al crear el usuario", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				RolesUsuarios form = new RolesUsuarios();
				form.setVisible(true);
			}
		});
	}
}
