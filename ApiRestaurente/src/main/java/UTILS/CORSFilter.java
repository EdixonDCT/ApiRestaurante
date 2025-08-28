package UTILS; // Define el paquete en el que se encuentra la clase

import java.io.IOException; // Importa la clase para manejar excepciones de entrada/salida
import javax.ws.rs.container.ContainerRequestContext; // Importa la interfaz para acceder a datos de la petición HTTP
import javax.ws.rs.container.ContainerResponseContext; // Importa la interfaz para acceder a datos de la respuesta HTTP
import javax.ws.rs.container.ContainerResponseFilter; // Importa la interfaz para filtrar y modificar respuestas
import javax.ws.rs.ext.Provider; // Importa la anotación para registrar un proveedor JAX-RS

@Provider // Indica que esta clase es un proveedor de JAX-RS y será registrada automáticamente
public class CORSFilter implements ContainerResponseFilter{ // Declara la clase que implementa el filtro de respuestas
    @Override // Indica que se está sobrescribiendo un método de la interfaz implementada
    public void filter (ContainerRequestContext requestContext, ContainerResponseContext responseContext) // Método que intercepta y modifica la respuesta HTTP
    throws IOException // Indica que el método puede lanzar una excepción de tipo IOException
    {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*"); // Permite solicitudes desde cualquier origen
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization"); // Especifica qué cabeceras se permiten en las solicitudes
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true"); // Permite el envío de credenciales como cookies o tokens
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT,PATCH, DELETE, OPTIONS, HEAD"); // Especifica los métodos HTTP permitidos
    }
}