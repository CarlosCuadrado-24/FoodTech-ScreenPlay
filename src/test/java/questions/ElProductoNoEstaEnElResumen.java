package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;
import ui.ResumenOrdenPage;

/**
 * Pregunta para verificar si un producto NO aparece en el resumen de orden.
 * Retorna true si el producto NO está presente, false si está presente.
 */
public class ElProductoNoEstaEnElResumen implements Question<Boolean> {

    private final String nombreProducto;

    private ElProductoNoEstaEnElResumen(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public static ElProductoNoEstaEnElResumen llamado(String nombreProducto) {
        return new ElProductoNoEstaEnElResumen(nombreProducto);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        // Convertir nombre a formato kebab-case
        String productoKebabCase = nombreProducto
                .toLowerCase()
                .replaceAll("\\s+", "-");

        // Retorna true si el elemento NO es visible
        return !Visibility.of(ResumenOrdenPage.ITEM_ORDEN.of(productoKebabCase))
                .answeredBy(actor);
    }
}
