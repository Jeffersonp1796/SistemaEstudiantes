package iti.vista;

import iti.data.EstudianteDAO;
import iti.modelo.Estudiante;

import java.util.List;
import java.util.Scanner;

public class EstudiantesApp {
    public static void main(String[] args){
        Scanner consola = new Scanner(System.in);
        var salir = false;
        var estudianteDAO = new EstudianteDAO();

        // MUESTRA MENU
        while (!salir){
            try{
                muestramenu();
                salir = ejecutarOpciones(consola, estudianteDAO);
            }
            catch (Exception e){
                System.out.println();
                System.out.println("Error en ejecución: " + e.getMessage());
                System.out.println();
            }
        }

    }

    private static void muestramenu(){
        System.out.println();
        System.out.println("""
                ***** SISTEMA DE ESTUDIANTES *****
                1. Listar estudiantes
                2. Buscar estudiante
                3. Agregar estudiante
                4. Actualizar estudiante
                5. Eliminar estudiante
                6. Salir
                """);
        System.out.print("Por favor, ingrese una opción: ");
        System.out.println();
    }

    private static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        System.out.println();
        boolean salir = false;
        // Validar opciones ingresadas
        switch (opcion){
            case 1 -> { // Listar estudiantes
                System.out.println();
                System.out.println(".:: Lista de estudiantes ::.");
                var estudiantes = estudianteDAO.listarEstudiantes();
                estudiantes.forEach(System.out::println);
            }
                case 2 -> { // Buscar estudiante
                    boolean volver = false;
                    while (!volver) {
                        System.out.println("""
                            .:: Buscar Estudiante ::.
                            1. Buscar por ID
                            2. Buscar por cédula
                            3. Volver
                            """);
                        System.out.print("Por favor, ingrese una opción: ");
                        var subOpcion = Integer.parseInt(consola.nextLine());
                        switch (subOpcion) {
                            case 1 -> { // Buscar por ID
                                System.out.print("Ingrese ID de estudiante a buscar: ");
                                var idEstudiante = Integer.parseInt(consola.nextLine());
                                var estudiante = new Estudiante(idEstudiante);
                                var existeEstudiante = estudianteDAO.findById(estudiante);
                                if (existeEstudiante)
                                    System.out.println("Estudiante encontrado: " + estudiante);
                                else
                                    System.out.println("No se encontró estudiante con ID: " + estudiante.getIdEstudiante());
                            }
                            case 2 -> { // Buscar por cédula
                                System.out.print("Ingrese cédula de estudiante a buscar: ");
                                var cedula = consola.nextLine();
                                var estudiante = estudianteDAO.buscarPorCedula(cedula);
                                if (estudiante != null)
                                    System.out.println("Estudiante encontrado: " + estudiante);
                                else
                                    System.out.println("No se encontró estudiante con cédula: " + cedula);
                            }
                            case 3 -> { // Volver al menú principal
                                volver = true;
                            }
                            default -> System.out.println("Opción ingresada no existe, por favor ingrese nuevamente.");
                        }
                    }
                }

                /*System.out.println("Ingrese ID de estudiante a buscar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var existeEstudiante = estudianteDAO.findById(estudiante);
                if(existeEstudiante)
                    System.out.println("Estudiante encontrado: " + estudiante);
                else
                    System.out.println("No se encontro estudiante con ID : " + estudiante.getIdEstudiante());
            }*/
            case 3 -> { // Agregar estudiante
                System.out.println(".:: Agregar Estudiante ::.");
                System.out.print("Cedula: ");
                var cedula = consola.nextLine();
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Teléfono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();

                var nuevoEstudiante = new Estudiante(cedula, nombre, apellido, telefono, email);
                var agregaEstudiante = estudianteDAO.insertarEstudiante(nuevoEstudiante);

                if(agregaEstudiante)
                    System.out.println("Estudiante agregado exitosamente: " + nuevoEstudiante);
                else
                    System.out.println("No se pudo agregar estudiante : " + nuevoEstudiante);

            }

            case 4 -> { // Actualizar estudiante
                System.out.println(".:: Actualizar Estudiante ::.");
                System.out.println("Ingrese ID de estudiante a actualizar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                System.out.print("Cedula: ");
                var cedula = consola.nextLine();
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Teléfono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();

                var editaEstudiante = new Estudiante(idEstudiante, cedula, nombre, apellido, telefono, email);
                var actualizado = estudianteDAO.actualizarEstudiante(editaEstudiante);
                if(actualizado)
                    System.out.println("Estudiante actualizado exitosamente: " + editaEstudiante);
                else
                    System.out.println("No se pudo actualizar estudiante : " + editaEstudiante);
            }
            case 5 -> { // Eliminar estudiant
                boolean volver = false;
                while (!volver) {
                    System.out.println("""
                            ***** Eliminar Estudiante *****
                            1. Eliminar por ID
                            2. Eliminar por cédula
                            3. Volver
                            """);
                    System.out.print("Por favor, ingrese una opción: ");
                    var subOpcion = Integer.parseInt(consola.nextLine());
                    switch (subOpcion) {
                        case 1 -> { // Eliminar por ID
                            System.out.print("Ingrese ID de estudiante a eliminar: ");
                            var idEstudiante = Integer.parseInt(consola.nextLine());
                            var eliminaEstudiante = new Estudiante(idEstudiante);
                            var eliminado = estudianteDAO.eliminarEstudiante(eliminaEstudiante);
                            if (eliminado)
                                System.out.println("Estudiante eliminado exitosamente: " + eliminaEstudiante);
                            else
                                System.out.println("No se pudo eliminar estudiante: " + eliminaEstudiante);
                        }
                        case 2 -> { // Eliminar por cédula
                            System.out.print("Ingrese cédula de estudiante a eliminar: ");
                            var cedula = consola.nextLine();
                            var eliminado = estudianteDAO.eliminarPorCedula(cedula);
                            if (eliminado)
                                System.out.println("Estudiante eliminado exitosamente.");
                            else
                                System.out.println("No se pudo eliminar estudiante con cédula: " + cedula);
                        }
                        case 3 -> { // Volver al menú principal
                            volver = true;
                        }
                        default -> System.out.println("Opción ingresada no existe, por favor ingrese nuevamente.");
                    }
                }
            }
            case 6 -> { // Salir de la app
                System.out.println("Gracias, hasta pronto!!!");
                salir = true;
            }
            default -> System.out.println("Opcion ingresada no existe, por favor ingrese nuevamente.");
        }
        return salir;
    }
    }