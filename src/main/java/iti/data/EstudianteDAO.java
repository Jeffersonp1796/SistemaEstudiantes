package iti.data;

import iti.modelo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static iti.connect.Connect.getConnection;

// DAO - Data Access Object
public class EstudianteDAO {
    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();

        // Trabajo con clase de conexion a BD
        PreparedStatement ps;
        ResultSet rs;
        Connection conect = getConnection();
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante;";
        try{
            ps = conect.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setCedula(rs.getString("cedula"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        }
        catch (SQLException e){
            System.out.println("Ocurrio un error al listar los estudiantes: " + e.getMessage());
        }
        finally {
            try{
                conect.close();
            }
            catch (Exception e){
                System.out.println("Error al cerrar conexion " + e.getMessage());
            }
        }
        return estudiantes;
    }

    public boolean findById(Estudiante estudiante) {
        PreparedStatement ps;
        ResultSet rs;
        Connection conect = getConnection();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?;";
        try{
            ps = conect.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            while (rs.next()){
                estudiante.setCedula(rs.getString("cedula"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            }
        }
        catch (SQLException e){
            System.out.println("Ocurrio un error al buscar estudiante: " + e.getMessage());
        }
        finally {
            try{
                conect.close();
            }
            catch (Exception e){
                System.out.println("Error al cerrar conexion " + e.getMessage());
            }
        }
        return false;
    }

    public boolean insertarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO estudiante (cedula, nombre, apellido, telefono, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conect = getConnection();
             PreparedStatement ps = conect.prepareStatement(sql)) {

            ps.setString(1, estudiante.getCedula());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getApellido());
            ps.setString(4, estudiante.getTelefono());
            ps.setString(5, estudiante.getEmail());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection conect = getConnection();
        String sql = "UPDATE estudiante SET cedula=?, nombre=?, apellido=?, telefono=?, email=? " +
                "WHERE id_estudiante=?;";
        try{
            ps = conect.prepareStatement(sql);
            ps.setString(1, estudiante.getCedula());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getApellido());
            ps.setString(4, estudiante.getTelefono());
            ps.setString(5, estudiante.getEmail());
            ps.setInt(6, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Ocurrio un error al actualizar estudiante: " + e.getMessage());
        }
        finally {
            try{
                conect.close();
            }
            catch (Exception e){
                System.out.println("Error al cerrar conexion " + e.getMessage());
            }
        }
        return false;
    }

    public boolean eliminarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection conect = getConnection();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?;";
        try{
            ps = conect.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        }
        catch (SQLException e){
            System.out.println("Ocurrio un error al eliminar estudiante: " + e.getMessage());
        }
        finally {
            try{
                conect.close();
            }
            catch (Exception e){
                System.out.println("Error al cerrar conexion " + e.getMessage());
            }
        }
        return false;
    }

    public Estudiante buscarPorCedula(String cedula) {
        PreparedStatement ps;
        ResultSet rs;
        Connection conect = getConnection();
        String sql = "SELECT * FROM estudiante WHERE cedula = ?;";
        Estudiante estudiante = null;

        try {
            ps = conect.prepareStatement(sql);
            ps.setString(1, cedula);
            rs = ps.executeQuery();
            if (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setCedula(rs.getString("cedula"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al buscar estudiante por cédula: " + e.getMessage());
        } finally {
            try {
                conect.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return estudiante;
    }

    public boolean eliminarPorCedula(String cedula) {
        PreparedStatement ps;
        Connection conect = getConnection();
        String sql = "DELETE FROM estudiante WHERE cedula = ?;";

        try {
            ps = conect.prepareStatement(sql);
            ps.setString(1, cedula);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante por cédula: " + e.getMessage());
        } finally {
            try {
                conect.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args){
        var estudianteDAO = new EstudianteDAO();

        // Registro nuevo de estudiante
        /*var nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setCedula("1725248963");
        nuevoEstudiante.setNombre("Paul");
        nuevoEstudiante.setApellido("Pruna");
        nuevoEstudiante.setTelefono("0979215698");
        nuevoEstudiante.setEmail("paulpruna@hotmail.com");

        boolean insertado = estudianteDAO.insertarEstudiante(nuevoEstudiante);
        if (insertado) {
            System.out.println("Estudiante insertado de manera exitosa.");
        } else {
            System.out.println("Estudiante no insertado.");
        }*/

        /*// Mostrar lista de estudiantes almacenados en BD
        System.out.println("Lista de estudiantes: ");
        List<Estudiante> estudiantes = estudianteDAO.listarEstudiantes();
        estudiantes.forEach(System.out::println);*/

        //Busquedaxced
        String cedulaBusqueda = "1725248963";
        Estudiante estudiante = estudianteDAO.buscarPorCedula(cedulaBusqueda);
        if (estudiante != null) {
            System.out.println("Estudiante encontrado: " + estudiante);
        } else {
            System.out.println("Estudiante no encontrado.");
        }

        //Eliminarxced
        String cedulaEliminar = "1725248963";
        boolean eliminado = estudianteDAO.eliminarPorCedula(cedulaEliminar);
        if (eliminado) {
            System.out.println("Estudiante eliminado.");
        } else {
            System.out.println("Estudiante no eliminado.");
        }
    }
}



