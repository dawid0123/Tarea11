package Tarea11Dawid;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LecturaYEscritura {

    // Guardar los alumnos en un archivo JSON usando org.json
    public static void saveAlumnosToFile(List<Alumno> alumnos, String fileName) throws IOException {
        // Crear un array JSON
        JSONArray jsonArray = new JSONArray();

        // Convertir cada alumno en un JSONObject y agregarlo al array
        for (Alumno alumno : alumnos) {
            JSONObject jsonAlumno = new JSONObject();
            jsonAlumno.put("nia", alumno.getNia()); // Agregar nia
            jsonAlumno.put("nombre", alumno.getNombre()); // Agregar nombre
            jsonAlumno.put("apellidos", alumno.getApellidos()); // Agregar apellidos
            jsonAlumno.put("genero", alumno.getGenero()); // Agregar genero
            jsonAlumno.put("fechaDeNacimiento", formatFecha(alumno.getFechaDeNacimiento())); // Formatear la fecha
            jsonAlumno.put("ciclo", alumno.getCiclo()); // Agregar ciclo
            jsonAlumno.put("curso", alumno.getCurso()); // Agregar curso
            jsonAlumno.put("grupo", alumno.getGrupo()); // Agregar grupo

            // Agregar el alumno al array JSON
            jsonArray.put(jsonAlumno);
        }

        // Guardar el array JSON en el archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(jsonArray.toString(4)); // El "4" es para la indentación en el archivo JSON
        }
    }

    // Leer alumnos desde un archivo JSON y agregarlos a la base de datos usando org.json
    public static void readAlumnosFromFile(String fileName) throws IOException {
        // Leer el archivo JSON
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }

        // Convertir el contenido JSON a un JSONArray
        JSONArray jsonArray = new JSONArray(sb.toString());

        // Recorrer el array JSON y convertir cada objeto a un Alumno
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonAlumno = jsonArray.getJSONObject(i);

            // Crear un Alumno a partir del objeto JSON
            Alumno alumno = new Alumno(
                jsonAlumno.getInt("nia"),
                jsonAlumno.getString("nombre"),
                jsonAlumno.getString("apellidos"),
                jsonAlumno.getString("genero"),
                parseFecha(jsonAlumno.getString("fechaDeNacimiento")), // Parsear la fecha
                jsonAlumno.getString("ciclo"),
                jsonAlumno.getString("curso"),
                jsonAlumno.getString("grupo")
            );

            // Aquí puedes agregar el alumno a la base de datos, por ejemplo:
            try {
				ConexionSQL.insertAlumno(alumno);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    // Método para formatear la fecha a String (yyyy-MM-dd)
    private static String formatFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    // Método para parsear una fecha desde String (yyyy-MM-dd) a Date
    private static Date parseFecha(String fechaStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(fechaStr);
        } catch (Exception e) {
            return null;
        }
    }
}
