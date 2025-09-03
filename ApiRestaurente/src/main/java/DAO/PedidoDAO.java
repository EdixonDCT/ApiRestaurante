package DAO;

import BD.DBConnection;
import MODELO.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Esta clase es el Data Access Object (DAO) para la entidad Pedido. Se encarga
 * de toda la lógica de interacción con la base de datos para las operaciones
 * CRUD (Crear, Leer, Actualizar, Borrar) y otras consultas relacionadas con los
 * pedidos.
 */
public class PedidoDAO {

    // Atributos de la clase para manejar la conexión, sentencias preparadas y resultados
    private Connection conn = null;
    private PreparedStatement prepStmt = null;
    private ResultSet rs = null;

    /**
     * Recupera y lista todos los pedidos de la base de datos.
     *
     * @return Una lista de objetos Pedido. Si no hay pedidos, devuelve una
     * lista vacía.
     */
    public ArrayList<Pedido> listarTodos() {
        // Se inicializa una lista para almacenar los pedidos
        ArrayList<Pedido> lista = new ArrayList<>();

        try {
            // Se obtiene la conexión a la base de datos
            conn = DBConnection.getConnection();
            // Se define la consulta SQL para seleccionar todos los registros
            String sql = "SELECT * FROM pedidos";
            // Se prepara la sentencia SQL para su ejecución
            prepStmt = conn.prepareStatement(sql);
            // Se ejecuta la consulta y se obtiene el conjunto de resultados
            rs = prepStmt.executeQuery();

            // Se itera sobre cada fila del resultado
            while (rs.next()) {
                // Por cada fila, se crea un nuevo objeto Pedido
                Pedido pedido = new Pedido();
                // Se asignan los valores de cada columna al objeto Pedido
                pedido.setId(rs.getString("id"));
                pedido.setNumeroMesa(rs.getString("numero_mesa"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setHora(rs.getString("hora"));
                pedido.setValorTotal(rs.getString("valor_total"));
                pedido.setIdCaja(rs.getString("id_caja"));
                pedido.setNumeroClientes(rs.getString("numero_clientes"));
                pedido.setIdReserva(rs.getString("id_reserva"));
                pedido.setCorreoCliente(rs.getString("correo_cliente"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));
                pedido.setFacturado(rs.getString("facturado"));
                pedido.setEliminado(rs.getString("eliminado"));

                // Se añade el objeto Pedido a la lista
                lista.add(pedido);
            }

        } catch (Exception e) {
            // Manejo de errores: imprime un mensaje de error y el stack trace
            System.err.println("ERROR AL LISTAR PEDIDOS: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Bloque finally para asegurar que los recursos (conexión, sentencia, resultado) se cierren
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

    /**
     * Obtiene un pedido específico por su ID.
     *
     * @param id El ID del pedido a buscar.
     * @return El objeto Pedido si se encuentra, o null si no existe.
     */
    public Pedido obtenerPorId(String id) {
        Pedido pedido = null;

        try {
            conn = DBConnection.getConnection();
            // Consulta SQL con un 'placeholder' (?) para evitar inyección SQL
            String sql = "SELECT * FROM pedidos WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            // Se establece el valor del placeholder con el ID del pedido, convirtiéndolo a entero
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            // Se verifica si el resultado contiene alguna fila
            if (rs.next()) {
                pedido = new Pedido();
                // Se asignan los valores a las propiedades del objeto Pedido
                pedido.setId(rs.getString("id"));
                pedido.setNumeroMesa(rs.getString("numero_mesa"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setHora(rs.getString("hora"));
                pedido.setValorTotal(rs.getString("valor_total"));
                pedido.setIdCaja(rs.getString("id_caja"));
                pedido.setNumeroClientes(rs.getString("numero_clientes"));
                pedido.setIdReserva(rs.getString("id_reserva"));
                pedido.setCorreoCliente(rs.getString("correo_cliente"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

        return pedido;
    }

    /**
     * Verifica si existe un pedido con un ID determinado.
     *
     * @param id El ID del pedido a verificar.
     * @return true si el pedido existe, false en caso contrario.
     */
    public boolean existePedidoPorId(String id) {
        boolean existe = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta optimizada: solo verifica la existencia sin traer todos los datos
            String sql = "SELECT 1 FROM pedidos WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            // Si el resultado tiene alguna fila, significa que el pedido existe
            if (rs.next()) {
                existe = true;
            }

        } catch (Exception e) {
            System.err.println("ERROR AL VERIFICAR PEDIDO POR ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

        return existe;
    }

    /**
     * Crea un nuevo pedido en la base de datos.
     *
     * @param pedido El objeto Pedido con los datos a insertar.
     * @return Un array de Strings con el resultado del proceso: [mensaje,
     * idGenerado].
     */
    public Pedido obtenerPorReserva(String id) {
        Pedido pedido = null;

        try {
            conn = DBConnection.getConnection();
            // Consulta SQL con un 'placeholder' (?) para evitar inyección SQL
            String sql = "SELECT * FROM pedidos WHERE id_reserva = ?";
            prepStmt = conn.prepareStatement(sql);
            // Se establece el valor del placeholder con el ID del pedido, convirtiéndolo a entero
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            // Se verifica si el resultado contiene alguna fila
            if (rs.next()) {
                pedido = new Pedido();
                // Se asignan los valores a las propiedades del objeto Pedido
                pedido.setId(rs.getString("id"));
                pedido.setNumeroMesa(rs.getString("numero_mesa"));
                pedido.setFecha(rs.getString("fecha"));
                pedido.setHora(rs.getString("hora"));
                pedido.setValorTotal(rs.getString("valor_total"));
                pedido.setIdCaja(rs.getString("id_caja"));
                pedido.setNumeroClientes(rs.getString("numero_clientes"));
                pedido.setIdReserva(rs.getString("id_reserva"));
                pedido.setCorreoCliente(rs.getString("correo_cliente"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

        return pedido;
    }

    public String[] crear(Pedido pedido) {
        String[] resultado = new String[2];
        // Mensaje y ID por defecto en caso de error
        resultado[0] = "Pedido: no se pudo crear.";
        resultado[1] = "-1";

        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            // Consulta INSERT. Las columnas 'fecha' y 'hora' se llenan automáticamente con la fecha/hora actual
            String sql = "INSERT INTO pedidos (numero_mesa, id_caja, numero_clientes, correo_cliente, metodo_pago, fecha, hora) "
                    + "VALUES (?, ?, ?, ?, ?, CURRENT_DATE(), CURRENT_TIME())";

            // Se prepara la sentencia y se indica que se deben devolver las claves generadas (el ID)
            prepStmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // Se establecen los parámetros de la consulta
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setInt(2, Integer.parseInt(pedido.getIdCaja()));
            prepStmt.setInt(3, Integer.parseInt(pedido.getNumeroClientes()));
            prepStmt.setString(4, pedido.getCorreoCliente());
            prepStmt.setString(5, pedido.getMetodoPago());

            // Se ejecuta la actualización (insert)
            int filas = prepStmt.executeUpdate();

            // Si se insertó al menos una fila
            if (filas > 0) {
                // Se obtiene el conjunto de claves generadas
                rs = prepStmt.getGeneratedKeys();
                // Si hay un resultado (el ID generado)
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    pedido.setId(String.valueOf(idGenerado)); // Se asigna el ID al objeto
                    resultado[0] = "Pedido: creado EXITOSAMENTE";
                    resultado[1] = String.valueOf(idGenerado);
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    /**
     * Crea un nuevo pedido que corresponde a una reserva. Es una versión
     * simplificada de 'crear' que solo inserta los datos de reserva.
     *
     * @param pedido El objeto Pedido con los datos de la reserva.
     * @return true si se crea correctamente, false en caso contrario.
     */
    public boolean crearReserva(Pedido pedido) {
        boolean creado = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta para crear una reserva, que no incluye todos los campos
            String sql = "INSERT INTO pedidos (numero_mesa,numero_clientes,id_reserva,correo_cliente) VALUES (?,?,?,?)";

            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setInt(2, Integer.parseInt(pedido.getNumeroClientes()));
            prepStmt.setInt(3, Integer.parseInt(pedido.getIdReserva()));
            prepStmt.setString(4, pedido.getCorreoCliente());

            // Se ejecuta la actualización
            int filas = prepStmt.executeUpdate();
            creado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL CREAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

        return creado;
    }

    /**
     * Actualiza los datos de un pedido existente.
     *
     * @param pedido El objeto Pedido con los nuevos datos.
     * @return true si se actualiza correctamente, false en caso contrario.
     */
    public boolean actualizar(Pedido pedido) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta UPDATE para modificar los datos del pedido
            String sql = "UPDATE pedidos SET numero_mesa = ?, id_caja = ?, numero_clientes = ?, correo_cliente = ?, metodo_pago = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            // Se establecen los nuevos valores en la sentencia preparada
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setInt(2, Integer.parseInt(pedido.getIdCaja()));
            prepStmt.setInt(3, Integer.parseInt(pedido.getNumeroClientes()));
            prepStmt.setString(4, pedido.getCorreoCliente());
            prepStmt.setString(5, pedido.getMetodoPago());
            // Se especifica el ID del pedido a actualizar
            prepStmt.setInt(6, Integer.parseInt(pedido.getId()));

            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    /**
     * Actualiza un pedido que era una reserva, completando los campos
     * restantes.
     *
     * @param pedido El objeto Pedido con los nuevos datos.
     * @return true si se actualiza correctamente, false en caso contrario.
     */
    public boolean actualizarReserva(Pedido pedido) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta UPDATE para actualizar una reserva, asignando la fecha, hora y otros campos
            String sql = "UPDATE pedidos SET fecha = CURRENT_DATE(), hora = CURRENT_TIME(), id_caja = ?,metodo_pago = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getIdCaja()));
            prepStmt.setString(2, pedido.getMetodoPago());
            prepStmt.setInt(3, Integer.parseInt(pedido.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    /**
     * Edita los detalles de una reserva existente (número de mesa, clientes,
     * correo).
     *
     * @param pedido El objeto Pedido con los datos a modificar.
     * @return true si se actualiza correctamente, false en caso contrario.
     */
    public boolean EditarReserva(Pedido pedido) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta UPDATE para modificar una reserva específica
            String sql = "UPDATE pedidos SET numero_mesa = ?, numero_clientes = ?, correo_cliente = ? WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(pedido.getNumeroMesa()));
            prepStmt.setInt(2, Integer.parseInt(pedido.getNumeroClientes()));
            prepStmt.setString(3, pedido.getCorreoCliente());
            prepStmt.setInt(4, Integer.parseInt(pedido.getId()));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    /**
     * Actualiza el estado del pedido a "facturado" (facturado = 1).
     *
     * @param id El ID del pedido a facturar.
     * @return true si el estado se actualiza correctamente, false en caso
     * contrario.
     */
    public boolean patchFacturar(String id) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta UPDATE para cambiar el estado de la columna 'facturado' a 1
            String sql = "UPDATE pedidos SET facturado = 1 WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR ESTADO DE PAGO PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    /**
     * Actualiza el estado del pedido a "no borrado" (eliminado = 0).
     *
     * @param id El ID del pedido a marcar como no borrado.
     * @return true si el estado se actualiza correctamente, false en caso
     * contrario.
     */
    public boolean patchBorradoNo(String id) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta UPDATE para cambiar el estado de la columna 'eliminado' a 0
            String sql = "UPDATE pedidos SET eliminado = 0 WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL BORRAR PEDIDO PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    /**
     * Actualiza el estado del pedido a "borrado" (eliminado = 1).
     *
     * @param id El ID del pedido a marcar como borrado.
     * @return true si el estado se actualiza correctamente, false en caso
     * contrario.
     */
    public boolean patchBorradoSi(String id) {
        boolean actualizado = false;

        try {
            conn = DBConnection.getConnection();
            // Consulta UPDATE para cambiar el estado de la columna 'eliminado' a 1
            String sql = "UPDATE pedidos SET eliminado = 1 WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            actualizado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL BORRAR PEDIDO PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    /**
     * Calcula el valor total de un pedido a partir de sus detalles y actualiza
     * el campo 'valor_total' en la tabla de pedidos.
     *
     * @param id El ID del pedido cuyo total se va a calcular y actualizar.
     * @return true si la actualización es exitosa, false en caso contrario.
     */
    public boolean PatchTotal(String id) {
        boolean actualizado = false;
        Connection conn = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            // 1. Consulta para calcular el total del pedido
            // Utiliza SUM para sumar los precios de comidas, bebidas y cócteles
            // y LEFT JOIN para incluir detalles que podrían no tener un tipo de producto
            // (por ejemplo, un detalle solo con bebida).
            // IFNULL se usa para manejar valores nulos y evitar errores de suma.
            String consultaTotal = "SELECT SUM(IFNULL(c.precio * dp.cantidad_comida, 0) + IFNULL(b.precio * dp.cantidad_bebida, 0) + IFNULL(co.precio * dp.cantidad_coctel, 0)) AS total_pedido FROM detalle_pedido AS dp LEFT JOIN comidas AS c ON dp.id_comida = c.id LEFT JOIN bebidas AS b ON dp.id_bebida = b.id LEFT JOIN cocteles AS co ON dp.id_coctel = co.id WHERE dp.id_pedido = ? GROUP BY dp.id_pedido";

            prepStmt = conn.prepareStatement(consultaTotal);
            prepStmt.setInt(1, Integer.parseInt(id));
            rs = prepStmt.executeQuery();

            // Si se encuentra un resultado para el total
            if (!rs.next()) {
                System.out.println("Pedido: no hay pedidos con el id " + id + ".");
            } else {
                double total = rs.getDouble("total_pedido");

                // 2. Consulta para actualizar el campo 'valor_total' del pedido
                String actualizarSQL = "UPDATE pedidos SET valor_total = ? WHERE id = ?";
                prepStmt = conn.prepareStatement(actualizarSQL);
                prepStmt.setDouble(1, total);
                prepStmt.setInt(2, Integer.parseInt(id));

                int filas = prepStmt.executeUpdate();
                actualizado = filas > 0;
            }

        } catch (Exception e) {
            System.err.println("ERROR AL ACTUALIZAR TOTAL DEL PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

        return actualizado;
    }

    /**
     * Elimina un pedido y todos los detalles de pedido asociados. Esta es una
     * eliminación física de los registros.
     *
     * @param id El ID del pedido a eliminar.
     * @return true si se elimina correctamente, false en caso contrario.
     */
    public boolean eliminar(String id) {
        boolean eliminado = false;

        try {
            conn = DBConnection.getConnection();

            // Paso 1: Se busca si existen registros en la tabla 'detalle_pedido'
            // que estén relacionados con el 'id_pedido' a eliminar.
            String sqlBuscar = "SELECT id FROM detalle_pedido WHERE id_pedido = ?";
            prepStmt = conn.prepareStatement(sqlBuscar);
            prepStmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = prepStmt.executeQuery();

            // Si se encuentra al menos un detalle, se procede a borrarlos
            if (rs.next()) {
                String sqlEliminarDetalle = "DELETE FROM detalle_pedido WHERE id_pedido = ?";
                prepStmt = conn.prepareStatement(sqlEliminarDetalle);
                prepStmt.setInt(1, Integer.parseInt(id));
                prepStmt.executeUpdate();
            }

            // Paso 2: Finalmente, se elimina el pedido principal de la tabla 'pedidos'.
            String sql = "DELETE FROM pedidos WHERE id = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, Integer.parseInt(id));
            int filas = prepStmt.executeUpdate();
            eliminado = filas > 0;

        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR PEDIDO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Cierre de recursos
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

    public Boolean verFacturadosCerrarCaja(String id) {
        boolean existe = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT 1 FROM pedidos WHERE valor_total = 0 AND id_caja = ?";
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, id);
            rs = prepStmt.executeQuery();

            if (rs.next()) {
                existe = true;
            }
        } catch (Exception e) {
            System.err.println("ERROR AL VERIFICAR PEDIDOS FACTURADOS DE CAJA: " + e.getMessage());
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
        return existe;
    }

}
