package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import ui.ResumenOrdenPage;

/**
 * Pregunta para obtener la cantidad de un producto en la orden.
 * Retorna la cantidad como String (ej: "x2", "x3")
 */
public class LaCantidadDelProducto implements Question<String> {

    private final String nombreProducto;

    private LaCantidadDelProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public static LaCantidadDelProducto conNombre(String nombreProducto) {
        return new LaCantidadDelProducto(nombreProducto);
    }

    @Override
    public String answeredBy(Actor actor) {
        String selectorProducto = nombreProducto
                .toLowerCase()
                .replaceAll("\\s+", "-");

        return Text.of(ResumenOrdenPage.CANTIDAD_EN_ORDEN.of(selectorProducto))
                .answeredBy(actor)
                .trim();
    }
}
