package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import ui.ResumenOrdenPage;

/**
 * Pregunta para verificar si un producto está visible en el resumen de orden.
 * Retorna true si el producto aparece en la orden.
 */
public class ElProductoEnElResumen implements Question<Boolean> {

    private final String nombreProducto;

    private ElProductoEnElResumen(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public static ElProductoEnElResumen conNombre(String nombreProducto) {
        return new ElProductoEnElResumen(nombreProducto);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String selectorProducto = nombreProducto
                .toLowerCase()
                .replaceAll("\\s+", "-");

        return Visibility.of(ResumenOrdenPage.ITEM_ORDEN.of(selectorProducto))
                .answeredBy(actor);
    }
}
