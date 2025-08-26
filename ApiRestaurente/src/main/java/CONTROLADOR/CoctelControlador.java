// Paquete que agrupa todos los controladores del proyecto
package CONTROLADOR;

import MODELO.Coctel; // Importa la clase 'Coctel', que representa un cóctel.
import DAO.CoctelDAO; // Importa la clase 'CoctelDAO' para interactuar con la base de datos.
import Utils.Middlewares; // Importa la clase 'Middlewares' para las validaciones de datos.

import java.util.List; // Importa la interfaz 'List' para manejar colecciones de objetos.

import javax.ws.rs.*; // Importa todas las anotaciones de JAX-RS para servicios web RESTful.
import javax.ws.rs.core.MediaType; // Importa 'MediaType' para especificar el tipo de contenido.
import javax.ws.rs.core.Response; // Importa 'Response' para construir y devolver respuestas HTTP.

@Path("cocteles") // Define la ruta base para todos los endpoints de este controlador.
@Produces(MediaType.APPLICATION_JSON) // Configura que los métodos de esta clase producirán respuestas en formato JSON.
@Consumes(MediaType.APPLICATION_JSON) // Configura que los métodos consumirán datos en formato JSON.
public class CoctelControlador {

    private CoctelDAO coctelDAO = new CoctelDAO(); // Crea una instancia del DAO para las operaciones de persistencia.

    @GET // Anotación para manejar peticiones HTTP GET.
    public Response listarCocteles() { // Método para obtener una lista de todos los cócteles.
        try { // Inicia un bloque try-catch para manejar posibles errores.
            List<Coctel> lista = coctelDAO.listarTodos(); // Llama al DAO para obtener todos los cócteles.
            return Response.ok(lista).build(); // Retorna una respuesta 200 (OK) con la lista de cócteles.
        } catch (Exception e) { // Captura cualquier excepción que ocurra.
            e.printStackTrace(); // Imprime la traza del error para depuración.
            return Response.serverError().build(); // Retorna un error 500 (Internal Server Error).
        }
    }

    @GET // Anotación para manejar peticiones HTTP GET.
    @Path("/{id}") // Mapea a la ruta '/cocteles/{id}', donde '{id}' es un parámetro de la URL.
    public Response obtenerCoctel(@PathParam("id") String id) { // Método para obtener un cóctel por su ID.
        try { // Inicia el bloque try-catch.
            String validaId = Middlewares.validarEntero(id, "id"); // Valida que el ID sea un número entero.
            if (!validaId.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // ...retorna un 400 (Bad Request).
            }

            Coctel coctel = coctelDAO.obtenerPorId(id); // Busca el cóctel en la base de datos por su ID.
            if (coctel != null) { // Si el cóctel es encontrado...
                return Response.ok(coctel).build(); // ...retorna una respuesta 200 (OK) con el objeto Coctel.
            } else { // Si el cóctel no es encontrado...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404 (Not Found).
                        .entity("Error: coctel NO ENCONTRADO.") // Con un mensaje de error.
                        .build(); // Construye y retorna la respuesta.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno del servidor.").build(); // Retorna un 500 con un mensaje de error.
        }
    }

    @POST // Anotación para manejar peticiones HTTP POST (crear un recurso).
    public Response crearCoctel(Coctel coctel) { // Método para crear un nuevo cóctel.
        try { // Inicia el bloque try-catch.
            String validaNombre = Middlewares.validarString(coctel.getNombre(), "nombre"); // Valida que el nombre no sea nulo o vacío.
            if (!validaNombre.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build(); // ...retorna un 400.
            }

            String validaPrecio = Middlewares.validarDouble(coctel.getPrecio(), "precio"); // Valida que el precio sea un número válido.
            if (!validaPrecio.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build(); // ...retorna un 400.
            }

            String[] resultado = coctelDAO.crear(coctel); // Llama al DAO para crear el cóctel y obtener el resultado.

            if (!resultado[1].equals("-1")) { // Si el ID de la creación no es '-1' (indica éxito)...
                String json = String.format("{\"mensaje\": \"%s\", \"id\": \"%s\"}", resultado[0], resultado[1]); // Construye una respuesta JSON manual.
                return Response.status(Response.Status.CREATED).entity(json).build(); // Retorna un 201 (Created) con el JSON.
            } else { // Si la creación falla...
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // ...retorna un 500 (Internal Server Error).
                        .entity("Error: no se pudo crear el coctel.").build(); // Con un mensaje de error.
            }

        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno en el servidor.").build(); // Retorna un 500.
        }
    }

    @PUT // Anotación para manejar peticiones HTTP PUT (actualizar un recurso completo).
    @Path("/{id}") // Mapea a la ruta '/cocteles/{id}'.
    public Response actualizarCoctel(@PathParam("id") String id, Coctel coctel) { // Método para actualizar un cóctel.
        try { // Inicia el bloque try-catch.
            coctel.setId(id); // Asigna el ID de la URL al objeto 'coctel'.

            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // ...retorna un 400.
            }

            String validaNombre = Middlewares.validarString(coctel.getNombre(), "nombre"); // Valida el nombre.
            if (!validaNombre.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaNombre).build(); // ...retorna un 400.
            }

            String validaPrecio = Middlewares.validarDouble(coctel.getPrecio(), "precio"); // Valida el precio.
            if (!validaPrecio.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaPrecio).build(); // ...retorna un 400.
            }

            boolean actualizado = coctelDAO.actualizar(coctel); // Llama al DAO para actualizar el cóctel.
            if (actualizado) { // Si la actualización fue exitosa...
                return Response.ok().entity("Coctel: "+coctel.getNombre()+" actualizado con EXITO.").build(); // ...retorna un 200 (OK) con un mensaje.
            } else { // Si la actualización falla...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404 (Not Found).
                        .entity("Error: coctel NO ENCONTRADO o NO ACTUALIZADO.").build(); // Con un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno del servidor.").build(); // Retorna un 500.
        }
    }

    @PATCH // Anotación para manejar peticiones HTTP PATCH (actualización parcial).
    @Path("/imagen/{id}") // Mapea a la ruta '/cocteles/imagen/{id}'.
    public Response actualizarImagenCoctel(@PathParam("id") String id, Coctel coctel) { // Método para actualizar solo la imagen del cóctel.
        try { // Inicia el bloque try-catch.
            coctel.setId(id); // Asigna el ID de la URL al objeto 'coctel'.

            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // ...retorna un 400.
            }

            boolean actualizado = coctelDAO.actualizarImagen(coctel); // Llama al DAO para actualizar la imagen.
            if (actualizado) { // Si la actualización fue exitosa...
                return Response.ok().entity("Imagen actualizada con EXITO.").build(); // ...retorna un 200 (OK).
            } else { // Si la actualización falla...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404.
                        .entity("Error: coctel NO ENCONTRADO o NO ACTUALIZADO.").build(); // Con un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno del servidor.").build(); // Retorna un 500.
        }
    }

    @PATCH // Anotación para peticiones HTTP PATCH.
    @Path("/{id}") // Mapea a la ruta '/cocteles/{id}'.
    public Response actualizarDisponibilidadCoctel(@PathParam("id") String id, Coctel coctel) { // Método para actualizar la disponibilidad del cóctel.
        try { // Inicia el bloque try-catch.
            coctel.setId(id); // Asigna el ID de la URL al objeto 'coctel'.

            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // ...retorna un 400.
            }

            String validaDisponible = Middlewares.validarBooleano(coctel.getDisponible(), "disponible"); // Valida que el campo 'disponible' sea un booleano.
            if (!validaDisponible.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaDisponible).build(); // ...retorna un 400.
            }

            boolean actualizado = coctelDAO.actualizarEstado(coctel); // Llama al DAO para actualizar el estado.
            if (actualizado) { // Si la actualización fue exitosa...
                return Response.ok().entity("Disponibilidad actualizada con EXITO.").build(); // ...retorna un 200.
            } else { // Si la actualización falla...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404.
                        .entity("Error: coctel NO ENCONTRADO o NO ACTUALIZADO.").build(); // Con un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno del servidor.").build(); // Retorna un 500.
        }
    }

    @DELETE // Anotación para manejar peticiones HTTP DELETE.
    @Path("/{id}") // Mapea a la ruta '/cocteles/{id}'.
    public Response eliminarCoctel(@PathParam("id") String id) { // Método para eliminar un cóctel.
        try { // Inicia el bloque try-catch.
            String validaId = Middlewares.validarEntero(id, "id"); // Valida el ID.
            if (!validaId.equals("ok")) { // Si la validación falla...
                return Response.status(Response.Status.BAD_REQUEST).entity(validaId).build(); // ...retorna un 400.
            }

            boolean eliminado = coctelDAO.eliminar(id); // Llama al DAO para eliminar el cóctel.
            if (eliminado) { // Si la eliminación fue exitosa...
                return Response.ok().entity("Coctel eliminado con EXITO.").build(); // ...retorna un 200.
            } else { // Si la eliminación falla...
                return Response.status(Response.Status.NOT_FOUND) // ...retorna un 404.
                        .entity("Error: coctel NO ENCONTRADO.").build(); // Con un mensaje de error.
            }
        } catch (Exception e) { // Captura cualquier excepción.
            e.printStackTrace(); // Imprime la traza del error.
            return Response.serverError().entity("Error interno del servidor.").build(); // Retorna un 500.
        }
    }
}
