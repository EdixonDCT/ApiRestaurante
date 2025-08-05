package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ComidaDAO {

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<Comida> listarTodos() {
        ArrayList<Comida> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM comidas";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                Comida comida = new Comida();
                comida.setId(rs.getString("id"));
                comida.setNombre(rs.getString("nombre"));
                comida.setPrecio(rs.getString("precio"));
                comida.setTipo(rs.getString("tipo"));
                comida.setImagen(rs.getString("imagen"));
                comida.setDisponible(rs.getString("disponible"));
                lista.add(comida);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR COMIDAS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return lista;
    }

    public Comida obtenerPorId(String id) {
        Comida comida = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM comidas WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                comida = new Comida();
                comida.setId(rs.getString("id"));
                comida.setNombre(rs.getString("nombre"));
                comida.setPrecio(rs.getString("precio"));
                comida.setTipo(rs.getString("tipo"));
                comida.setImagen(rs.getString("imagen"));
                comida.setDisponible(rs.getString("disponible"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER COMIDA POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return comida;
    }

    public String[] crear(Comida comida) {
        String[] resultado = new String[2];
        resultado[0] = "Reserva: no se pudo crear."; // Mensaje por defecto
        resultado[1] = "-1"; // ID por defecto si falla

        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO comidas (nombre, precio, tipo) VALUES (?, ?, ?)";
            prepStmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, comida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(comida.getPrecio()));
            prepStmt.setString(3, comida.getTipo());
            
                            int filas = prepStmt.executeUpdate();
                            
            if (filas > 0) {
                rs = prepStmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    comida.setId(String.valueOf(idGenerado)); // si lo manejas como String
                    resultado[0] = "Comida: creado EXITOSAMENTE";
                    resultado[1] = String.valueOf(idGenerado);
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return resultado;
    }

    public boolean actualizar(Comida comida) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE comidas SET nombre = ?, precio = ?, tipo = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, comida.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(comida.getPrecio()));
            prepStmt.setString(3, comida.getTipo());
            prepStmt.setInt(4, Integer.parseInt(comida.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return actualizado;
    }

    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM comidas WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return eliminado;
    }

    public boolean actualizarImagen(Comida comida) {
        boolean actualizarImagen = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE comidas SET imagen = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, comida.getImagen());
            prepStmt.setInt(2, Integer.parseInt(comida.getId()));

            int filas = prepStmt.executeUpdate();
            actualizarImagen = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR IMAGEN COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL ACTUALIZAR IMAGEN CONEXIÓN: " + ex.getMessage());
            }
        }

        return actualizarImagen;
    }

    public boolean actualizarEstado(Comida comida) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE comidas SET disponible = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(comida.getDisponible()));
            prepStmt.setInt(2, Integer.parseInt(comida.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD COMIDA: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.err.println("ERROR AL CERRAR CONEXIÓN: " + ex.getMessage());
            }
        }

        return actualizado;
    }
}
