package clase;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;



public class ventanaPrincip extends JFrame {

	private JPanel contentPane;
	private JTextField textN;
	private JTextField textA;
	private JTextField textAS;
	private JTextField textD;
	private JButton btnIntroducir;
	private JLabel labelResultado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaPrincip frame = new ventanaPrincip();
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
	public ventanaPrincip() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textN = new JTextField();
		textN.setBounds(24, 60, 134, 28);
		contentPane.add(textN);
		textN.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(55, 32, 61, 16);
		contentPane.add(lblNombre);

		textA = new JTextField();
		textA.setBounds(196, 60, 134, 28);
		contentPane.add(textA);
		textA.setColumns(10);

		textAS = new JTextField();
		textAS.setBounds(24, 121, 134, 28);
		contentPane.add(textAS);
		textAS.setColumns(10);

		textD = new JTextField();
		textD.setBounds(196, 121, 134, 28);
		contentPane.add(textD);
		textD.setColumns(10);

		JLabel lblAsignatura = new JLabel("Asignatura");
		lblAsignatura.setBounds(34, 100, 124, 16);
		contentPane.add(lblAsignatura);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(196, 32, 61, 16);
		contentPane.add(lblApellido);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(196, 100, 61, 16);
		contentPane.add(lblDni);

		labelResultado = new JLabel("");
		labelResultado.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(labelResultado);


		btnIntroducir = new JButton("Introducir");
		btnIntroducir.addActionListener(new ActionListener() {

			//Acción del botón introducir:

			public void actionPerformed(ActionEvent e) {

				//Conexión a base de datos:

				Connection conexion = null;

				try {
					conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1/clase?user=root&password=root");
					System.out.println("Conectado");

					Statement stm1 = conexion.createStatement();
					String query = "INSERT INTO alumnos (nombre, apellido, asignatura, dni) values ('"+textN.getText()+"','"+textA.getText()+"','"+textAS.getText()+"','"+textD.getText()+"')";
					System.out.println(query);
					stm1.executeUpdate(query);

					conexion.close();
					System.out.println("Conexion cerrada");

					textN.setText("");
					textA.setText("");
					textAS.setText("");
					textD.setText("");

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnIntroducir.setBounds(30, 176, 117, 29);
		contentPane.add(btnIntroducir);


		//Boton de consultar 

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
// Ventana mostrando
				
				labelResultado.setText("");

// Creamos la conexión
				
				Connection conexion = null;

				try {
					conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1/clase?user=root&password=root");
					System.out.println("Se ha conectado con la base de datos correctamente");
					Statement stmt2=conexion.createStatement();
					
//Ejecutamos la consulta
					
					
					ResultSet resultadoConsulta = stmt2.executeQuery("select * FROM alumnos WHERE nombre='"+textN.getText()+"'"); 
					while(resultadoConsulta.next()){

//Mostramos los resultados
					
					resultados frame = new resultados();
					frame.setVisible(true);
					System.out.println(resultadoConsulta.getString(1)+" "+resultadoConsulta.getString(2)+"	Asign: "+resultadoConsulta.getString(3)+"	DNI: "+resultadoConsulta.getString(4));

//Escribimos los datos en la ventana
						
						textN.setText(resultadoConsulta.getString(1));
						textA.setText(resultadoConsulta.getString(2));
						textAS.setText(resultadoConsulta.getString(3));
						textD.setText(resultadoConsulta.getString(4));
					}
					
//Información en consola
					
					System.out.println("Se han Consultado  registros");
					labelResultado.setText("Mostrando datos");
					conexion.close();
					System.out.println("Cerrando conexión a base de datos");
					stmt2.close();

					//textN.setText("");
					//textA.setText("");
					//textT.setText("");
					//textH.setText("");
					//textS.setText("");
				} catch(SQLException ex){
					setTitle(ex.toString());
				}
			}
		});
		btnConsultar.setBounds(193, 176, 117, 29);
		contentPane.add(btnConsultar);

		labelResultado = new JLabel("");
		labelResultado.setBounds(91, 217, 240, 16);
		contentPane.add(labelResultado);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(364, 229, 4, 4);
		contentPane.add(scrollPane);


		//Cargamos el driver de mysql:

		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}


