package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import ui.ResumenOrdenPage;

/**
 * Pregunta para verificar si el pedido contiene múltiples productos específicos.
 * Retorna true si TODOS los productos especificados están presentes.
 */
public class ElPedidoContiene implements Question<Boolean> {

    private final String[] nombresProductos;

    private ElPedidoContiene(String... nombresProductos) {
        this.nombresProductos = nombresProductos;
    }

    public static ElPedidoContiene losProductos(String... nombresProductos) {
        return new ElPedidoContiene(nombresProductos);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        // Verifica que TODOS los productos estén presentes
        for (String nombreProducto : nombresProductos) {
            String productoKebabCase = nombreProducto
                    .toLowerCase()
                    .replaceAll("\\s+", "-");

            boolean estaPresente = Visibility.of(ResumenOrdenPage.ITEM_ORDEN.of(productoKebabCase))
                    .answeredBy(actor);

            if (!estaPresente) {
                return false; // Si alguno no está, retorna false
            }
        }
        return true; // Todos están presentes
    }
}
