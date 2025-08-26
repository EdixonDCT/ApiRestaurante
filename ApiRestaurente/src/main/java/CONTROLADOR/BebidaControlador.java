package CONTROLADOR; // Define el paquete al que pertenece esta clase.

import MODELO.Bebida; // Importa la clase Bebida del paquete MODELO.
import DAO.BebidaDAO; // Importa la clase BebidaDAO del paquete MODELO.
import Utils.Middlewares; // Importa la clase Middlewares del paquete CONTROLADOR.

import java.util.List; // Importa la clase List para trabajar con listas de objetos.
import javax.ws.rs.*; // Importa todas las clases del paquete JAX-RS para crear servicios web RESTful.
import javax.ws.rs.core.MediaType; // Importa la clase MediaType para especificar los tipos de datos (ej. JSON).
import javax.ws.rs.core.Response; // Importa la clase Response para construir las respuestas HTTP.

@Path("bebidas") // Define la ruta URL base para esta clase de controlador (ej. /api/bebidas).
@Produces(MediaType.APPLICATION_JSON) // Especifica que los métodos en esta clase producirán respuestas en formato JSON.
@Consumes(MediaType.APPLICATION_JSON) // Especifica que los métodos en esta clase consumirán datos en formato JSON.
public class BebidaControlador { // Declaración de la clase del controlador para las bebidas.

    private BebidaDAO bebidaDAO = new BebidaDAO(); // Crea una nueva instancia de BebidaDAO para interactuar con la base de datos.

    @GET // Anotación para mapear este método a una solicitud HTTP GET.
    public Response listarBebidas() { // Método para obtener la lista completa de bebidas.
        try { // Inicia un bloque try-catch para manejar posibles errores.
            List<Bebida> lista = bebidaDAO.listarTodos(); // Llama al método del DAO para obtener todas las bebidas.
            return Response.ok(lista).build(); // Devuelve una respuesta HTTP 200 (OK) con la lista de bebidas en formato JSON.
        } catch (Exception e) { // Captura cualquier excepción que pueda ocurrir.
            e.printStackTrace(); // Imprime la traza del error en la consola del servidor.
            return Response.serverError().build(); // Devuelve una respuesta HTTP 500 (Error Interno del Servidor).
        }
    }

    @GET // Anotación para mapear este método a una solicitud HTTP GET.
    @Path("/{id}") // Define una sub-ruta que incluye un parámetro de ruta "id".
    public Response obtenerBebida(@PathParam("id") String id) { // Método para obtener una bebida por su ID.
        try { // Inicia un bloque try-catch.
            String validaId = Middlewares.validarEntero(id, "id"); // Valida que el ID proporcionado sea un número entero.
            if (!validaId.equals("ok")) { // Comprueba si la validación del ID no fue exitosa.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // Devuelve un error 400 (Solicitud incorrecta) con el mensaje de error.
            }

            Bebida bebida = bebidaDAO.obtenerPorId(id); // Llama al DAO para buscar la bebida por su ID.
            if (bebida != null) { // Comprueba si se encontró la bebida.
                return Response.ok(bebida).build(); // Devuelve una respuesta 200 (OK) con los datos de la bebida.
            } else { // Si no se encontró la bebida.
                return Response.status(Response.Status.NOT_FOUND) // Devuelve una respuesta 404 (No Encontrado).
                        .entity("Error: bebida NO ENCONTRADA.") // Añade un mensaje de error al cuerpo de la respuesta.
                        .build(); // Construye y envía la respuesta.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno en el servidor.").build(); // Devuelve un error 500.
        }
    }

    @POST // Anotación para mapear este método a una solicitud HTTP POST.
    public Response crearBebida(Bebida bebida) { // Método para crear una nueva bebida. El cuerpo de la solicitud se convierte en un objeto Bebida.
        try { // Inicia un bloque try-catch.
            String validaNombre = Middlewares.validarString(bebida.getNombre(), "nombre"); // Valida que el nombre no esté vacío.
            if (!validaNombre.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build(); // Devuelve un error 400.
            }

            String validaPrecio = Middlewares.validarDouble(bebida.getPrecio(), "precio"); // Valida que el precio sea un número válido.
            if (!validaPrecio.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build(); // Devuelve un error 400.
            }

            String validaUnidad = Middlewares.validarUnidadBebida(bebida.getUnidad(), "unidad"); // Valida la unidad de la bebida.
            if (!validaUnidad.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaUnidad).build(); // Devuelve un error 400.
            }

            String validaTipo = Middlewares.validarString(bebida.getTipo(), "tipo"); // Valida que el tipo no esté vacío.
            if (!validaTipo.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build(); // Devuelve un error 400.
            }

            String[] resultado = bebidaDAO.crear(bebida); // Llama al DAO para insertar la nueva bebida en la base de datos.

            if (!resultado[1].equals("-1")) { // Comprueba si la creación fue exitosa (el DAO no devolvió -1).
                String json = String.format("{\"mensaje\": \"%s\", \"id\": \"%s\"}", resultado[0], resultado[1]); // Crea una cadena JSON con el mensaje y el nuevo ID.
                return Response.status(Response.Status.CREATED).entity(json).build(); // Devuelve una respuesta 201 (Creado) con el JSON.
            } else { // Si la creación falló.
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // Devuelve un error 500.
                        .entity("Error: no se pudo crear el bebida.").build(); // Con un mensaje de error.
            }

        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno en el servidor.").build(); // Devuelve un error 500.
        }
    }

    @PUT // Anotación para mapear este método a una solicitud HTTP PUT.
    @Path("/{id}") // Define la sub-ruta con el ID de la bebida a actualizar.
    public Response actualizarBebida(@PathParam("id") String id, Bebida bebida) { // Método para actualizar una bebida existente.
        try { // Inicia un bloque try-catch.
            bebida.setId(id); // Establece el ID en el objeto bebida que viene del cuerpo de la solicitud.

            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID de la ruta.
            if (!validaId.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // Devuelve un error 400.
            }

            String validaNombre = Middlewares.validarString(bebida.getNombre(), "nombre"); // Valida el nombre.
            if (!validaNombre.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build(); // Devuelve un error 400.
            }

            String validaPrecio = Middlewares.validarDouble(bebida.getPrecio(), "precio"); // Valida el precio.
            if (!validaPrecio.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build(); // Devuelve un error 400.
            }

            String validaUnidad = Middlewares.validarUnidadBebida(bebida.getUnidad(), "unidad"); // Valida la unidad.
            if (!validaUnidad.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaUnidad).build(); // Devuelve un error 400.
            }

            String validaTipo = Middlewares.validarString(bebida.getTipo(), "tipo"); // Valida el tipo.
            if (!validaTipo.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaTipo).build(); // Devuelve un error 400.
            }

            boolean actualizado = bebidaDAO.actualizar(bebida); // Llama al DAO para actualizar la bebida en la base de datos.
            if (actualizado) { // Comprueba si la actualización fue exitosa.
                return Response.ok().entity("Bebida: " + bebida.getNombre() + " actualizada con ÉXITO.").build(); // Devuelve una respuesta 200 (OK) con un mensaje de éxito.
            } else { // Si la actualización falló.
                return Response.status(Response.Status.NOT_FOUND) // Devuelve un error 404.
                        .entity("Error: bebida NO ENCONTRADA o NO ACTUALIZADA.").build(); // Con un mensaje de error.
            }

        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno en el servidor.").build(); // Devuelve un error 500.
        }
    }

    @PATCH // Anotación para mapear este método a una solicitud HTTP PATCH (actualización parcial).
    @Path("/imagen/{id}") // Define la sub-ruta para actualizar solo la imagen.
    public Response actualizarImagenBebida(@PathParam("id") String id, Bebida bebida) { // Método para actualizar la imagen de una bebida.
        try { // Inicia un bloque try-catch.
            bebida.setId(id); // Establece el ID en el objeto bebida.

            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // Devuelve un error 400.
            }

            boolean actualizado = bebidaDAO.actualizarImagen(bebida); // Llama al DAO para actualizar solo la imagen.
            if (actualizado) { // Comprueba si la actualización fue exitosa.
                return Response.ok().entity("Imagen de la bebida actualizada con ÉXITO.").build(); // Devuelve una respuesta 200 (OK) con un mensaje de éxito.
            } else { // Si la actualización falló.
                return Response.status(Response.Status.NOT_FOUND) // Devuelve un error 404.
                        .entity("Error: bebida NO ENCONTRADA o NO ACTUALIZADA.").build(); // Con un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno en el servidor.").build(); // Devuelve un error 500.
        }
    }

    @PATCH // Anotación para mapear este método a una solicitud HTTP PATCH.
    @Path("/{id}") // Define la sub-ruta con el ID de la bebida.
    public Response actualizarDisponibilidadBebida(@PathParam("id") String id, Bebida bebida) { // Método para actualizar el estado de disponibilidad.
        try { // Inicia un bloque try-catch.
            bebida.setId(id); // Establece el ID en el objeto bebida.

            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // Devuelve un error 400.
            }

            String validaDisponible = Middlewares.validarBooleano(bebida.getDisponible(), "disponible"); // Valida el campo "disponible".
            if (!validaDisponible.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponible).build(); // Devuelve un error 400.
            }

            boolean actualizado = bebidaDAO.actualizarEstado(bebida); // Llama al DAO para actualizar el estado de disponibilidad.
            if (actualizado) { // Comprueba si la actualización fue exitosa.
                return Response.ok().entity("Disponibilidad de bebida actualizada con ÉXITO.").build(); // Devuelve una respuesta 200 (OK) con un mensaje de éxito.
            } else { // Si la actualización falló.
                return Response.status(Response.Status.NOT_FOUND) // Devuelve un error 404.
                        .entity("Error: bebida NO ENCONTRADA o NO ACTUALIZADA.").build(); // Con un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno en el servidor.").build(); // Devuelve un error 500.
        }
    }

    @DELETE // Anotación para mapear este método a una solicitud HTTP DELETE.
    @Path("/{id}") // Define la sub-ruta con el ID de la bebida a eliminar.
    public Response eliminarBebida(@PathParam("id") String id) { // Método para eliminar una bebida.
        try { // Inicia un bloque try-catch.
            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) { // Comprueba si la validación falló.
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // Devuelve un error 400.
            }

            boolean eliminado = bebidaDAO.eliminar(id); // Llama al DAO para eliminar la bebida.
            if (eliminado) { // Comprueba si la eliminación fue exitosa.
                return Response.ok().entity("Bebida eliminada con ÉXITO.").build(); // Devuelve una respuesta 200 (OK) con un mensaje de éxito.
            } else { // Si la eliminación falló.
                return Response.status(Response.Status.NOT_FOUND) // Devuelve un error 404.
                        .entity("Error: bebida NO ENCONTRADA.").build(); // Con un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno en el servidor.").build(); // Devuelve un error 500.
        }
    }
}