package Tarea11Dawid;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Menú de opciones
            System.out.println("Menú de opciones:");
            System.out.println("1. Insertar un nuevo alumno");
            System.out.println("2. Mostrar todos los alumnos");
            System.out.println("3. Guardar todos los alumnos en un fichero");
            System.out.println("4. Leer alumnos de un fichero y guardarlos en la BD");
            System.out.println("5. Modificar el nombre de un alumno por PK");
            System.out.println("6. Eliminar un alumno por PK");
            System.out.println("7. Eliminar alumnos por apellido");
            System.out.println("8. Guardar todos los alumnos en un fichero XML/JSON");
            System.out.println("9. Leer un fichero JSON de alumnos y guardarlos en la BD");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer

            try {
                switch (choice) {
                    case 1 -> insertAlumno(sc);
                    case 2 -> showAlumnos();
                    case 3 -> saveAlumnosToFile(sc);
                    case 4 -> readAlumnosFromFile(sc);
                    case 5 -> updateAlumno(sc);
                    case 6 -> deleteAlumno(sc);
                    case 7 -> deleteAlumnosByApellido(sc);
                    case 8 -> saveAlumnosToXMLOrJSON(sc);
                    case 9 -> readAlumnosFromXMLOrJSON(sc);
                    case 0 -> exit = true;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException | IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }

    // Insertar un nuevo alumno
    private static void insertAlumno(Scanner scanner) throws SQLException, ParseException {
        System.out.print("NIA: ");
        int nia = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Género: ");
        String genero = scanner.nextLine();

        // Formato de fecha: dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        Date fechaNacimiento = sdf.parse(fechaStr);

        System.out.print("Ciclo: ");
        String ciclo = scanner.nextLine();
        System.out.print("Curso: ");
        String curso = scanner.nextLine();
        System.out.print("Grupo: ");
        String grupo = scanner.nextLine();

        Alumno alumno = new Alumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);
        ConexionSQL.insertAlumno(alumno);
        System.out.println("Alumno insertado correctamente.");
    }

    // Mostrar todos los alumnos
    private static void showAlumnos() throws SQLException {
        List<Alumno> alumnos = ConexionSQL.getAllAlumnos();
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
    }

    // Guardar todos los alumnos en un fichero (Formato txt o CSV)
    private static void saveAlumnosToFile(Scanner sc) throws SQLException, IOException {
        System.out.print("Ingrese el nombre del archivo (sin extensión): ");
        String fileName = sc.nextLine();
        List<Alumno> alumnos = ConexionSQL.getAllAlumnos();
        LecturaYEscritura.saveAlumnosToFile(alumnos, fileName + ".txt");
        System.out.println("Alumnos guardados en el fichero.");
    }

    // Leer alumnos de un fichero y guardarlos en la BD
    private static void readAlumnosFromFile(Scanner sc) throws IOException {
        System.out.print("Ingrese el nombre del archivo (con extensión): ");
        String fileName = sc.nextLine();
        LecturaYEscritura.readAlumnosFromFile(fileName);
        System.out.println("Alumnos importados desde el fichero.");
    }

    // Modificar el nombre de un alumno por PK
    private static void updateAlumno(Scanner scanner) throws SQLException {
        System.out.print("Introduce el NIA del alumno: ");
        int nia = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Introduce el nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        ConexionSQL.updateAlumnoNombre(nia, nuevoNombre);
        System.out.println("Nombre actualizado.");
    }

    // Eliminar un alumno por PK
    private static void deleteAlumno(Scanner scanner) throws SQLException {
        System.out.print("Introduce el NIA del alumno a eliminar: ");
        int nia = scanner.nextInt();
        ConexionSQL.deleteAlumno(nia);
        System.out.println("Alumno eliminado.");
    }

    // Eliminar alumnos por apellido
    private static void deleteAlumnosByApellido(Scanner scanner) throws SQLException {
        System.out.print("Introduce el apellido a eliminar: ");
        String apellido = scanner.nextLine();
        ConexionSQL.deleteAlumnosByApellido(apellido);
        System.out.println("Alumnos eliminados.");
    }

    // Guardar todos los alumnos en un fichero XML o JSON
    private static void saveAlumnosToXMLOrJSON(Scanner sc) throws SQLException, IOException {
        System.out.println("Seleccione el formato para guardar los alumnos:");
        System.out.println("1. JSON");
        System.out.println("2. XML");
        System.out.print("Opción: ");
        int option = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        List<Alumno> alumnos = ConexionSQL.getAllAlumnos();

        if (option == 1) {
            LecturaYEscritura.saveAlumnosToFile(alumnos, "alumnos.json");
            System.out.println("Alumnos guardados en formato JSON.");
        }  else {
            System.out.println("Opción inválida.");
        }
    }

    // Leer un fichero XML o JSON de alumnos y guardarlos en la BD
    private static void readAlumnosFromXMLOrJSON(Scanner sc) throws IOException {
        System.out.println("Seleccione el formato de archivo:");
        System.out.println("1. JSON");
        System.out.println("2. XML");
        System.out.print("Opción: ");
        int option = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        if (option == 1) {
            LecturaYEscritura.readAlumnosFromFile("alumnos.json");
            System.out.println("Alumnos importados desde JSON.");
        } else if (option == 2) {
            LecturaYEscritura.readAlumnosFromFile("alumnos.xml");
            System.out.println("Alumnos importados desde XML.");
        } else {
            System.out.println("Opción inválida.");
        }
    }
}
