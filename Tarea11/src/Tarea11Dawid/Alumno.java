package Tarea11Dawid;

import java.util.Date;

public class Alumno {

	private int nia;
    private String nombre;
    private String apellidos;
    private String genero;
    private Date fechaDeNacimiento;
    private String ciclo;
    private String curso;
    private String grupo;

    public Alumno(int nia, String nombre, String apellidos, String genero, Date fechaDeNacimiento, String ciclo, String curso, String grupo) {
        this.nia = nia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.ciclo = ciclo;
        this.curso = curso;
        this.grupo = grupo;
    }

    public int getNia() {
        return nia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public String getGrupo() {
        return grupo;
    }

    @Override
    public String toString() {
        return "Alumno [nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero
                + ", fechaDeNacimiento=" + fechaDeNacimiento + ", ciclo=" + ciclo + ", curso=" + curso + ", grupo=" + grupo + "]";
    }
}

