package Tarea11Dawid;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConexionSQL {

	 private static final String DB_URL = "jdbc:mysql://localhost:3306/Alumnos08"; //jdbc:mysql://[host]:[puerto]/[nombre_base_de_datos]
	    private static final String USER = "dawid"; // Usuario de la base de datos
	    private static final String PASS = "manager"; // Contraseña de la base de datos

	    public static Connection getConnection() throws SQLException {
	        return DriverManager.getConnection(DB_URL, USER, PASS);
	    }

	    // Insertar un nuevo alumno
	    public static void insertAlumno(Alumno alumno) throws SQLException {
	        String sql = "INSERT INTO alumno (nia, nombre, apellidos, genero, fecha_de_nacimiento, ciclo, curso, grupo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, alumno.getNia());
	            stmt.setString(2, alumno.getNombre());
	            stmt.setString(3, alumno.getApellidos());
	            stmt.setString(4, alumno.getGenero());
	            stmt.setDate(5, new java.sql.Date(alumno.getFechaDeNacimiento().getTime()));
	            stmt.setString(6, alumno.getCiclo());
	            stmt.setString(7, alumno.getCurso());
	            stmt.setString(8, alumno.getGrupo());
	            stmt.executeUpdate();
	        }
	    }

	    // Obtener todos los alumnos
	    public static List<Alumno> getAllAlumnos() throws SQLException {
	        List<Alumno> alumnos = new ArrayList<>();
	        String sql = "SELECT * FROM alumno";
	        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
	            while (rs.next()) {
	                Alumno alumno = new Alumno(
	                        rs.getInt("nia"),
	                        rs.getString("nombre"),
	                        rs.getString("apellidos"),
	                        rs.getString("genero"),
	                        rs.getDate("fecha_de_nacimiento"),
	                        rs.getString("ciclo"),
	                        rs.getString("curso"),
	                        rs.getString("grupo")
	                );
	                alumnos.add(alumno);
	            }
	        }
	        return alumnos;
	    }

	    // Modificar el nombre de un alumno por su NIA
	    public static void updateAlumnoNombre(int nia, String nuevoNombre) throws SQLException {
	        String sql = "UPDATE alumno SET nombre = ? WHERE nia = ?";
	        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, nuevoNombre);
	            stmt.setInt(2, nia);
	            stmt.executeUpdate();
	        }
	    }

	    // Eliminar un alumno por NIA
	    public static void deleteAlumno(int nia) throws SQLException {
	        String sql = "DELETE FROM alumno WHERE nia = ?";
	        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, nia);
	            stmt.executeUpdate();
	        }
	    }

	    // Eliminar alumnos por apellido
	    public static void deleteAlumnosByApellido(String palabra) throws SQLException {
	        String sql = "DELETE FROM alumno WHERE apellidos LIKE ?";
	        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, "%" + palabra + "%");
	            stmt.executeUpdate();
	        }
	    }
	}
