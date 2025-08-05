package MODELO;

import BD.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CoctelDAO {

    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    public ArrayList<Coctel> listarTodos() {
        ArrayList<Coctel> lista = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM cocteles";
            prepStmt = conn.prepareStatement(sql);
            rs = prepStmt.executeQuery();

            while (rs.next()) {
                Coctel coctel = new Coctel();
                coctel.setId(rs.getString("id"));
                coctel.setNombre(rs.getString("nombre"));
                coctel.setPrecio(rs.getString("precio"));
                coctel.setImagen(rs.getString("imagen"));
                coctel.setDisponible(rs.getString("disponible"));
                lista.add(coctel);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL LISTAR COCTELES: " + e.getMessage());
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

    public Coctel obtenerPorId(String id) {
        Coctel coctel = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM cocteles WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                coctel = new Coctel();
                coctel.setId(rs.getString("id"));
                coctel.setNombre(rs.getString("nombre"));
                coctel.setPrecio(rs.getString("precio"));
                coctel.setImagen(rs.getString("imagen"));
                coctel.setDisponible(rs.getString("disponible"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER COCTEL POR ID: " + e.getMessage());
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

        return coctel;
    }

    public String[] crear(Coctel coctel) {
        String[] resultado = new String[2];
        resultado[0] = "Reserva: no se pudo crear."; // Mensaje por defecto
        resultado[1] = "-1"; // ID por defecto si falla

        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO cocteles (nombre, precio) VALUES (?, ?)";
            prepStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, coctel.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(coctel.getPrecio()));

            int filas = prepStmt.executeUpdate();

            if (filas > 0) {
                rs = prepStmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    coctel.setId(String.valueOf(idGenerado)); // si lo manejas como String
                    resultado[0] = "Coctel: creado EXITOSAMENTE";
                    resultado[1] = String.valueOf(idGenerado);
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR AL CREAR COCTEL: " + e.getMessage());
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

    public boolean actualizar(Coctel coctel) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE cocteles SET nombre = ?, precio = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, coctel.getNombre());
            prepStmt.setDouble(2, Double.parseDouble(coctel.getPrecio()));
            prepStmt.setInt(3, Integer.parseInt(coctel.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR COCTEL: " + e.getMessage());
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
            String sql = "DELETE FROM cocteles WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR COCTEL: " + e.getMessage());
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

    public boolean actualizarImagen(Coctel coctel) {
        boolean actualizarImagen = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE cocteles SET imagen = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, coctel.getImagen());
            prepStmt.setInt(2, Integer.parseInt(coctel.getId()));

            int filas = prepStmt.executeUpdate();
            actualizarImagen = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR IMAGEN COCTEL: " + e.getMessage());
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

    public boolean actualizarEstado(Coctel coctel) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE cocteles SET disponible = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(coctel.getDisponible()));
            prepStmt.setInt(2, Integer.parseInt(coctel.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR DISPONIBILIDAD COCTEL: " + e.getMessage());
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
